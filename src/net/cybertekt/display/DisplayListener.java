package net.cybertekt.display;

/**
 * Display Listener - (C) Cybertekt Software
 *
 * Defines a contract for receiving and responding to {@link Display display}
 * events such as close requests and resize events.
 *
 * @version 1.0.0
 * @since 1.0.0
 * @author Andrew Vektor
 */
public interface DisplayListener {

    /**
     * Called when a request is made to terminate a {@link Display display}.
     *
     * @param display the target {@link Display display}.
     */
    public abstract void onClose(final Display display);

    /**
     * Called when a {@link Display display} content buffer is resized.
     *
     * @param display the affected {@link Display display}.
     * @param width the new width of the resized display in pixels.
     * @param height the new height of the resized display in pixels.
     */
    public abstract void onResize(final Display display, final int width, final int height);

    /**
     * Called when the position of a {@link Display display} is changed.
     *
     * @param display the affected {@link Display display}.
     * @param xPos the new x-axis position.
     * @param yPos the new y-axis position.
     */
    public abstract void onMove(final Display display, final int xPos, final int yPos);

    /**
     * Called when a request is made to minimize or maximize a
     * {@link Display display}.
     *
     * @param display the affected {@link Display display}.
     * @param iconified true if the {@link Display display} has been minimize,
     * false if the {@link Display display} has been maximized.
     */
    public abstract void onIconify(final Display display, final boolean iconified);

    /**
     * Called when a {@link Display display} gains or loses input focus.
     *
     * @param display the affected {@link Display display}.
     * @param focused true if the {@link Display display} has gained input
     * focus, or false if the {@link Display display} has lost input focus.
     */
    public abstract void onFocus(final Display display, final boolean focused);

    /**
     * Called when the mouse cursor enters or exits the bounds of a
     * {@link Display display}.
     *
     * @param display the affected {@link Display display}.
     * @param entered true if the mouse cursor has entered the
     * {@link Display display}, or false if the mouse cursor has exited the
     * display.
     */
    public abstract void onMouseEnter(final Display display, final boolean entered);
    
    public abstract void onScroll(final Display display, final int amount);
    
    public abstract void onMouseMove(final Display display, final int xPos, final int yPos);

}
