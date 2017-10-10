package net.cybertekt.exception;

/**
 * Initialization Exception - (C) Cybertekt Software
 *
 * Occurs when the application encounters an error while attempting to
 * initialize a critical resource.
 *
 * @version 1.0.0
 * @since 1.0.0
 * @author Andrew Vektor
 */
public class InitializationException extends RuntimeException {

    public InitializationException(final String msg) {
        super(msg);
    }
}
