package net.cybertekt.asset;

import net.cybertekt.util.HashCache;
import net.cybertekt.util.HashCache.CacheMode;
import net.cybertekt.util.HashCache.MapMode;

/**
 * Asset Type - (C) Cybertekt Software.
 * 
 * <p>
 * Immutable class that defines a file type extension. File type extensions are
 * defined as the characters following the last period in the file name. File
 * extensions are not case-sensitive and will always be stored and displayed in
 * uppercase.
 * </p>
 * 
 * <p>
 * Asset types may only be constructed using the static utility method
 * {@link #getType(java.lang.String)}. They are internally cached which
 * guarantees that there is ever only a single active instance for a particular
 * file extension. A {@link java.lang.ref.WeakReference} is used to track and
 * manage the cached types. The type cache is automatically updated to remove
 * obsolete entries whenever the cache is modified. This optimizes them for
 * quick searching and retrieval, especially when used as the key in a
 * {@link java.util.Map} or {@link java.util.IdentityHashMap}.
 * </p>
 *
 * @version 1.0.0
 * @since 1.0.0
 * @author Andrew Vektor
 */
public final class AssetType {

    /**
     * {@link net.cybertekt.core.util.HashCache} that stores and manages the
     * asset types created by the {@link #getType(java.lang.String)} static
     * utility method.
     */
    private static final HashCache<String, AssetType> typeCache = new HashCache<>(CacheMode.Weak, MapMode.Hash);

    /**
     * Tracks the total number of constructed types, which is incremented and
     * used as the hash code for each unique type constructed. This ensures
     * there are never hash code collisions between types and optimizes them for
     * use with {@link java.util.HashMap}.
     */
    private static int hashCount = 1;

    /**
     * Static utility method for retrieving the asset type for the specified
     * file type extension. File type extensions are <b>not</b> case-sensitive
     * and are automatically converted to uppercase characters. A new type will
     * be constructed if one does not already exist for the specified extension
     * within the {@link #typeCache}.
     *
     * @param extension the file extension for which to retrieve the asset type.
     * @return the asset type for the specified file type extension.
     */
    public static final AssetType getType(final String extension) {
        updateCache();
        String ext = extension.toUpperCase();
        AssetType type = typeCache.get(ext);
        if (type == null) {
            typeCache.put(ext, (type = new AssetType(ext)));
        }
        return type;
    }

    /**
     * Indicates the total number of types currently cached in the internal
     * {@link #typeCache}.
     *
     * @return the total number of cached types.
     */
    public static final int getTypeCount() {
        return typeCache.size();
    }

    /**
     * Call to manually update the internal type cache which will purge obsolete
     * (unused) AssetType objects from the static type cache. AssetType objects
     * become obsolete when they no longer have any strong references.
     */
    public static void updateCache() {
        typeCache.update();
    }

    /**
     * Indicates the file type extension of the external resource. The file type
     * extension is defined as the characters that follow the last period in the
     * file name.
     */
    private final String ext;

    /**
     * Stores the hash code assigned to this type during construction.
     */
    private final int hashCode;

    /**
     * Constructs a new type for the specified file extension. This constructor
     * is private in order to prevent the construction of duplicate types. The
     * static utility method {@link #getType(java.lang.String)} should always be
     * used to retrieve asset type objects. Asset types cannot be constructed
     * directly from outside of this class.
     *
     * @param extension the file type extension for which to create the type.
     */
    private AssetType(final String extension) {
        ext = extension;
        hashCode = AssetType.hashCount++;
    }

    /**
     * Returns the file type extension of the external resource. The file type
     * extension is defined as the characters that follow the last period in the
     * file name.
     *
     * @return the file type extension defined by this asset type.
     */
    public final String getExt() {
        return ext;
    }

    /**
     * Return the asset type file extension with brackets ('[') appended to the
     * beginning and end of the file extension. This method is intended mainly
     * for use with logging.
     *
     * @return the asset file path relative to the base assets directory.
     */
    @Override
    public final String toString() {
        return "[" + ext + "]";
    }

    /**
     * Two types are only considered to be equal when both reference the same
     * object. Extension Strings are not directly compared as there should only
     * ever be a single instance for a specific file extension. This greatly
     * increases the speed at which types can be compared and retrieved, making
     * them ideal for use as the key in a {@link java.util.Map}.
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
     * Returns the hash code that was assigned to this asset type
     * during construction.
     *
     * @return the hash code assigned to this asset type.
     */
    @Override
    public final int hashCode() {
        return hashCode;
    }
}
