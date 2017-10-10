package net.cybertekt.util;

/**
 * Timer - (C) Cybertekt Software
 * 
 * @version 1.0.0
 * @since 1.0.0
 * @author Andrew Vektor
 */
public class Timer {
    
    private long time;
    
    public void start() {
        time = System.nanoTime();
    }
}
