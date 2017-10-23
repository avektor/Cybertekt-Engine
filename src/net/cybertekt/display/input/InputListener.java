package net.cybertekt.display.input;

import net.cybertekt.display.Display;

/**
 * Input Listener - (C) Cybertekt Software
 *
 * <p>
 * Defines a contract for receiving input events triggered by
 * {@link InputMapping input mappings}.
 *
 * Defines a contract for receiving and responding to action and analog
 * {@link InputMapping input mapping} events transmitted by the
 * {@link Display display} to which the mapping is registered. The
 * {@link InputListener#onAction(net.cybertekt.display.Display, java.lang.String) onAction()}
 * method is called from the input loop once every time the mapping is
 * activated. The onInput() method is called once every update, continously
 * while the mapping is activated.
 *
 * @version 1.0.0
 * @since 1.0.0
 * @author Andrew Vektor
 */
public interface InputListener {

    /**
     * Called when an {@link InputMapping input analog mapping} is activated by
     * the {@link Display display} to which it is registered. Analog mappings
     * are called from the update loop once every frame whilst they remain
     * activated.
     *
     * @param display the {@link Display display} to which the
     * {@link InputMapping input mapping} is registered.
     * @param mapping the registered name of the activated
     * {@link InputMapping mapping}.
     * @param tpf the amount of time that has elapsed since the last update.
     */
    public void onInput(final Display display, final String mapping, final float tpf);

}
