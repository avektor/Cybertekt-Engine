package net.cybertekt.display.input;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Input Action - (C) Cybertekt Software
 *
 * An input action defines a input mapping that activates when all of the input
 * events are triggered. The input events are not exclusive or sequential,
 * meaning any combination of events in any order will activate the mapping as
 * long as the input events specified during construction are included. Use the
 * {@link InputSequence input sequence} class for mapping a set of exclusive and
 * sequential input events.
 *
 * @version 1.0.0
 * @since 1.0.0
 * @author Andrew Vektor
 */
public class InputAction implements InputMapping {

    /**
     * The list of inputs that activates the mapping.
     */
    private List<Input> inputs = new ArrayList();

    /**
     * The action event that causes the mapping to activate.
     */
    private Input.State action = Input.State.Pressed;

    /**
     * Internal boolean used to track the activation status of this mapping.
     */
    private boolean activated = false;

    /**
     * Internal boolean that prevents pressed action events from automatically
     * being activated when other keys are released. This ensures that pressed
     * actions only activate the mapping when they are actually pressed, not
     * when they are held down along with another key and the other keys are
     * released.
     */
    private boolean reset = true;

    /**
     * Defines the action and inputs that cause this mapping to activate.
     *
     * @param action the action that causes this mapping to activate.
     * @param inputs the inputs that activate this mapping.
     */
    public InputAction(final Input.State action, final Input... inputs) {
        this.action = action;
        this.inputs = Arrays.asList(inputs);
    }

    /**
     * Returns true when the input action is activated. The input action will
     * activate when all input events as defined in the
     * {@link #inputs inputs array} have been triggered.
     *
     * @param inputMap a map of input events in the order that they were
     * triggered.
     * @return true if all input events defined by the
     * {@link #inputs inputs array} have been triggered sequentially and
     * exclusively.
     */
    @Override
    public final boolean poll(final List<Input> inputMap) {
        /* Enable Reset When All Inputs Have Been Released */
        if (inputMap.isEmpty()) {
            reset = true;
        }

        /* Input Action Activation Logic */
        switch (action) {
            case Pressed: {
                if (reset) {
                    if (inputMap.containsAll(inputs)) {
                        reset = false;
                    }
                    if (!activated) {
                        return activated = inputMap.containsAll(inputs);
                    } else {
                        activated = inputMap.containsAll(inputs);
                        return false;
                    }
                }
                return false;
            }
            case Held: {
                return activated = inputMap.containsAll(inputs);
            }
            case Released: {
                if (!activated) {
                    activated = inputMap.containsAll(inputs);
                    return false;
                } else {
                    return !(activated = inputMap.containsAll(inputs));
                }
            }
        }
        return false;
    }
}
