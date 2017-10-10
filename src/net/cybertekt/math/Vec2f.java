package net.cybertekt.math;

import net.cybertekt.util.FastMath;

/**
 * Vector2f - (C) Cybertekt Software.
 *
 * Defines a vector that consists of two floating-point components stored
 * internally as {@link #x X} and {@link #y Y}. Methods are provided for
 * performing mathematical operations between this vector and other two
 * dimensional vectors.
 *
 * @version 1.0.0
 * @since 1.0.0
 * @author Andrew Vektor
 */
public final class Vec2f {

    /**
     * Vector in which the sum of its components equal zero. Also known as a
     * zero length or empty vector.
     */
    public transient static final Vec2f ZERO = new Vec2f(0f, 0f);

    /**
     * Vector in which the y component is equal to zero and the x component
     * equals one. Also known as a 'unit' or 'normalized' vector.
     */
    public transient static final Vec2f UNIT_X = new Vec2f(1f, 0f);

    /**
     * Vector in which the x component is equal to zero and the y component
     * equals one. Also known as a 'unit' or 'normalized' vector.
     */
    public transient static final Vec2f UNIT_Y = new Vec2f(0f, 1f);

    /**
     * Two floating-point components that define the dimensions of this vector.
     */
    protected float x, y;

    /**
     * Constructs a two dimensional vector in which the sum of its components
     * equal zero. Also known as a zero length or empty vector.
     */
    public Vec2f() {
        x = y = 0;
    }

    /**
     * Constructs a two dimensional vector from two floating-point values.
     *
     * @param xToSet the x component of the vector.
     * @param yToSet the y component of the vector.
     */
    public Vec2f(final float xToSet, final float yToSet) {
        x = xToSet;
        y = yToSet;
    }

    /**
     * Constructs a two dimensional vector defined by copying the values from
     * another vector. The provided vector is not internally modified in any
     * way. Passing null arguments into this constructor is not permitted.
     *
     * @param toCopy the vector from which to copy.
     * @throws NullPointerException if the vector argument passed into this
     * constructor is null.
     */
    public Vec2f(final Vec2f toCopy) {
        x = toCopy.x;
        y = toCopy.y;
    }

    /**
     * Generates a vector defined by adding together a vector to this vector.
     * Neither this vector nor the provided vector are internally modified in
     * any way. Passing null arguments into this method is not permitted. This
     * method creates and returns a new vector object each time it is called.
     * <br /><br />
     * Simple description: new vec2f(this.x + other.x, this.y + other.y)
     *
     * @param toAdd the vector in which to add to this vector.
     * @return a vector equal to the sum of this vector and another vector. F
     * @throws NullPointerException if the vector argument passed into this
     * method is null.
     */
    public final Vec2f add(final Vec2f toAdd) {
        return new Vec2f(x + toAdd.x, y + toAdd.y);
    }

    /**
     * Generates a vector defined by adding two floating-point values to this
     * vector. This vector is not internally modified in any way by this method.
     * A new vector object is created and returned each time this method is
     * called.
     * <br /><br />
     * Simple description: new vec2f(this.x + xToAdd, this.y + yToAdd)
     *
     * @param xToAdd amount to add to the x component of this vector.
     * @param yToAdd amount to add to the y component of this vector.
     * @return the generated vector.
     */
    public final Vec2f add(final float xToAdd, final float yToAdd) {
        return new Vec2f(x + xToAdd, y + yToAdd);
    }

    /**
     * Generates a vector defined by adding a value to each component of this
     * vector. This vector is not internally modified in any way by this method.
     * A new vector object is created and returned each time this method is
     * called.
     * <br /><br />
     * Simple description: new vec2f(this.x + amount, this.y + amount)
     *
     * @param amount the amount to add to each component of this vector.
     * @return the generated vector.
     */
    public final Vec2f add(final float amount) {
        return new Vec2f(x + amount, y + amount);
    }

