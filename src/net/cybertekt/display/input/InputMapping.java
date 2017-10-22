package net.cybertekt.display.input;

import java.util.Map;

/**
 * Input Mapping - (C) Cybertekt Software
 * 
 * @version 1.0.0
 * @since 1.0.0
 * @author Andrew Vektor
 */
public interface InputMapping {
    
    public boolean poll(final Map<Input, Input.State> inputMap);
    
    public Input.State getState();
}
