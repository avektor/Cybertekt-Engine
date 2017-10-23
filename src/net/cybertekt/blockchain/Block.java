package net.cybertekt.blockchain;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import net.cybertekt.crypto.RSAKeyPair;

/**
 *
 * @author Vektor
 */
@Entity
public class Block implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @Id @GeneratedValue
    private long id;
    
    private byte[] publicKey;
    
    private byte[] data;
    
    private byte[] signature;
    
    public Block(){};
    
    public Block(final byte[] publicKey, final byte[] data, final byte[] signature) {
        this.publicKey = publicKey;
        this.data = data;
        this.signature = signature;
    }
    
    public long getId() {
        return id;
    }

    public byte[] getPublicKey() {
        return publicKey;
    }
    
    public byte[] getData() {
        return data;
    }

    public byte[] getSignature() {
        return signature;
    }
    
    public boolean verify() {
        return RSAKeyPair.verify(publicKey, data, signature);
    }

}