    /**
     * Calculates the sum of this vector and another vector, storing the result
     * in the second 'storage' vector. Neither this vector nor the first vector
     * argument are internally modified in any way by this method. Passing null
     * arguments into this method is not permitted. Use this method whenever
     * possible to avoid creating new vector objects. A reference to the second
     * 'storage' vector is returned for the purpose of call chaining.
     * <br /><br />
     * Simple description: storage(this.x + other.x, this.y + other.y)
     *
     * @param toAdd the vector in which to add to this vector.
     * @param toStore the vector in which to store the result.
     * @return the stored vector.
     * @throws NullPointerException if the vector argument passed into this
     * method is null.
     */
    public final Vec2f add(final Vec2f toAdd, final Vec2f toStore) {
        return toStore.set(x + toAdd.x, y + toAdd.y);
    }

    /**
     * Internally adds a vector to this vector. The vector passed into this
     * method is not internally modified in any way. Passing null arguments into
     * this method is not permitted. This method does not create any additional
     * objects and returns a reference to this vector for the purpose of call
     * chaining.
     * <br /><br />
     * Simple description:<br />this(this.x + other.x, this.y + other.y)
     *
     * @param toAdd the vector in which to add to this vector.
     * @return this vector.
     * @throws NullPointerException if the vector argument passed into this
     * method is null.
     */
    public final Vec2f addLocal(final Vec2f toAdd) {
        x += toAdd.x;
        y += toAdd.y;
        return this;
    }

    /**
     * Internally adds two floating-point values to this vector. This method
     * does not create any additional objects and returns a reference to this
     * vector for the purpose of call chaining.
     * <br /><br />
     * Simple description:<br />this(this.x + xToAdd, this.y + yToAdd)
     *
     * @param xToAdd the amount to add to the x component of this vector.
     * @param yToAdd the amount to add to the y component of this vector.
     * @return this vector
     */
    public final Vec2f addLocal(final float xToAdd, final float yToAdd) {
        x += xToAdd;
        y += yToAdd;
        return this;
    }

    /**
     * Internally adds a value to each component of this vector. This method
     * does not create any additional objects and returns a reference to this
     * vector for the purpose of call chaining.
     * <br /><br />
     * Simple description:<br />this(this.x + amount, this.y + amount)
     *
     * @param amount the amount to add to each component of this vector.
     * @return this vector.
     */
    public final Vec2f addLocal(final float amount) {
        x += amount;
        y += amount;
        return this;
    }

    /**
     * Generates a vector defined by the difference between this vector and
     * another vector. Neither this vector nor the provided vector are
     * internally modified in any way. Passing null arguments into this method
     * is not permitted. This method creates and returns a new vector object
     * each time it is called.
     * <br /><br />
     * Simple description: new vec2f(this.x - other.x, this.y - other.y)
     *
     * @param toSubtract the vector in which to subtract from this vector.
     * @return the generated vector.
     * @throws NullPointerException if the vector argument passed into this
     * method is null.
     */
    public final Vec2f subtract(final Vec2f toSubtract) {
        return new Vec2f(x - toSubtract.x, y - toSubtract.y);
    }

    /**
     * Generates a vector defined by the difference between this vector and two
     * floating-point values. This vector is not internally modified in any way
     * by this method. A new vector object is created and returned each time
     * this method is called.
     * <br /><br />
     * Simple description: new vec2f(this.x - xToSubtract, this.y + yToSubtract)
     *
     * @param xToSubtract amount to subtract from the x component.
     * @param yToSubtract amount to subtract from the y component.
     * @return the generated vector.
     */
    public final Vec2f subtract(final float xToSubtract, final float yToSubtract) {
        return new Vec2f(x - xToSubtract, y - yToSubtract);
    }

    /**
     * Generates a vector defined by subtracting a value from each component of
     * this vector. This vector is not internally modified in any way by this
     * method. A new vector object is created and returned each time this method
     * is called.
     * <br /><br />
     * Simple description: new vec2f(this.x - amount, this.y - amount)
     *
     * @param amount the amount to subtract from each component of this vector.
     * @return the generated vector.
     */
    public final Vec2f subtract(final float amount) {
        return new Vec2f(x - amount, y - amount);
    }

