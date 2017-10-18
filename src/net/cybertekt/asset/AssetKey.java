package net.cybertekt.asset;

import net.cybertekt.util.HashCache;
import net.cybertekt.util.HashCache.CacheMode;
import net.cybertekt.util.HashCache.MapMode;

/**
 * Asset Key - (C) Cybertekt Software.
 * 
 * <p>
 * An asset key is an immutable object that identifies the case-sensitive file
 * location of an external resource relative to the root assets directory as
 * defined in the {@link AssetManager#rootDir} field.
 * </p>
 * 
 * <p>
 * Keys may only be constructed using the static utility method
 * {@link #getKey(java.lang.String)}. Each key is internally cached to guarantee
 * that there is ever only a single key instance for particular resource path. A
 * {@link java.lang.ref.WeakReference} is used to track and manage the cached
 * keys. The key cache is automatically updated to remove obsolete entries
 * whenever the cache is modified. This optimizes them for quick searching and
 * retrieval, especially when used as the key in a {@link java.util.Map} or
 * {@link java.util.IdentityHashMap}.
 * </p>
 *
 * @version 1.0.0
 * @since 1.0.0
 * @author Andrew Vektor
 */
public final class AssetKey {

    /**
     * {@link net.cybertekt.core.util.CacheMap} for storing and managing the
     * keys created by the {@link #getKey(java.lang.String)} static utility
     * method.
     */
    private static final HashCache<String, AssetKey> keyCache = new HashCache(CacheMode.Weak, MapMode.Hash);

    /**
     * Tracks the total number of constructed keys which is incremented and used
     * as the hash code for each unique key constructed. This ensures there are
     * never hash code collisions between keys and optimizes them for use with
     * {@link java.util.HashMap}.
     */
    private static int hashCount = 1;

    /**
     * Static utility method for retrieving the asset key for the external
     * resource located at the specified case-sensitive file path relative to
     * the root assets directory as defined within the
     * {@link AssetManager asset manager}. A new key will be constructed if one
     * does not already exists within the {@link #keyCache}.
     *
     * @param path the location of the file for which to retrieve and asset key.
     * File path locations are relative to the root asset directory as defined
     * within the {@link AssetManager#rootDir asset manager}.
     * @return the asset key associated with the external asset located at the
     * path specified relative to the root assets directory.
     */
    public static final AssetKey getKey(String path) {
        if (path.contains("\\")) {
            path = path.replace('\\', '/');
        }
        AssetKey key = keyCache.get(path);
        if (key == null) {
            keyCache.put(path, (key = new AssetKey(path)));
        }
        return key;
    }

    /**
     * Static utility method for retrieving multiple asset keys for the
     * resources located at the specified file paths relative to the root assets
     * directory as defined within the {@link AssetManager asset manager} class.
     * A new key will be constructed if one does not already exist within the
     * {@link #keyCache}.
     *
     * @param paths the location of the files for which to retrieve an asset
     * key. File path locations are relative to the root asset directory as
     * defined within the {@link AssetManager#rootDir asset manager}.
     * @return an array of asset keys associated with the external assets
     * located at the paths specified relative to the root assets directory.
     */
    public static final AssetKey[] getKeys(final String... paths) {
        AssetKey[] keys = new AssetKey[paths.length];
        for (int i = 0; i < paths.length - 1; i++) {
            keys[i] = AssetKey.getKey(paths[i]);
        }
        return keys;
    }

    /**
     * Static utility method for retrieving multiple asset keys for resources
     * located at the specified file paths with a common base directory. Asset
     * paths should be specified relative to the root assets directory as
     * defined within the {@link AssetManager asset manager}. A new key will be
     * constructed if one does not already exist within the {@link #keyCache}.
     *
     * @param baseDir the common base directory from which to load the assets
     * relative to the root assets directory as defined within the
     * {@link AssetManager asset manager}.
     * @param relativeFilePaths the file paths of the asset resources relative
     * to the root assets directory and the common base directory.
     * @return an array of asset keys associated with t he external assets
     * located at the paths specified relative to the root assets directory and
     * common base directory.
     */
    public static final AssetKey[] getKeys(final String baseDir, final String... relativeFilePaths) {
        AssetKey[] keys = new AssetKey[relativeFilePaths.length];
        for (int i = 0; i < relativeFilePaths.length - 1; i++) {
            keys[i] = AssetKey.getKey(baseDir + relativeFilePaths[i]);
        }
        return keys;
    }

