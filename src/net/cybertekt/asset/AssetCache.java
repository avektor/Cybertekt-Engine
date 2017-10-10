package net.cybertekt.asset;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import net.cybertekt.util.HashCache;
import net.cybertekt.util.HashCache.CacheMode;
import net.cybertekt.util.HashCache.MapMode;

/**
 * Asset Cache - (C) Cybertekt Software
 *
 * Stores {@link Asset assets} in a cache with their corresponding
 * {@link AssetKey keys}.
 *
 * @version 1.0.0
 * @param <T> the {@link AssetType type} of {@link Asset asset} to store in the
 * cache.
 * @since 1.0.0
 * @author Andrew Vektor
 */
public class AssetCache<T extends Asset> {

    /**
     * {@link ConcurrentLinkedQueue Queue} that stores the {@link AssetKey keys}
     * for {@link Asset assets} that have been added to the cache but have not
     * yet been loaded into memory by the {@link AssetLoader loader} associated
     * with the {@link AssetType file type} of the asset.
     */
    private final Queue<AssetKey> loadQueue = new ConcurrentLinkedQueue<>();

    /**
     * {@link HashCache Cache} that stores the {@link Asset assets} that have
     * been loaded into memory by their associated {@link AssetLoader loader}.
     * Assets are indexed by their corresponding {@link AssetKey key}.
     */
    private final HashCache<AssetKey, T> cache = new HashCache<>(CacheMode.Soft, MapMode.Concurrent);

    /**
     * Constructs a new
     *
     * @param keys
     */
    public AssetCache(final AssetKey... keys) {
        for (final AssetKey key : keys) {
            loadQueue.add(key);
        }
    }

    /**
     * Returns and removes the {@link AssetKey asset key} at the head of the
     * {@link AssetCache#loadQueue cache load queue}, or null if the cache load
     * queue is empty.
     *
     * @return the {@link AssetKey asset key} at the head of the cache load
     * queue, or null if the cache load queue is empty.
     */
    protected final AssetKey poll() {
        return loadQueue.poll();
    }

    protected final void cache(final AssetKey key, final Asset asset) {

    }

    /**
     * Returns the {@link Asset asset}
     *
     * @param key
     * @return
     */
    public final T get(final AssetKey key) {
        return cache.get(key);
    }
}