    /**
     * Calculates the difference between this vector and another vector, storing
     * the result in the second 'storage' vector. Neither this vector nor the
     * first vector argument is internally modified in any way by this method.
     * Passing null arguments into this method is not permitted. Use this method
     * whenever possible to avoid creating new vector objects. A reference to
     * the second 'storage' vector is returned for the purpose of call chaining.
     * <br /><br />
     * Simple description: storage(this.x - other.x, this.y - other.y)
     *
     * @param toSubtract the vector to subtract from this vector.
     * @param toStore the vector in which to store the result.
     * @return the stored vector.
     * @throws NullPointerException if the vector argument passed into this
     * method is null.
     */
    public final Vec2f subtract(final Vec2f toSubtract, final Vec2f toStore) {
        return toStore.set(x - toSubtract.x, y - toSubtract.y);
    }

    /**
     * Internally subtracts a vector from this vector. The vector passed into
     * this method is not internally modified in any way. Passing null arguments
     * into this method is not permitted. This method does not create any
     * additional objects and returns a reference to this vector for the purpose
     * of call chaining.
     * <br /><br />
     * Simple description:<br />this(this.x - other.x, this.y - other.y)
     *
     * @param toSubtract the vector to subtract from this vector.
     * @return this vector.
     * @throws NullPointerException if the vector argument passed into this
     * method is null.
     */
    public final Vec2f subtractLocal(final Vec2f toSubtract) {
        x -= toSubtract.x;
        y -= toSubtract.y;
        return this;
    }

    /**
     * Internally subtracts two floating-point values from this vector. This
     * method does not create any additional objects and returns a reference to
     * this vector for the purpose of call chaining.
     * <br /><br />
     * Simple description:<br />this(this.x + xToAdd, this.y + yToAdd)
     *
     * @param xToSubtract the amount to subtract from the x component.
     * @param yToSubtract the amount to subtract from the y component.
     * @return this vector
     */
    public final Vec2f subtractLocal(final float xToSubtract, final float yToSubtract) {
        x -= xToSubtract;
        y -= yToSubtract;
        return this;
    }

    /**
     * Internally subtracts a value from each component of this vector. This
     * method does not create any additional objects and returns a reference to
     * this vector for the purpose of call chaining.
     *
     * @param amount the amount to subtract from each component of this vector.
     * @return this vector.
     */
    public final Vec2f subtractLocal(final float amount) {
        x -= amount;
        y -= amount;
        return this;
    }

    /**
     * Generates a vector defined by multiplying this vector by another vector.
     * Neither this vector nor the provided vector are internally modified in
     * any way. Passing null arguments into this method is not permitted. This
     * method creates and returns a new vector object each time it is called.
     * <br /><br />
     * Simple description: new vec2f(this.x * other.x, this.y * other.y)
     *
     * @param toMult the vector by which to multiply this vector.
     * @return a vector equal to the product of this vector and another vector.
     * @throws NullPointerException if the vector argument passed into this
     * method is null.
     */
    public final Vec2f mult(final Vec2f toMult) {
        return new Vec2f(x * toMult.x, y * toMult.y);
    }

    /**
     * Generates a vector defined by multiplying this vector by two
     * floating-point values. This vector is not internally modified in any way
     * by this method. A new vector object is created and returned each time
     * this method is called.
     * <br /><br />
     * Simple description: new vec2f(this.x * xToMult, this.y + yToMult)
     *
     * @param xToMult amount by which to multiply the x component.
     * @param yToMult amount by which to multiply the y component.
     * @return the generated vector.
     */
    public final Vec2f mult(final float xToMult, final float yToMult) {
        return new Vec2f(x * xToMult, y * yToMult);
    }

