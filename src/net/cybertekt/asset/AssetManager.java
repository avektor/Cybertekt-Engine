package net.cybertekt.asset;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import net.cybertekt.util.HashCache;
import net.cybertekt.util.HashCache.CacheMode;
import net.cybertekt.util.HashCache.MapMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Asset Manager - (C) Cybertekt Software.
 * <p>
 * Static utility class that provides functionality for concurrently loading,
 * caching, and constructing {@link Asset assets} from external files located in
 * the projects {@link #rootDir assets\} directory. Assets are loaded via a
 * String or {@link AssetKey asset key} that specifies the location of the file.
 * </p>
 * <p>
 * The static methods provided by this class are <b>NOT</b> thread-safe! This
 * was a conscious design choice as this class itself spawns new threads as
 * needed to handle the requested asset loading tasks. The static methods
 * provided by this class must <b>ONLY</b> be called from the main application
 * thread.
 * </p>
 *
 * {@link AssetLoader asset loaders} and retrieving from external files in a
 * platform independent way.
 *
 * Defines three {@link java.lang.RuntimeException runtime exceptions} that may
 * be thrown when attempting to load external assets.
 *
 * @version 1.0.0
 * @since 1.0.0
 * @author Andrew Vektor
 */
public final class AssetManager {

    /**
     * SLF4J internal class logger for debugging.
     */
    public static final Logger log = LoggerFactory.getLogger(AssetManager.class);

    /**
     * Stores the absolute path of the root assets directory on the current
     * users system. This is the directory from which the {@link AssetManager}
     * will search for and load {@link Asset assets}.
     */
    public static final String rootDir = System.getProperty("user.dir").replace('\\', '/') + "/assets/";

    /**
     * Executor Service to which {@link AssetLoader asset loader} tasks are
     * submitted.
     * <p>
     * I have done a lot of testing with this and I'm still not entirely sure
     * what type of thread pool to use here. There doesn't seem to be an easy
     * option for a max-limit, unbounded queue, cached thread pool which I think
     * would work best for this type of request system. I have slightly explored
     * the idea of using a working-stealing thread pool but I've read
     * conflicting information regarding the effectiveness of this approach for
     * this type of system.
     * </p>
     * <p>
     * A few other simple approaches that will produce different results given
     * the device the application is running on:
     * </p>
     * private static final ExecutorService threadPool =
     * Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors(),
     * new AssetThreadFactory()); private static final ExecutorService
     * threadPool = Executors.newCachedThreadPool();
     */
    private static final ThreadPool threadPool = new ThreadPool();

    /**
     * Stores the registered {@link AssetLoader asset loaders} based on the
     * {@link AssetType asset types} it supports. Only one loader at a time may
     * be registered to a specific asset type.
     */
    private static final Map<AssetType, AssetLoader> assetLoaders = new IdentityHashMap<>();

    /**
     * {@link java.util.concurrent.ConcurrentHashMap Hash map} for storing
     * {@link Asset assets} that are waiting to be loaded.
     */
    private static final Map<AssetKey, Future<? extends Asset>> pendingAssets = new ConcurrentHashMap<>();

    /**
     * Softly caches fully loaded {@link Asset assets} based on their associated
     * {@link AssetKey key}. Assets that are no longer in use will be
     * periodically purged from the cache.
     */
    private static final HashCache<AssetKey, Asset> cachedAssets = new HashCache<>(CacheMode.Soft, MapMode.Concurrent);

    /**
     * Stores fallback {@link Asset assets} to be used in the event that an
     * asset of the same type fails to initialize when requested. This can be
     * used to prevent {@link AssetInitializationException exceptions} for a
     * specific asset {@link AssetType type}.
     */
    private static final Map<AssetType, Asset> fallbackAssets = new ConcurrentHashMap<>();

    /**
     * {@link AtomicInteger Atomic integer} that tracks the number of
     * {@link Asset assets} that have been loaded successfully.
     */
    private static final AtomicInteger loaded = new AtomicInteger(0);

    /**
     * {@link AtomicInteger Atomic integer} that tracks the number of enqueued
     * {@link Asset assets} currently waiting to be loaded.
     */
    private static final AtomicInteger requested = new AtomicInteger(0);

    /**
     * {@link AtomicInteger Atomic integer} that tracks the number of
     * {@link Asset assets} that have failed to load.
     */
    private static final AtomicInteger failed = new AtomicInteger(0);

    /**
     * Private constructor that prohibits the construction of other instances of
     * AssetManager. This class is designed for static access only. Creating
     * additional instances of this class is strongly discouraged as it will
     * result in unexpected behavior.
     */
    private AssetManager() {
    }

    public static final AssetKey load(final String path) {
        return load(AssetKey.getKey(path));
    }

    public static final AssetKey load(final String path, final boolean reload) {
        return load(AssetKey.getKey(path), reload);
    }

    public static final AssetKey load(final AssetKey key) {
        return load(key, false);
    }

    public static final AssetKey[] load(final String... paths) {
        AssetKey[] keys = new AssetKey[paths.length];
        for (int i = 0; i < paths.length; i++) {
            keys[i] = load(paths[i]);
        }
        return keys;
    }

    public static final AssetKey[] load(final AssetKey... keys) {
        for (final AssetKey key : keys) {
            load(key);
        }
        return keys;
    }

    /**
     * Submits a task to construct an {@link Asset asset} from the file located
     * at the path defined by the specified {@link AssetKey key}.
     *
     * @param key
     * @param reload
     * @return
     */
    public static final AssetKey load(final AssetKey key, final boolean reload) {
        if (!reload && pendingAssets.containsKey(key) || cachedAssets.containsKey(key)) {
            return key;
        }
        AssetLoader loader = assetLoaders.get(key.getType());
        if (loader != null) {
            try {
                pendingAssets.put(key, (Future<Asset>) threadPool.submit(loader.newTask(key, stream(key))));
                requested.incrementAndGet();
                return key;
            } catch (final AssetNotFoundException e) {
                final Asset fallback = getFallback(key.getType());
                if (fallback != null) {
                    cachedAssets.put(key, fallback);
                    return key;
                } else {
                    throw e;
                }
            }
        } else {
            final Asset fallback = getFallback(key.getType());
            if (fallback != null) {
                cachedAssets.put(key, fallback);
                return key;
            } else {
                throw new UnsupportedAssetTypeException(key);
            }
        }
    }

    public static final Asset get(final String path) {
        return get(AssetKey.getKey(path));
    }

    public static final <T extends Asset> T get(final Class<T> assetClass, final String path) {
        return assetClass.cast(get(path));
    }

    public static final <T extends Asset> T get(final Class<T> assetClass, final AssetKey key) {
        return assetClass.cast(get(key));
    }

    /**
     * Retrieves the {@link Asset assets} fo
     *
     * @param <T>
     * @param assetClass
     * @param paths
     * @return
     */
    public static final <T extends Asset> T[] get(final Class<T> assetClass, final String... paths) {
        T[] assets = (T[]) Array.newInstance(assetClass, paths.length);
        for (int i = 0; i < paths.length; i++) {
            assets[i] = assetClass.cast(get(paths[i]));
        }
        return assets;
    }

    /**
     * Retrieves the {@link Asset assets} for the files located at the paths
     * specified by the {@link AssetKey asset keys} provided.
     *
     * @param <T>
     * @param assetClass
     * @param keys
     * @return
     */
    public static final <T extends Asset> T[] get(final Class<T> assetClass, final AssetKey... keys) {
        T[] assets = (T[]) Array.newInstance(assetClass, keys.length);
        for (int i = 0; i < keys.length; i++) {
            assets[i] = assetClass.cast(get(keys[i]));
        }
        return assets;
    }

    /**
     * Retrieves the {@link Asset asset} for the file located at the path
     * specified by the provided {@link AssetKey#getAbsolutePath() asset key}.
     * If the asset has already been loaded...
     *
     * @param key
     * @return
     */
    public static final Asset get(final AssetKey key) {
        Asset asset = cachedAssets.get(key);
        if (asset != null) {
            return asset;
        }

        final Future<? extends Asset> future = pendingAssets.get(key);
        if (future != null) {
            try {
                asset = future.get();
                cachedAssets.put(key, asset);
                pendingAssets.remove(key);
            } catch (final InterruptedException | ExecutionException e) {
                final Asset fallback = getFallback(key.getType());
                if (fallback != null) {
                    cachedAssets.put(key, asset = fallback);
                    pendingAssets.remove(key);
                } else {
                    throw new AssetInitializationException(key, e.getLocalizedMessage());
                }
            }
        } else {
            requested.incrementAndGet();
            AssetLoader loader = assetLoaders.get(key.getType());
            if (loader != null) {
                try {
                    cachedAssets.put(key, asset = loader.loadInline(key, stream(key)));
                    loaded.incrementAndGet();
                } catch (final AssetNotFoundException | AssetInitializationException e) {
                    final Asset fallback = getFallback(key.getType());
                    failed.incrementAndGet();

                    if (fallback != null) {
                        cachedAssets.put(key, fallback);
                    } else {
                        throw e;
                    }
                }
            } else {
                failed.incrementAndGet();
                throw new UnsupportedAssetTypeException(key);
            }
        }
        return asset;
    }

    /**
     * Retrieves an {@link java.io.InputStream} for the file located at the
     * specified path, relative to the {@link #rootDir base assets directory}.
     *
     * @param path the location of the file for which to retrieve the
     * {@link java.io.InputStream}.
     * @return the {@link java.io.InputStrem} for the file located at the
     * specified path.
     */
    public static final InputStream stream(final String path) throws AssetNotFoundException {
        return stream(AssetKey.getKey(path));
    }

    /**
     * Retrieves the {@link java.io.InputStream input stream} for the file
     * located at the path specified by the provided
     * {@link AssetKey#getAbsolutePath() asset key}.
     *
     * @param key the {@link AssetKey key} for which to retrieve an
     * {@link java.io.InputStream input stream}.
     * @return the {@link InputStream input stream} for the file located at the
     * path specified by the {@link AssetKey#getAbsolutePath() asset key}.
     */
    public static final InputStream stream(final AssetKey key) throws AssetNotFoundException {
        try {
            InputStream stream = new FileInputStream(key.getAbsolutePath());
            return stream;
        } catch (final FileNotFoundException e) {
            log.info("Could not retrieve stream for resource at: " + key.getAbsolutePath());
            throw new AssetNotFoundException(key);
        }
    }

    /**
     * Extracts and returns the full text extracted from the provided
     * {@link InputStream stream}.
     *
     * @param stream the stream from which to extract the text.
     * @return the text extracted from the provided {@link InputStream stream}.
     */
    public static final String streamToString(final InputStream stream) {
        if (stream != null) {
            return new java.util.Scanner(stream).useDelimiter("\\A").next();
        } else {
            log.error("Could not extract text from null input stream!");
            return null;
        }
    }

    /**
     * Registers the {@link AssetLoader loader} class to be used for loading
     * {@link Asset assets} of the specified {@link AssetType type(s)}. Loaders
     * registered via this method <b>must</b> have a publicly accessible empty
     * constructor.
     *
     * Warning: Any {@link AssetType asset types} previously registered to
     * another {@link AssetLoader loader} will be overridden to point to the new
     * loader. The changes will have an application-wide effect.
     *
     * @param loader the {@link AssetLoader loader} to register with the
     * specified {@link AssetType types}.
     * @param types the asset {@link AssetType types} to associate with the
     * {@link AssetLoader loader}.
     */
    public static final void registerLoader(final Class<? extends AssetLoader> loader, final AssetType... types) {
        try {
            registerLoader(loader.newInstance(), types);
        } catch (final InstantiationException | IllegalAccessException e) {
            log.warn("Unable to register [{}] - {}: Asset Loaders must have a publicly accessible empty constructor!", loader.getSimpleName(), e.getClass().getSimpleName());
        }
    }

    /**
     * Registers the {@link AssetLoader loader} class to be used for loading
     * {@link Asset assets} of the specified {@link AssetType type(s)}. Loaders
     * registered via this method <b>must</b> have a publicly accessible empty
     * constructor.
     *
     * Warning: Any {@link AssetType asset types} previously registered to
     * another {@link AssetLoader loader} will be overridden to point to the new
     * loader. The changes will have an application-wide effect.
     *
     * @param loader the {@link AssetLoader loader} to register with the
     * specified {@link AssetType types}.
     * @param types the asset {@link AssetType types} to associate with the
     * {@link AssetLoader loader}.
     */
    public static final void registerLoader(final Class<? extends AssetLoader> loader, final List<AssetType> types) {
        registerLoader(loader, types.toArray(new AssetType[types.size()]));
    }

    /**
     * Registers the {@link AssetLoader loader} to be used for loading
     * {@link Asset assets} of the specified {@link AssetType type(s)}.
     *
     * Warning: Any {@link AssetType asset types} previously registered to
     * another {@link AssetLoader loader} will be overridden to point to the new
     * loader. The changes will have an application-wide effect.
     *
     * @param loader the {@link AssetLoader loader} to register with the
     * specified {@link AssetType types}.
     * @param types the asset {@link AssetType types} to associate with the
     * {@link AssetLoader loader}.
     */
    public static final void registerLoader(final AssetLoader loader, final AssetType... types) {
        for (final AssetType t : types) {
            assetLoaders.put(t, loader);
            log.info("Registered {} file type extension with {}]", t.getExt(), loader.getClass().getSimpleName());
        }
    }

    /**
     * Sets the fallback {@link Asset asset} to be used for all assets of the
     * specified {@link AssetType type(s)} when an asset of that type is unable
     * to be loaded for any reason.
     *
     * @param fallback the {@link Asset} to use when unable to load assets of
     * the specified {@link AssetType type(s)}.
     * @param types the {@link AssetType type(s)} to associate with the fallback
     * asset.
     */
    public static final void setFallback(final Asset fallback, final AssetType... types) {
        for (final AssetType type : types) {
            fallbackAssets.put(type, fallback);
        }
    }

    /**
     * Returns the fallback {@link Asset asset} registered for the specified
     * {@link AssetType type}
     *
     * @param type the {@link AssetType type} of asset for which to retrieve the
     * fallback.
     * @return the fallback {@link Asset asset} registered to
     * {@link AssetType type} specified or null if no fallback asset exists for
     * the specified type.
     */
    public static final Asset getFallback(final AssetType type) {
        return fallbackAssets.get(type);
    }

    /**
     * Aborts any pending load tasks, resets the submitted and completed tasks
     * counts to zero, and clears the existing asset cache.
     */
    public static final void restart() {
        abort();
        reset();
        clear();
    }

    /**
     * Resets the three internal atomic counters that are used for tracking the
     * number of requested, loaded, and failed {@link Asset assets}.
     */
    public static final void reset() {
        requested.set(0);
        loaded.set(0);
        failed.set(0);
    }

    /**
     * Clears the {@link AssetCache asset cache}.
     */
    public static final void clear() {
        if (!cachedAssets.isEmpty()) {
            cachedAssets.clear();
            log.debug("Asset cache has been cleared", cachedAssets.size());
        }
    }

    /**
     * Cancels all pending asset loading tasks.
     */
    public static final void abort() {
        if (!pendingAssets.isEmpty()) {
            pendingAssets.entrySet().stream().filter((entry) -> (!entry.getValue().isDone())).forEach((entry) -> {
                entry.getValue().cancel(true);
            });
            log.debug("{} asset loading task(s) have been aborted.", pendingAssets.size());
            threadPool.purge();
        }
    }

    /**
     * Returns the current size of the {@link AssetCache asset cache}.
     *
     * @return the current size of the {@link AssetCache asset cache}.
     */
    public static final int getCacheSize() {
        return cachedAssets.size();
    }

    /**
     * Returns the number of {@link Asset assets} of the specified
     * {@link AssetType type} stored in the {@link AssetCache asset cache}. This
     * method does not take into consideration any assets that have not been
     * completely loaded. The {@link #pendingAssets task cache} will be updated
     * and any completed tasks will have their resulting {@link Asset asset}
     * added to the {@link #cachedAssets assets cache} along with the
     * {@link AssetKey asset key} associated with it before the count is taken.
     *
     * @param type the {@link AssetType type} of {@link Asset asset} to count.
     * @return the number of cached {@link Asset assets} of the specified type.
     */
    public static final int getCacheSize(final AssetType type) {
        return cachedAssets.keySet().stream().filter((key) -> (key.getType().equals(type))).mapToInt(e -> 1).sum();
    }

    /**
     * Indicates if the asset manager is currently executing asset loading
     * tasks.
     *
     * @return true if the asset manager is currently loading assets or false if
     * it has completed all tasks submitted.
     */
    public static final boolean isLoading() {
        return getLoaded() < getRequested();
    }

    /**
     * Returns a float value between 0f and 1f that indicates the percentage of
     * submitted tasks that have completed execution since the last time the
     * {@link #reset()} method was called.
     *
     * @return the percentage of submitted tasks that have completed execution.
     */
    public static final float getProgress() {
        return (getLoaded() < getRequested()) ? (((float) getLoaded()) / ((float) getRequested())) : 1f;
    }

    /**
     * Returns the total number of {@link Asset assets} requested from the
     * manager since the last time {@link #reset reset()} was called.
     *
     * @return the number of assets requested since the last
     * {@link #reset reset}
     */
    public static final int getRequested() {
        return requested.get();
    }

    /**
     * Returns the total number of assets loaded since the last time
     * {@link #reset()} was called.
     *
     * @return the number of assets loaded since the last {@link #reset()}.
     */
    public static final int getLoaded() {
        return loaded.get();
    }

    /**
     * Returns the total number of assets that failed to load since the last
     * time {@link #reset()} was called.
     *
     * @return number of failed attempts to load an asset since the last
     * {@link #reset()}.
     */
    public static final int getFailed() {
        return failed.get();
    }

    /**
     * Returns the total number of threads in the thread pool currently in the
     * process of loading {@link Asset assets}.
     *
     * @return the number of active threads in the thread pool.
     */
    public static final int getActive() {
        return (int) threadPool.getActiveCount();
    }

    /**
     * Indicates the total number of threads in the asset thread pool.
     *
     * @return the total number of threads in the asset thread pool.
     */
    public static final int getPoolSize() {
        return threadPool.getPoolSize();

    }

    /**
     * Private {@link java.util.concurrent.ThreadPoolExecutor thread executor}
     * class modified to use custom settings and help track the number of tasks
     * submitted / completed by updating these volatile fields after the
     * execution of each task.
     */
    private static final class ThreadPool extends ThreadPoolExecutor {

        /**
         * Constructs a thread pool with a number of core threads equal to the
         * number of available processors, or one if the system has only a
         * single processor. The total number of threads will never be greater
         * than the number of set core threads. If no thread is available for a
         * given task the task will be queued in an unbounded
         * {@link LinkedBlockingQueue}. Core threads will automatically time-out
         * and be destroyed after existing for 5 seconds without any work to do.
         * Threads will then be recreated as needed up to the maximum number of
         * core threads originally set.
         */
        public ThreadPool() {
            super((Runtime.getRuntime().availableProcessors() > 1) ? Runtime.getRuntime().availableProcessors() - 1 : 1, Integer.MAX_VALUE, 1, TimeUnit.SECONDS, new LinkedBlockingQueue(), new AssetThreadFactory());
            //super(0, Integer.MAX_VALUE, 10, TimeUnit.SECONDS, new SynchronousQueue<>(), new AssetThreadFactory());
            super.allowCoreThreadTimeOut(true);
        }

        @Override
        public void afterExecute(final Runnable task, final Throwable exceptions) {
            try {
                Asset asset = (Asset) ((Future<?>) task).get();
                cachedAssets.put(asset.getKey(), asset);
                pendingAssets.remove(asset.getKey());
                loaded.incrementAndGet();
            } catch (final InterruptedException | ExecutionException | CancellationException | ClassCastException e) {
                /**
                 * Do nothing here other than increment the failed counter.
                 * Failed assets should remain in the pending assets map to be
                 * later handled when user attempts to retrieve the asset. When
                 * this happens, the get() method will search for fallback
                 * assets and then throw an AssetInitializationException if
                 * necessary.
                 */
                log.warn("Failed to load asset {}", e.getMessage());
                failed.incrementAndGet();
            }
        }
    }

    /**
     * Factory class for generating the daemon threads used for
     * the concurrent loading of {@link Asset assets}.
     */
    private static final class AssetThreadFactory implements ThreadFactory {

        /**
         * Tracks the number of threads created by this factory.
         */
        private int count;

        /**
         * The name prefix to use for each thread created.
         */
        private final String prefix = "Assets-";

        /**
         * Constructs and returns a new daemon asset thread.
         *
         * @param task the runnable task for which to create the thread.
         * @return the constructed daemon thread.
         */
        @Override
        public final Thread newThread(final Runnable task) {
            Thread t = new Thread(task, prefix + count++);
            t.setDaemon(true);
            return t;
        }
    }

    /**
     * Runtime exception thrown when the asset manager is unable to locate the
     * file at the path specified by an {@link AssetKey asset key}.
     */
    public static final class AssetNotFoundException extends RuntimeException {

        /**
         * The {@link AssetKey key} that specifies the path where the missing
         * asset file should be located.
         */
        private final AssetKey key;

        /**
         * Runtime exception thrown when the asset manager is unable to locate
         * the file at the path specified by an {@link AssetKey asset key}.
         *
         * @param key the {@link AssetKey asset key} that specifies the location
         * of the missing asset file.
         */
        public AssetNotFoundException(final AssetKey key) {
            super(key.getAbsolutePath());
            this.key = key;
        }

        /**
         * Returns a human-readable error message along with the path of the
         * {@link AssetKey asset key} that specifies the location of the missing
         * asset file.
         *
         * @return the human-readable error message and the file path location
         * of the missing asset.
         */
        @Override
        public final String getLocalizedMessage() {
            return "Could not located resource at: " + key.getAbsolutePath();
        }
    }

    /**
     * Runtime exception thrown when there is no {@link AssetLoader loader}
     * registered for the {@link AssetType file type extension} of an
     * {@link Asset asset}.
     */
    public static final class UnsupportedAssetTypeException extends RuntimeException {

        /**
         * The {@link AssetKey key} of the {@link Asset asset} that caused the
         * exception.
         */
        private final AssetKey key;

        /**
         * Constructs a new UnsupportAssetException caused by the specified key.
         *
         * @param key the {@link AssetKey key} of the {@link Asset asset} that
         * caused the exception.
         */
        public UnsupportedAssetTypeException(final AssetKey key) {
            this.key = key;
        }

        /**
         * Returns a human-readable error message that returns the path of the
         * {@link AssetKey key} and {@link AssetType type} that caused the
         * exception.
         *
         * @return a human-readable error message appended with the location and
         * file type extension of the {@link Asset asset} that caused the
         * exception.
         */
        @Override
        public final String getLocalizedMessage() {
            return key + " Unsupported Asset Type - " + key.getType();
        }
    }

    /**
     * A runtime exception that occurs when an {@link Asset asset} has a valid
     * file path but is still unable to be loaded.
     */
    public static final class AssetInitializationException extends RuntimeException {

        /**
         * The {@link AssetKey key} associated with the {@link Asset asset}
         * responsible for the initialization error.
         */
        private final AssetKey key;

        /**
         * The reason for the initialization failure.
         */
        private final String reason;

        /**
         * Constructs a new initialization exception caused by the specified
         * {@link AssetKey key} for the specified reason.
         *
         * @param key the {@link AssetKey key} that caused the exception.
         * @param reason the human-readable cause of the exception.
         */
        public AssetInitializationException(final AssetKey key, final String reason) {
            super(reason);
            this.key = key;
            this.reason = reason;
        }

        /**
         * Returns a human-readable error message along with the path of the
         * {@link AssetKey asset key} that specifies the location of the missing
         * asset file and the reason for the exception.
         *
         * @return the human-readable error message and the file path location
         * of the missing asset and the reason for the exception.
         */
        @Override
        public final String getLocalizedMessage() {
            return key + " - " + reason;
        }
    }
}