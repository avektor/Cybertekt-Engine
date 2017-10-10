package net.cybertekt.asset;

import java.util.ArrayList;
import java.util.List;
import net.cybertekt.util.FastMath;
import net.cybertekt.util.HashCache;
import net.cybertekt.util.HashCache.CacheMode;
import net.cybertekt.util.HashCache.MapMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author dal0119
 */
public class HashTest {

    public static final Logger log = LoggerFactory.getLogger(HashTest.class);

    private HashCache<AssetKey, Float> hashCache = new HashCache<>(CacheMode.Soft, MapMode.Concurrent);

    private List<AssetKey> keyList = new ArrayList<>();

    public static void main(final String[] args) {
        HashTest app = new HashTest();
        app.start();
    }

    public void start() {
        long time = System.nanoTime();
        for (int i = 0; i < 1000000; i++) {
            AssetKey k = AssetKey.getKey("Interface/Test/" + i + ".png");
            hashCache.put(k, 1f);
            keyList.add(k);
        }
        keyList.stream().forEach((k) -> {
            hashCache.get(k);
        });
        float elapsed = System.nanoTime() - time;
        log.info("Hash Cache Access Speed - [{}] Keys Cached - Took [{}ms] - [{} k/s]", AssetKey.getKeyCount(), (int) elapsed, (int) elapsed / keyList.size());

    }

    public long getAvgTime() {
        long time = System.nanoTime();
        keyList.stream().forEach((k) -> {
            hashCache.get(k);
        });
        return (System.nanoTime() - time) / keyList.size();
    }

    public long getBestTime() {
        long time = System.nanoTime();
        long best = 5000;
        for (final AssetKey k : keyList) {
            hashCache.get(k);
            if ((System.nanoTime() - time) < best) {
                best = System.nanoTime() - time;
            }
            time = System.nanoTime();
        }
        return best;
    }

}
