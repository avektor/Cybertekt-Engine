package net.cybertekt.display.input;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Input Mapping - (C) Cybertekt Software
 *
 * Immutable class that defines a set of input conditions and provides a
 * {@link InputMapping#poll() poll method} for determining if those input have
 * been met.
 *
 *
 * TODO:
 *
 * FIGURE OUT HOW TO HANDLE ORDERED/UNORDERED MAPPINGS
 *
 * @version 1.0.0
 * @since 1.0.0
 * @author Andrew Vektor
 */
public class InputAction implements InputMapping {

    private List<Input> inputs;

    private Map<Input.Mod, Input.State> modifiers = new HashMap() {
        {
            put(Input.Mod.Alt, Input.State.Released);
            put(Input.Mod.Ctrl, Input.State.Released);
            put(Input.Mod.Shift, Input.State.Released);
            put(Input.Mod.Super, Input.State.Released);
        }
    };

    /**
     * The list of input events that, when triggered, will cause the
     * {@link #poll(java.util.Map)} method to return true.
     */
    private List<Input.State> activators = new ArrayList() {
        {
            add(Input.State.Pressed);
            //add(Input.State.Held);
            add(Input.State.Released);
        }
    };

    private Input.State state = Input.State.Released;

    /**
     * THIS IS THE REAL CONSTRUCTOR
     *
     * @param inputs
     */
    public InputAction(final Input... inputs) {
        this.inputs = Arrays.asList(inputs);
        for (final Input i : inputs) {
            if (i instanceof Input.Mod) {
                modifiers.put((Input.Mod) i, Input.State.Pressed);
            }
        }
    }

    public InputAction when(final Input.State... activators) {
        this.activators = Arrays.asList(activators);
        return this;
    }
    
    @Override
    public final boolean poll(final Map<Input, Input.State> inputMap) {
        if (inputs.stream().allMatch((i) -> (inputMap.get(i) == Input.State.Pressed)) && checkMods(inputMap)) {
            switch (state) {
                case Pressed: {
                    return activators.contains(state = Input.State.Held);
                }
                case Held: {
                    return activators.contains(Input.State.Held);
                }
                case Released: {
                    return activators.contains(state = Input.State.Pressed);
                }
            }
        } else if (state != Input.State.Released) {
            return activators.contains((state = Input.State.Released));
        }
        return false;
    }

    public final Input.State getState() {
        return state;
    }

    /**
     * Should return true if the current state of the input maps modifiers is in
     * line with the mappings modifiers.
     *
     * @param inputMap
     * @return
     */
    protected final boolean checkMods(final Map<Input, Input.State> inputMap) {
        return modifiers.entrySet().stream().noneMatch((m) -> inputMap.get(m.getKey()) != m.getValue());
    }
}
