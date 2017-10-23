package net.cybertekt.display.input;

import java.util.List;

/**
 * Input Mapping - (C) Cybertekt Software
 *
 * Defines a contract for implementing input mappings which are attached to a
 * {@link Display display} and triggered by the {@link #poll(List) poll} method.
 *
 * @version 1.0.0
 * @since 1.0.0
 * @author Andrew Vektor
 */
public interface InputMapping {

    public boolean poll(final List<Input> inputMap);
}
