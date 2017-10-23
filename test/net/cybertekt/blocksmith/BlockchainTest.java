/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.cybertekt.blocksmith;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import net.cybertekt.app.Application;
import net.cybertekt.blockchain.Block;
import net.cybertekt.crypto.RSAKeyPair;
import net.cybertekt.exception.CryptoException;

/**
 *
 * @author Vektor
 */
public class BlockchainTest extends Application {

    public BlockchainTest() {
        super("Blockchain Unit Test");
    }

    public static void main(final String[] args) {
        BlockchainTest app = new BlockchainTest();

        /* Set Application Settings */
        app.initialize();
    }

    @Override
    public void init() {
        /* Connect to the Blockchain DB */
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("$objectdb/blockchains/test.odb");
        EntityManager eM = emf.createEntityManager();

        try {
            /* Create an RSA key pair */
            RSAKeyPair key = RSAKeyPair.generate(512);

            /* Create Transaction Data */
            byte[] data = "This is a transaction".getBytes();

            /* Sign Transaction Data */
            byte[] signature = key.sign(data);

            /* Create A Valid Block */
            Block test = new Block(key.getPublic(), data, signature);

            /* Add The Block To the DB */
            eM.getTransaction().begin();
            eM.persist(test);
            eM.getTransaction().commit();

            long blocks = (long) eM.createQuery("SELECT COUNT(b) FROM Block b").getSingleResult();
            log.info("Total Blocks: {}", blocks);
            
            /* Verify All Blocks */
            int verified = 0;
            long time = System.nanoTime();
            
            for (Block b : eM.createQuery("SELECT b FROM Block b", Block.class).getResultList()) {
                if (b.verify()) {
                    verified++;
                }
            }
            
            log.info("Verified {} of {} Blocks [{}ms]", verified, blocks, (System.nanoTime() - time) / 1000000);

        } catch (final CryptoException e) {
            log.error("{}: {}", e.getClass().getName(), e.getMessage());
        }

        /* Close Blockchain DB Connection */
        eM.close();
        emf.close();

    }

    @Override
    public void update(final float tpf) {

    }

}