    /**
     * Generates a vector defined by multiplying each component by a scalar
     * amount. This vector is not internally modified in any way by this method.
     * A new vector object is created and returned each time this method is
     * called.
     * <br /><br />
     * Simple description: new vec2f(this.x * amount, this.y * amount)
     *
     * @param amount the amount by which to multiply each component.
     * @return the generated vector.
     */
    public final Vec2f mult(final float amount) {
        return new Vec2f(x * amount, y * amount);
    }

    /**
     * Calculates the product of this vector and another vector, storing the
     * result in the second 'storage' vector. Neither this vector nor the first
     * vector argument are internally modified in any way by this method.
     * Passing null arguments into this method is not permitted. Use this method
     * whenever possible to avoid creating new vector objects. A reference to
     * the second 'storage' vector is returned for the purpose of call chaining.
     * <br /><br />
     * Simple description: storage(this.x * other.x, this.y * other.y)
     *
     * @param toMult the vector by which to multiply this vector.
     * @param toStore the vector in which to store the result.
     * @return the stored vector.
     * @throws NullPointerException if either vector argument passed into this
     * method is null.
     */
    public final Vec2f mult(final Vec2f toMult, final Vec2f toStore) {
        return toStore.set(x * toMult.x, y * toMult.y);
    }

    /**
     * Internally multiplies this vector by another vector. The vector passed
     * into this method is not internally modified in any way. Passing null
     * arguments into this method is not permitted. This method does not create
     * any additional objects and returns a reference to this vector for the
     * purpose of call chaining.
     * <br /><br />
     * Simple description:<br />this(this.x * other.x, this.y * other.y)
     *
     * @param toMult the vector by which to multiply this vector.
     * @return this vector.
     * @throws NullPointerException if the vector argument passed into this
     * method is null.
     */
    public final Vec2f multLocal(final Vec2f toMult) {
        x *= toMult.x;
        y *= toMult.y;
        return this;
    }

    /**
     * Internally multiplies this vector by two floating-point values. This
     * method does not create any additional objects and returns a reference to
     * this vector for the purpose of call chaining.
     * <br /><br />
     * Simple description:<br />this(this.x * xToMult, this.y * yToMult)
     *
     * @param xToMult the amount by which to multiply the x component.
     * @param yToMult the amount by which to multiply the y component.
     * @return this vector
     */
    public final Vec2f multLocal(final float xToMult, final float yToMult) {
        x *= xToMult;
        y *= yToMult;
        return this;
    }

    /**
     * Internally multiplies each component of this vector by a scalar. This
     * method does not create any additional objects and returns a reference to
     * this vector for the purpose of call chaining.
     * <br /><br />
     * Simple description:<br />this(this.x * amount, this.y * amount)
     *
     * @param amount the amount by which to multiply each component.
     * @return this vector.
     */
    public final Vec2f multLocal(final float amount) {
        x *= amount;
        y *= amount;
        return this;
    }

    /**
     * Generates a vector defined by dividing this vector by another vector.
     * Neither this vector nor the provided vector are internally modified in
     * any way. Passing null arguments into this method is not permitted. This
     * method creates and returns a new vector object each time it is called.
     * <br /><br />
     * Simple description: new vec2f(this.x / other.x, this.y / other.y)
     *
     * @param toDiv the vector by which to divide this vector.
     * @return a vector equal to the quotient of this vector and another vector.
     * @throws NullPointerException if the vector argument passed into this
     * method is null.
     */
    public final Vec2f divide(final Vec2f toDiv) {
        return new Vec2f(x / toDiv.x, y / toDiv.y);
    }

    /**
     * Generates a vector defined by dividing this vector by two floating-point
     * values. This vector is not internally modified in any way by this method.
     * A new vector object is created and returned each time this method is
     * called.
     * <br /><br />
     * Simple description: new vec2f(this.x / xToDiv, this.y / yToDiv)
     *
     * @param xToDiv amount by which to divide the x component.
     * @param yToDiv amount by which to divide the y component.
     * @return the generated vector.
     */
    public final Vec2f divide(final float xToDiv, final float yToDiv) {
        return new Vec2f(x / xToDiv, y / yToDiv);
    }

