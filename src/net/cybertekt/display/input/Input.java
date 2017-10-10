package net.cybertekt.display.input;

import java.util.Map;

/**
 * Input - (C) Cybertekt Software
 *
 * Defines the Cybertekt Engine input constants, each of which correspond can be
 * used for the construction of {@link InputMapping input mappings}. Defines
 * Cybertekt Engine input constants which can be used for creating
 * {@link InputMapping input mappings} and determining the current
 * {@link State state} of an input.
 *
 *
 * Singleton Design Pattern
 *
 *
 * @version 1.0.0
 * @since 1.0.0
 * @author Andrew Vektor
 */
public interface Input {

    /**
     * Input state constants each represent one of three possible input states.
     */
    public static enum State implements Input {
        /**
         * Pressed is an <b>action</b> event that occurs <b>once</b> each time
         * as soon as an input is pressed from its released (resting) position.
         */
        Pressed,
        /**
         * Held is an <b>analog</b> event that occurs <b>one time each frame</b>
         * while an input is pressed.
         */
        Held,
        /**
         * Released is an <b>action</b> event that occurs <b>once</b> each time
         * an input returns to its resting position from a pressed state.
         */
        Released;
    }

    public static enum Key implements Input {
        /* Input Constant for Unrecognized Keys. (Input Code -99)*/
        Unknown,
        /* Alphabetic Key Input Constants. */
        A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T, U, V, W, X, Y, Z,
        /* Punctuation Key Input Constants. */
        Period, Comma, Apostrophe, Semicolon, Accent, Slash, Backslash, BracketLeft, BracketRight,
        /* Numeric Key Input Constants. */
        Zero, One, Two, Three, Four, Five, Six, Seven, Eight, Nine, Dash, Equals,
        /* Formatting Key Input Constants. */
        Tab, Space, Backspace,
        /* Modifier Key Input Constants. */
        AltLeft, AltRight, CtrlLeft, CtrlRight, ShiftLeft, ShiftRight, SuperLeft, SuperRight,
        /* Named Function Key Input Constants. */
        Enter, Escape, Insert, Delete, Home, End, Last, Menu, Pause, Print, PageUp, PageDown, CapsLock, ScrollLock,
        /* Numbered Function Key Input Constants. */
        F1, F2, F3, F4, F5, F6, F7, F8, F9, F10, F11, F12, F13, F14,
        F15, F16, F17, F18, F19, F20, F21, F22, F23, F24, F25,
        /* Arrow Key Input Constants. */
        Up, Down, Left, Right;
    }

    public static enum Mod implements Input {
        Alt, Ctrl, Shift, Super;
    }

    public static enum Mouse implements Input {
        Left, Right, Middle, Forward, Back, Six, Seven, Eight, Scroll;
    }

    public static enum Numpad implements Input {
        Zero, One, Two, Three, Four, Five, Six, Seven, Eight, Nine,
        Add, Subtract, Multiply, Divide, Equal, Enter, Decimal, Lock;
    }
}
