package net.cybertekt.asset;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;
import net.cybertekt.asset.AssetManager.AssetInitializationException;

/**
 * Asset Loader - (C) Cybertekt Software.
 *
 * <p>
 * Defines a contract for constructing specific {@link AssetType types} of
 * {@link Asset assets} from an external resource such as an image or audio
 * file.
 * </p>
 *
 * @version 1.0.0
 * @since 1.0.0
 * @author Andrew Vektor
 */
public abstract class AssetLoader {

    /**
     * Map that stores asset loading tasks based on the type of asset to be
     * loaded.
     */
    protected final Map<AssetType, AssetTask> taskMap = new HashMap<>();

    /**
     * Subclasses must return a callable {@link AssetTask task} which will be
     * used for the concurrent construction of the {@link Asset asset}
     * associated with the specified {@link AssetKey asset key} using the data
     * provided by the {@link InputStream stream}.
     *
     * @param key the {@link AssetKey asset key} associated with the
     * {@link Asset asset} to be loaded.
     * @param stream the input stream for the file located at the path specified
     * by the {@link AssetKey asset key} relative to the root assets directory
     * as defined within the {@link AssetManager#rootDir asset manager}.
     * @return an {@link AssetTask task} for loading the asset associated with
     * the provided {@link AssetKey asset key}.
     */
    public abstract AssetTask newTask(final AssetKey key, final InputStream stream);

    /**
     * Constructs and returns the {@link Asset asset} associated with the
     * provided {@link AssetKey asset key} using the data provided by an input
     * stream.
     *
     * @param key the {@link AssetKey asset key} associated with the
     * {@link Asset asset} to be loaded.
     * @param stream the input stream for the file located at the path specified
     * by the {@link AssetKey asset key} relative to the root assets directory
     * as defined within the {@link AssetManager#rootDir asset manager}.
     * @return the {@link Asset asset} associated with the provided
     * {@link AssetKey asset key} constructed from the data provided by the
     * input stream.
     * @throws net.cybertekt.asset.AssetManager.AssetInitializationException if
     * the {@link Asset asset} cannot be loaded by this loader using the data
     * provided by the input stream.
     */
    public final Asset loadInline(final AssetKey key, final InputStream stream) throws AssetInitializationException {
        return newTask(key, stream).load();
    }

    /**
     * Defines a callable class for constructing {@link Asset assets} supported
     * by this loader.
     */
    public abstract class AssetTask implements Callable {

        /**
         * The {@link AssetKey asset key} associated with the
         * {@link Asset asset} to be loaded.
         */
        protected final AssetKey key;

        /**
         * An input stream that provides the data to be used for constructing
         * the {@link Asset asset}.
         */
        protected final InputStream input;

        /**
         * The {@link Asset asset} loaded by this task.
         */
        private Asset asset;

        /**
         * Constructs a new task for loading the {@link Asset asset} associated
         * with the provided {@link AssetKey asset key} and input stream.
         *
         * @param key the {@link AssetKey asset key} associated with the
         * {@link Asset asset} to be loaded by this task.
         * @param input the input stream containing the data for the external
         * file from which to load the {@link Asset asset}.
         */
        public AssetTask(final AssetKey key, final InputStream input) {
            this.key = key;
            this.input = input;
        }

        /**
         * Begins loading the {@link Asset asset} associated with the
         * {@link AssetKey asset key} and input stream provided to this task
         * during constructing if it has not already been loaded. If the
         * {@link Asset asset} has already been loaded, this method will return
         * the previously loaded asset immediately.
         *
         * @return the {@link Asset asset} loaded by this task.
         * @throws net.cybertekt.asset.AssetManager.AssetInitializationException
         * if the loader is unable to load construct the asset.
         */
        @Override
        public final Asset call() throws AssetInitializationException {
            if (asset == null) {
                asset = load();
            }
            return asset;
        }

        /**
         * Constructs and returns the {@link Asset asset} associated with the
         * {@link AssetKey asset key} and input stream provided to this task
         * during construction.
         *
         * @return the {@link Asset asset} associated with the
         * {@link AssetKey asset key} and input stream provided to this task
         * during construction.
         * @throws net.cybertekt.asset.AssetManager.AssetInitializationException
         * if the loader is unable to construct the asset.
         */
        public abstract Asset load() throws AssetInitializationException;

        /**
         * Returns the {@link AssetKey asset key} associated with the
         * {@link Asset asset} to be loaded by this task.
         *
         * @return the {@link AssetKey asset key} associated with the
         * {@link Asset asset} to be loaded by this task.
         */
        public final AssetKey getKey() {
            return key;
        }

        /**
         * Returns the {@link Asset asset} loaded by this task or null if the
         * asset has not yet been loaded.
         *
         * @return the {@link Asset asset} loaded by this task or null if the
         * asset has not yet been loaded.
         */
        public final Asset getAsset() {
            return asset;
        }
    }
}
