package net.cybertekt.crypto;

import net.cybertekt.app.Application;
import net.cybertekt.exception.CryptoException;
import net.cybertekt.util.Encoder;

/**
 *
 * @author Vektor
 */
public class CryptoTest extends Application {

    public CryptoTest() {
        super("Cryptography Unit Test");
    }

    public static void main(final String[] args) {
        CryptoTest app = new CryptoTest();

        /* Set Application Settings */
        app.initialize();
    }

    @Override
    public void init() {
        try {
            /* Create Secret Message */
            String data = "Plaintext Secret Message";
            
            /* Generate Secure RSAKeyPair */
            long time = System.nanoTime();
            RSAKeyPair keyPair = RSAKeyPair.generate(4096);
            log.info("RSA Key Generated [{}ms]: {}", (System.nanoTime() - time) / 1000000, Encoder.toHex(keyPair.getPublic()));
            
            /* Encrypt Data */
            time = System.nanoTime();
            byte [] encryptedData = keyPair.encrypt(data.getBytes());
            log.info("Message Encrypted [{}ms]: {}", (System.nanoTime() - time) / 1000000, Encoder.toHex(encryptedData));
            
            /* Sign the Encrypted Data */
            time = System.nanoTime();
            byte[] signature = keyPair.sign(encryptedData);
            log.info("Message Signed [{}ms]: {}", (System.nanoTime() - time) / 1000000, Encoder.toHex(signature));
            
            /* Verify the Encrypted Data */
            time = System.nanoTime();
            log.info("Signature Verified [{}ms]: {}",(System.nanoTime() - time) / 1000000, keyPair.verify(encryptedData, signature));
            log.info("Signature Imported and Verified [{}ms]: {}", (System.nanoTime() - time) / 1000000, RSAKeyPair.verify(keyPair.getPublic(), encryptedData, signature));
            
            /* Decrypt the Encrypted Data */
            time = System.nanoTime();
            log.info("Message Decrypted [{}ms]: {}", (System.nanoTime() - time) / 1000000, new String(keyPair.decrypt(encryptedData)));
            
        } catch (final CryptoException e) {
            log.error("Fatal Exception: {}\nReason: {}", e.getClass().getName(), e.getMessage());
        }
    }

    @Override
    public void update(final float tpf) {

    }
}
