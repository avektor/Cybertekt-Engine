package net.cybertekt.asset;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;
import net.cybertekt.asset.AssetManager.AssetInitializationException;

/**
 * Asset Loader - (C) Cybertekt Software.
 * <p />
 * Defines a contract for constructing {@link Asset asset objects} from an
 * external resource such as an image or audio file.
 *
 * @version 1.0.0
 * @since 1.0.0
 * @author Andrew Vektor
 */
public abstract class AssetLoader {

    protected final Map<AssetType, AssetTask> taskMap = new HashMap<>();

    /**
     * Subclasses must return a callable task that constructs and returns an
     * {@link Asset} object constructed from the provided
     * {@link InputStream stream}.
     *
     * @param key
     * @param stream
     * @return
     */
    public abstract AssetTask newTask(final AssetKey key, final InputStream stream);

    public final Asset loadInline(final AssetKey key, final InputStream stream) throws AssetInitializationException {
        return newTask(key, stream).load();
    }

    public abstract class AssetTask implements Callable {

        protected final AssetKey key;

        protected final InputStream input;

        private Asset asset;

        public AssetTask(final AssetKey key, final InputStream input) {
            this.key = key;
            this.input = input;
        }

        /**
         * Ensures the asset does not get loaded twice if it succeeded the first
         * time.
         *
         * @return
         * @throws AssetInitializationException
         */
        @Override
        public final Asset call() {
            if (asset == null) {
                asset = load();
            }
            return asset;
        }

        public abstract Asset load() throws AssetInitializationException;

        public final AssetKey getKey() {
            return key;
        }

        public final Asset getAsset() {
            return asset;
        }
    }
}