    /**
     * Generates a vector defined by dividing each component by a value. This
     * vector is not internally modified in any way by this method. A new vector
     * object is created and returned each time this method is called.
     * <br /><br />
     * Simple description: new vec2f(this.x / amount, this.y / amount)
     *
     * @param amount the amount by which to divide each component.
     * @return the generated vector.
     */
    public final Vec2f divide(final float amount) {
        return new Vec2f(x / amount, y / amount);
    }

    /**
     * Calculates the quotient of this vector and another vector, storing the
     * result in the second 'storage' vector. Neither this vector nor the first
     * vector argument are internally modified in any way by this method.
     * Passing null arguments into this method is not permitted. Use this method
     * whenever possible to avoid creating new vector objects. A reference to
     * the second 'storage' vector is returned for the purpose of call chaining.
     * <br /><br />
     * Simple description: storage(this.x / other.x, this.y / other.y)
     *
     * @param toDiv the vector by which to divide this vector.
     * @param toStore the vector in which to store the result.
     * @return the stored vector.
     * @throws NullPointerException if either vector argument passed into this
     * method is null.
     */
    public final Vec2f divide(final Vec2f toDiv, final Vec2f toStore) {
        return toStore.set(x / toDiv.x, y / toDiv.y);
    }

    /**
     * Internally divides this vector by another vector. The vector passed into
     * this method is not internally modified in any way. Passing null arguments
     * into this method is not permitted. This method does not create any
     * additional objects and returns a reference to this vector for the purpose
     * of call chaining.
     * <br /><br />
     * Simple description:<br />this(this.x / other.x, this.y / other.y)
     *
     * @param toDiv the vector by which to divide this vector.
     * @return this vector.
     * @throws NullPointerException if the vector argument passed into this
     * method is null.
     */
    public final Vec2f divideLocal(final Vec2f toDiv) {
        x /= toDiv.x;
        y /= toDiv.y;
        return this;
    }

    /**
     * Internally divides this vector by two floating-point values. This method
     * does not create any additional objects and returns a reference to this
     * vector for the purpose of call chaining.
     * <br /><br />
     * Simple description:<br />this(this.x / xToDiv, this.y / yToDiv)
     *
     * @param xToDiv the amount by which to divide the x component.
     * @param yToDiv the amount by which to divide the y component.
     * @return this vector
     */
    public final Vec2f divideLocal(final float xToDiv, final float yToDiv) {
        x /= xToDiv;
        y /= yToDiv;
        return this;
    }

    /**
     * Internally divides each component of this vector by a value. This method
     * does not create any additional objects and returns a reference to this
     * vector for the purpose of call chaining.
     * <br /><br />
     * Simple description:<br />this(this.x / amount, this.y / amount)
     *
     * @param amount the amount by which to divide each component.
     * @return this vector.
     */
    public final Vec2f divideLocal(final float amount) {
        x /= amount;
        y /= amount;
        return this;
    }

    /**
     * Generates a vector interpolated by an amount between this vector and
     * another vector. Neither this vector nor the first vector argument are
     * internally modified in any way by this method. Passing null arguments
     * into this method is not permitted. A new vector object is created and
     * returned each time this method is called.
     *
     * @param toVector the vector towards which to interpolate.
     * @param interpolateAmount the amount by which to interpolate.
     * @return the generated vector.
     */
    public final Vec2f interpolate(final Vec2f toVector, final float interpolateAmount) {
        return new Vec2f((1f - interpolateAmount) * x + interpolateAmount * toVector.x, (1f - interpolateAmount) * y + interpolateAmount * toVector.y);
    }

    /**
     * Internally interpolates this vector by an amount between this vector and
     * another vector. The vector passed into this method is not internally
     * modified in any way. Passing null arguments into this method is not
     * permitted. This method does not create any additional objects and returns
     * a reference to this vector for the purpose of call chaining.
     *
     * @param toVector the vector towards which to interpolate.
     * @param interpolateAmount the amount by which to interpolate.
     * @return this vector.
     */
    public final Vec2f interpolateLocal(final Vec2f toVector, final float interpolateAmount) {
        x = (1f - interpolateAmount) * x + interpolateAmount * toVector.x;
        y = (1f - interpolateAmount) * y + interpolateAmount * toVector.y;
        return this;
    }

