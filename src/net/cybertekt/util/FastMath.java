package net.cybertekt.util;

/**
 * Fast Math - (C) Cybertekt Software.
 * <p />
 * Static utility class that provides methods for efficient mathematical
 * computations and floating-point approximations of the standard java.util.Math
 * functions.
 * 
 * @version 1.0.0
 * @since 1.0.0
 * @author Andrew Vektor
 */
public final class FastMath {

    /**
     * Floating-point PI approximation.
     */
    public static final float PI = 3.14159265f;

    /**
     * Floating-point approximation of (PI * PI).
     */
    public static final float PI2 = 3.14159265f * 3.14159265f;

    /**
     * Half PI floating-point approximation of (PI / 2).
     */
    public static final float HALF_PI = 3.14159265f / 2f;

    /**
     * Quarter PI floating-point approximation (PI / 4).
     */
    public static final float QUARTER_PI = 3.14159265f / 4f;

    /**
     * Floating-point approximation for converting degrees into radians (degrees
     * * DEG_TO_RAD = radians).
     */
    public static final float DEG_TO_RAD = 3.14159265f / 180f;

    /**
     * Floating-point approximation for converting radians into degrees (radians
     * * RAD_TO_DEG = degrees).
     */
    public static final float RAD_TO_DEG = 180f / 3.1415927f;

    /**
     * Calculates a floating-point approximation for the trigonometric sine of
     * the angle (in radians) that is provided by the caller of this method.
     *
     * @param radians the angle in radians.
     * @return the sine of the angle.
     * @see java.lang.Math#sin(double)
     */
    public static final float sin(final float radians) {
        return (float) Math.sin(radians);
    }

    /**
     * Calculates a floating-point approximation for the trigonometric cosine of
     * the angle passed to this method (in radians).
     *
     * @param radians the angle in radians.
     * @return the cosine of the angle.
     * @see java.lang.Math#cos(double)
     */
    public static final float cos(final float radians) {
        return (float) Math.cos(radians);
    }

    /**
     * Calculates a floating-point approximation for the trigonometric tangent
     * of the angle passed to this method (in radians).
     *
     * @param radians the angle in radians.
     * @return the tangent of the angle.
     * @see java.lang.Math#tan(double)
     */
    public static final float tan(final float radians) {
        return (float) Math.tan(radians);
    }

    /**
     * Calculates a floating-point approximation for the trigonometric arcsine
     * of the angle passed to this method (in radians).
     *
     * @param radians the value (in radians) to arcsine.
     * @return the arcsine of the value.
     * @see java.lang.Math#asin(double)
     */
    public static final float asin(final float radians) {
        return (float) Math.asin(radians);
    }

    /**
     * Returns the arccosine of a value in radians.
     *
     * @param radians the value (in radians) to arccosine.
     * @return the arccosine of the value.
     * @see java.lang.Math#acos(double)
     */
    public static final float acos(final float radians) {
        return (float) Math.acos(radians);
    }

    /**
     * Returns the arctangent of a value in radians.
     *
     * @param radians the value (in radians) to arctangent.
     * @return the arc tangent of the value.
     * @see java.lang.Math#atan(double)
     */
    public static final float atan(final float radians) {
        return (float) Math.atan(radians);
    }

    /**
     * Returns the angle theta from the conversion of rectangular coordinates
     * (x, y) to polar coordinates (r, theta).
     *
     * @param y axis coordinate.
     * @param x axis coordinate.
     * @return the angle theta.
     * @see java.lang.Math#atan2(double, double)
     */
    public static final float atan2(final float y, final float x) {
        return (float) Math.atan2(y, x);
    }

    /**
     * Returns the smallest (closest to negative infinity) value that is greater
     * than or equal to the argument and is equal to a mathematical integer.
     * This is currently a direct call to java.lang.Math#ceil(double) but is
     * subject to change in the future if a more efficient method is discovered.
     * Use of this over java.lang.Math() is strongly advised for this reason.
     *
     * @param value the value to round up.
     * @return the smallest value that is greater than or equal to the argument
     * and is equal to a mathematical integer.
     * @see java.lang.Math#ceil(double)
     */
    public static final float ceil(final float value) {
        return (float) Math.ceil(value);
    }

    /**
     * Returns the largest (closest to positive infinity) value that is less
     * than or equal to the argument (value) and also equal to a mathematical
     * integer. This is currently a direct call to java.lang.Math#floor(double)
     * but is subject to change in the future if a more efficient method is
     * discovered. Use of this over java.lang.Math() is strongly advised for
     * this reason.
     *
     *
     * @param value the value to round down.
     * @return the largest value that is less than or equal to the argument and
     * is equal to a mathematical integer.
     * @see java.lang.Math#floor(double)
     */
    public static final float floor(final float value) {
        return (float) Math.floor(value);
    }

    /**
     * Calculates and returns the argument (value) squared.
     *
     * @param value the value to multiply by itself.
     * @return the value squared.
     */
    public static final float squared(final float value) {
        return value * value;
    }

    /**
     * Calculates and returns a floating-point approximation equal to the square
     * root of the provided argument (value). This is currently a direct call to
     * java.lang.Math#sqrt(double) but is subject to change in the future if a
     * more efficient method is discovered. Use of this over java.lang.Math is
     * strongly advised for this reason.
     *
     * @param value the value of which to return the square root.
     * @return the square root of the value.
     * @see java.lang.Math#sqrt(double).
     */
    public static final float sqrt(final float value) {
        return (float) Math.sqrt(value);
    }

    /**
     * Calculates and returns a floating-point approximation equal to the first
     * argument (value) raised by the second argument (exponent). This is
     * currently a direct call to java.lang.Math#pow(double, double) but is
     * subject to change in the future if a more efficient method is discovered.
     * Use of this over java.lang.Math is strongly advised for this reason.
     *
     * @param value the base value.
     * @param exponent the exponent value.
     * @return the value raised by the exponent.
     * @see java.lang.Math#pow(double, double)
     */
    public static final float pow(final float value, final float exponent) {
        return (float) Math.pow(value, exponent);
    }

    /**
     * Returns the closest integer to the argument, with ties rounding to
     * positive infinity.
     *
     * @param value the value to round.
     * @return the rounded value.
     */
    public static final float round(final float value) {
        return (float) Math.round(value);
    }
}
