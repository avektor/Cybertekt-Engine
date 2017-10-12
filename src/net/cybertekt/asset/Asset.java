package net.cybertekt.asset;

/**
 * Asset - (C) Cybertekt Software.
 * <p>
 * Asset is the contract for defining an external resource located at the path
 * specified by the {@link AssetKey key} provided during construction. All
 * implementing subclasses should be designed in a way as to be considered
 * effectively immutable. An asset can only be constructed by the loader
 * associated with its {@link AssetType type}. Assets are constructed by
 * {@link AssetLoader loaders} which are registered with the
 * {@link AssetManager asset manager} of the application.
 * </p>
 *
 * @version 1.0.0
 * @since 1.0.0
 * @author Andrew Vektor
 */
public abstract class Asset {

    /**
     * The {@link AssetKey key} that specifies the file path location of the
     * external resource file (relative to the base assets
     * {@link AssetManager#baseDir directory}) and the associated
     * {@link AssetType type} extension of the asset.
     */
    private final AssetKey key;

    /**
     * Constructs an asset with the assigned {@link AssetKey key}.
     *
     * @param key the {@link AssetKey key} assigned to the asset.
     */
    protected Asset(final AssetKey key) {
        this.key = key;
    }

    /**
     * Returns the {@link AssetKey key} that specifies the file path location of
     * the external resource file (relative to the base assets
     * {@link AssetManager#baseDir directory}) and the associated
     * {@link AssetType type} extension of the asset.
     *
     * @return the {@link AssetKey key} associated with this asset.
     */
    public final AssetKey getKey() {
        return key;
    }
}
