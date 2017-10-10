package net.cybertekt.crypto;

import java.security.InvalidKeyException;
import java.security.InvalidParameterException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.Signature;
import java.security.SignatureException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import net.cybertekt.exception.CryptoException;

/**
 * RSA Key Pair - (C) Cybertekt Software
 *
 * Provides functions for generating asymmetric key pairs using the RSA
 * algorithm.
 *
 * @version 1.0.0
 * @since 1.0.0
 * @author Andrew Vektor
 */
public class RSAKeyPair {

    /**
     * Encryption Algorithm - RSA
     */
    public static final String ALGORITHM = "RSA";

    /**
     * Stores the generated {@link java.security.KeyPair key pair}.
     */
    private final KeyPair key;

    /**
     * Constructs and returns a new secure RSAKeyPair which can be used to
     * encrypt, decrypt, sign, and verify data in the form of byte[] arrays.
     *
     * @param keySize the size (in bits) o
     * @return
     * @throws CryptoException
     */
    public static final RSAKeyPair generate(final int keySize) throws CryptoException {
        try {
            KeyPairGenerator keyGenerator = KeyPairGenerator.getInstance(ALGORITHM);
            keyGenerator.initialize(keySize, SecureRandom.getInstanceStrong());
            return new RSAKeyPair(keyGenerator.genKeyPair());
        } catch (final NoSuchAlgorithmException | InvalidParameterException e) {
            throw new CryptoException(e.getMessage());
        }
    }

    public static final boolean verify(final byte[] key, final byte[] data, final byte[] signature) {
        try {
            final Signature sig = Signature.getInstance("SHA1withRSA");
            sig.initVerify(KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(key)));
            sig.update(data);
            return sig.verify(signature);
        } catch (final NoSuchAlgorithmException | InvalidKeyException | InvalidKeySpecException | SignatureException e) {
            return false;
        }
    }

    private RSAKeyPair(final KeyPair key) {
        this.key = key;
    }

    public final byte[] encrypt(final byte[] data) throws CryptoException {
        try {
            final Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, key.getPublic());
            return cipher.doFinal(data);
        } catch (final NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException e) {
            throw new CryptoException("Encryption Failed - " + e.getLocalizedMessage());
        }
    }

    public final byte[] decrypt(final byte[] data) throws CryptoException {
        try {
            final Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, key.getPrivate());
            return cipher.doFinal(data);
        } catch (final NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException e) {
            throw new CryptoException("Decryption Failed - " + e.getLocalizedMessage());
        }
    }

    public final byte[] sign(final byte[] data) throws CryptoException {
        try {
            final Signature sig = Signature.getInstance("SHA1withRSA");
            sig.initSign(key.getPrivate());
            sig.update(data);
            return sig.sign();
        } catch (final NoSuchAlgorithmException | InvalidKeyException | SignatureException e) {
            throw new CryptoException("Signature Failed - " + e.getLocalizedMessage());
        }
    }

    public final boolean verify(final byte[] data, final byte[] signature) throws CryptoException {
        try {
            final Signature sig = Signature.getInstance("SHA1withRSA");
            sig.initVerify(key.getPublic());
            sig.update(data);
            return sig.verify(signature);
        } catch (final NoSuchAlgorithmException | InvalidKeyException | SignatureException e) {
            throw new CryptoException("Signature Failed - " + e.getLocalizedMessage());
        }
    }

    public final byte[] getPublic() {
        return key.getPublic().getEncoded();
    }
}
