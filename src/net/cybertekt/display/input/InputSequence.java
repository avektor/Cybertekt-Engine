package net.cybertekt.display.input;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Input Sequence - (C) Cybertekt Software
 *
 * An input sequence defines a set of input events that must be triggered
 * exclusively and in the same order as specified during construction.
 * Exclusivity means that the mapping will not activate if any input events
 * other than those specified during construction have been triggered. The input
 * events must also be triggered in the same order as defined by this mapping.
 *
 * @version 1.0.0
 * @since 1.0.0
 * @author Andrew Vektor
 */
public class InputSequence implements InputMapping {

    /**
     * The list of inputs that activate the mapping.
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
     * Defines the input action and exclusive, sequential set of input events
     * that cause this mapping to activate.
     *
     * @param action the action, or state that activates the mapping.
     * @param inputs the inputs that activate the mapping.
     */
    public InputSequence(final Input.State action, final Input... inputs) {
        this.action = action;
        this.inputs = Arrays.asList(inputs);
    }

    /**
     * Returns true when the input sequence is activated. The input sequence
     * will activate when all input events have been exclusively triggered in
     * the same order as defined in the {@link #inputs inputs array}.
     *
     * @param inputMap a map of input events in the order that they were
     * triggered.
     * @return true if all input events defined by the
     * {@link #inputs inputs array} have been triggered sequentially and
     * exclusively.
     */
    @Override
    public final boolean poll(final List<Input> inputMap) {
        /* Input Sequence Activation Logic */
        switch (action) {
            case Pressed: {
                if (!activated) {
                    return activated = inputMap.equals(inputs);
                } else {
                    activated = inputMap.equals(inputs);
                    return false;
                }
            }
            case Held: {
                return activated = inputMap.equals(inputs);
            }
            case Released: {
                if (!activated) {
                    activated = inputMap.equals(inputs);
                    return false;
                } else {
                    return !(activated = inputMap.equals(inputs));
                }
            }
        }
        return false;
    }
}