    /**
     * Creates a copy of this vector and multiplies it by negative one. A new
     * vector object is created and returned each time this method is called.
     *
     * @return a copy of this vector multiplied by -1.
     */
    public final Vec2f negate() {
        return new Vec2f(this).multLocal(-1);
    }

    /**
     * Internally multiplies each component of this vector by negative one. This
     * method does not create any additional objects and returns a reference to
     * this vector for the purpose of call chaining.
     *
     * @return this vector.
     */
    public final Vec2f negateLocal() {
        setX(-x);
        setY(-y);
        return this;
    }

    /**
     * Generates a copy of this vector truncated by a limit. If the length
     * (magnitude) of this vector is greater than the limit, the vector will be
     * normalized and then multiplied by the limit. A new vector object is
     * created and returned each time this method is called.
     *
     * @param limit the length limit of the vector.
     * @return the generated vector.
     */
    public final Vec2f truncate(final float limit) {
        if (length() <= limit) {
            return new Vec2f(this);
        } else {
            return normalize().multLocal(limit);
        }
    }

    /**
     * Internally truncates this vector to ensure that its length (magnitude)
     * does not exceed a limit. If the length (magnitude) of this vector is
     * greater than the limit, the vector will be normalized and then multiplied
     * by the limit. Returns a reference to this vector for the purpose of call
     * chaining.
     *
     * @param limit the length limit of this vector.
     * @return this
     */
    public final Vec2f truncateLocal(final float limit) {
        if (length() <= limit) {
            return this;
        } else {
            return normalizeLocal().multLocal(limit);
        }
    }

    /**
     * Generates a copy of this vector normalized so that the sum of all its
     * components equal one. A new vector object is created and returned each
     * time this method is called.
     *
     * @return this vector as a unit vector.
     */
    public final Vec2f normalize() {
        return new Vec2f(this).truncateLocal(1f);
    }

    /**
     * Internally normalizes this vector so that the sum of all its components
     * equal one. This method is equivalent to calling truncateLocal(1f).
     *
     * @return this vector.
     */
    public final Vec2f normalizeLocal() {
        truncateLocal(1f);
        return this;
    }

    /**
     * Generates a copy of this value with the components rounded to the closest
     * integer with ties rounding up.
     *
     * @return the generated vector.
     */
    public final Vec2f round() {
        return new Vec2f(FastMath.round(x), FastMath.round(y));
    }

    /**
     * Internally rounds the components of this vector to the closest integer
     * with ties rounding up.
     *
     * @return this vector.
     */
    public final Vec2f roundLocal() {
        x = FastMath.round(x);
        y = FastMath.round(y);
        return this;
    }

    /**
     * Calculates the dot product of this vector and another vector. The vector
     * passed into this method is not internally modified in any way. Passing
     * null arguments into this method is not permitted. This method does not
     * create any additional objects.
     *
     * @param toDot the vector to dot with this vector.
     * @return the dot product of this vector and the parameter vector.
     */
    public final float dot(final Vec2f toDot) {
        return (x * toDot.x) + (y * toDot.y);
    }

    /**
     * Calculates the angle, in radians, between this vector and another vector.
     * The vector passed into this method is not internally modified in any way.
     * Passing null arguments into this method is not permitted. This method
     * does not create any additional objects.
     *
     * @param other the other vector in which to find the angle.
     * @return the angle between this vector and the other vector.
     */
    public final float angleBetween(final Vec2f other) {
        return FastMath.acos(dot(other));
    }

    /**
     * Calculates the length of this vector.
     *
     * @return the length (magnitude) of this vector.
     */
    public final float length() {
        return FastMath.sqrt((x * x) + (y * y));
    }