    /**
     * Indicates the total number of keys currently stored within in the
     * {@link #keyCache}.
     *
     * @return the total number of cached keys.
     */
    public static final int getKeyCount() {
        return keyCache.size();
    }

    /**
     * Indicates the location of the external resource, relative to the base
     * assets directory as defined by the {@link AssetManager#rootDir} field.
     */
    private final String path;

    /**
     * Indicates the file type extension of the external resource.
     */
    private final AssetType type;

    /**
     * Stores the hash code assigned to the key during construction.
     */
    private final int hashCode;

    /**
     * Constructs a new key for the file located at the specified path, relative
     * to the base assets directory as defined by the
     * {@link AssetManager#rootDir} field. This constructor is private in order
     * to prevent the construction of duplicate keys. The static utility method
     * {@link #getKey(java.lang.String)} should always be used to retrieve asset
     * key objects. Asset keys cannot be constructed directly from outside of
     * this class.
     *
     * @param assetPath the location of the external file relative to the base
     * assets directory as defined by the {@link AssetManager#rootDir} field.
     */
    private AssetKey(final String assetPath) {
        path = assetPath;
        hashCode = AssetKey.hashCount++;
        if (path.lastIndexOf('.') > -1) {
            type = AssetType.getType(path.substring(path.lastIndexOf('.') + 1, path.length()));
        } else {
            throw new IllegalArgumentException("Malformed asset file path [" + path + "] - Missing required file type extension");
        }
    }

    /**
     * Retrieves the {@link AssetType} for this asset key as defined by the file
     * type extension of the external file.
     *
     * @return the {@link AssetType} for this asset key.
     */
    public final AssetType getType() {
        return type;
    }

    /**
     * Returns the location of the external resource, relative to the base
     * assets directory as defined by the {@link AssetManager#rootDir} field.
     *
     * @return the location of the external resource relative to the base assets
     * directory.
     */
    public final String getPath() {
        return path;
    }

    /**
     * Returns the absolute path of the asset.
     *
     * @return the absolute path of the asset.
     */
    public final String getAbsolutePath() {
        return AssetManager.rootDir + path;
    }

    /**
     * Returns the file name of the asset including the file extension.
     *
     * @return the file name and type extension of the asset.
     */
    public final String getName() {
        return getName(true);
    }

    /**
     * Returns the file name of the asset.
     *
     * @param includeFileExtension true to include the file extension.
     * @return the file name of the asset.
     */
    public final String getName(final boolean includeFileExtension) {
        if (includeFileExtension) {
            return path.substring(path.lastIndexOf('/') + 1);
        }
        return path.substring(path.lastIndexOf('/') + 1, path.lastIndexOf("."));
    }

    /**
     * Returns the file extension of the external resource. The file type
     * extension is defined as the characters that follow the last period in the
     * file name.
     *
     * @return the file type extension of the external resource.
     */
    public final String getExtension() {
        return type.getExt();
    }

    /**
     * Return the asset key file path relative to the
     * {@link AssetManager#rootDir base assets directory}. The String returned
     * by this method appends brackets ('[') to the beginning and end of the
     * file path and therefore should <b>NOT</b> be used to specify the file
     * path to external systems (use {@link #getPath() getPath()} instead. This
     * method is intended for use with logging.
     *
     * @return the asset file path relative to the base assets directory.
     */
    @Override
    public final String toString() {
        return "[" + path + "]";
    }

    /**
     * Two keys are only considered to be equal when both point to the same
     * object instance. As there should only ever be a single instance for a
     * provided file path, the file paths are not directly compared. This
     * greatly increases the speed at which keys can be compared and makes them
     * ideal for use with {@link java.util.IdentityHashMap}.
     *
     * @param o the object with which to compare this asset key.
     * @return true if the provided object is the same instance of asset key as
     * this, false otherwise.
     */
    @Override
    public final boolean equals(final Object o) {
        return o == this;
    }

    /**
     * Override to return the hash code that was assigned to this asset key
     * during construction.
     *
     * @return the hash code assigned to this asset key.
     */
    @Override
    public final int hashCode() {
        return hashCode;
    }
}
