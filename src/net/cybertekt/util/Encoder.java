package net.cybertekt.util;

import sun.misc.BASE64Encoder;

/**
 *
 * @author Vektor
 */
public class Encoder {
    
    public static String toHex(final byte[] data) {
        StringBuilder hexKey = new StringBuilder();
        for (int i = 0; i < data.length; ++i) {
            hexKey.append(Integer.toHexString(0x0100 + (data[i] & 0x00FF)).substring(1));
        }
        return hexKey.toString();
    }
    
    public static String toBASE64(final byte[] data) {
        return new BASE64Encoder().encode(data);
    }
}