    /**
     * Calculates the length of this vector, squared.
     *
     * @return the length (magnitude) of this vector, squared.
     */
    public final float lengthSquared() {
        return (x * x) + (y * y);
    }

    /**
     * Calculates the distance between this vector and another vector. The
     * vector passed into this method is not internally modified in any way.
     * Passing null arguments into this method is not permitted. This method
     * does not create any additional objects.
     *
     * @param otherVector the other vector to calculate the distance between.
     * @return the distance between this vector and another vector squared.
     */
    public final float distance(final Vec2f otherVector) {
        return (x - otherVector.x) + (y - otherVector.y);
    }

    /**
     * Calculates the distance squared between this vector and another vector.
     * The vector passed into this method is not internally modified in any way.
     * Passing null arguments into this method is not permitted. This method
     * does not create any additional objects.
     *
     * @param otherVector the vector to calculate the distance between.
     * @return the distance between this vector and another vector squared.
     */
    public final float distanceSquared(final Vec2f otherVector) {
        return ((x - otherVector.x) * (x - otherVector.x)) + ((y - otherVector.y) * (y - otherVector.y));
    }

    /**
     * Indicates if this vector is a normalized (unit) vector.
     *
     * @return true if the length of this vector is approximately equal to one
     * or false if is not.
     */
    public final boolean isNormalized() {
        return length() < 0.99f || length() < 1.01f;
    }

    /**
     * Internally sets the x and y component of this vector. Returns a reference
     * to this vector for the purpose of call chaining.
     *
     * @param xToSet the x component to set.
     * @param yToSet the y component to set.
     * @return this vector.
     */
    public final Vec2f set(final float xToSet, final float yToSet) {
        x = xToSet;
        y = yToSet;
        return this;
    }

    /**
     * Internally sets the components of this vector to be equal to another
     * vector. Returns a reference to this vector for the purpose of call
     * chaining.
     *
     * @param toCopy the vector from which to copy the components.
     * @return this vector
     */
    public final Vec2f set(final Vec2f toCopy) {
        x = toCopy.x;
        y = toCopy.y;
        return this;
    }

    /**
     * Internally sets the x component of this vector.
     *
     * @param xToSet the x component to set.
     */
    public final void setX(final float xToSet) {
        x = xToSet;
    }

    /**
     * Return the x component of this vector.
     *
     * @return the x component.
     */
    public final float getX() {
        return x;
    }

    /**
     * Internally sets the y component of this vector.
     *
     * @param yToSet the y component to set.
     */
    public final void setY(final float yToSet) {
        y = yToSet;
    }

    /**
     * Returns the y component of this vector.
     *
     * @return the y component of this vector.
     */
    public final float getY() {
        return y;
    }

    /**
     * Indicates if the specified object is equal to this vector. This method
     * returns true if and only if the provided object is an instance of
     * Vector2f and its {@link #x X} and {@link #y Y} components are equal to
     * the X and Y components of this vector.
     *
     * @param obj the object to compare with this vector.
     * @return true if the object is an instance of Vector2f and is equal to
     * this vector, false otherwise.
     */
    @Override
    public final boolean equals(final Object obj) {
        if (obj instanceof Vec2f) {
            Vec2f v = (Vec2f) obj;
            return v.x == x && v.y == y;
        }
        return false;
    }

    /**
     * Hash code generated based on the values of the internal {@link #x X} and
     * {@link #y Y} fields.
     *
     * @return the hash code calculated for this vector.
     */
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 41 * hash + Float.floatToIntBits(this.x);
        hash = 41 * hash + Float.floatToIntBits(this.y);
        return hash;
    }

    /**
     * Returns a String representation of this vector.
     *
     * @return String representation of the vector in the format of (x,y).
     */
    @Override
    public final String toString() {
        return "(" + x + " ," + y + ")";
    }

    /**
     * Returns a copy of this vector.
     *
     * @return a new vector with dimensions equal to the dimensions of this
     * vector.
     */
    public final Vec2f copy() {
        return new Vec2f(x, y);
    }
}
