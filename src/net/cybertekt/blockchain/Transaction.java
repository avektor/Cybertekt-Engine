package net.cybertekt.blockchain;

import java.io.Serializable;
import javax.persistence.Entity;

/**
 *
 * @author Vektor
 */

public abstract class Transaction implements Serializable {
    
    byte[] publicKey, type, data, signature;
    
    public Transaction() {
        
    }
    
    public Transaction(final byte[] publicKey, final byte[] type, final byte[] data, final byte[] signature) {
        this.publicKey = publicKey;
        this.type = type;
        this.data = data;
        this.signature = signature;        
    }
}
