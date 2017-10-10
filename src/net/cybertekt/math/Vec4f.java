package net.cybertekt.math;

import net.cybertekt.util.FastMath;

/**
 * Vector4f - (C) Cybertekt Software.
 *
 * Defines a vector that consists of four floating-point components stored
 * internally as {@link #x X}, {@link #y Y}, {@link #z Z}, and {@link #w W}.
 * Methods are provided for performing mathematical operations between this
 * vector and other four dimensional vectors.
 *
 * @version 1.0.0
 * @since 1.0.0
 * @author Andrew Vektor
 */
public final class Vec4f {

    /**
     * Vector in which the sum of its components are equal to zero. Also known
     * as a zero length or empty vector.
     */
    public transient static final Vec4f ZERO = new Vec4f(0f, 0f, 0f, 0f);

    /**
     * Vector in which the sum of the y, z, and w components are equal to zero
     * and the x component equals one. Also known as a 'unit' or 'normalized'
     * vector.
     */
    public transient static final Vec4f UNIT_X = new Vec4f(1f, 0f, 0f, 0f);

    /**
     * Vector in which the sum of the x, z, and w components are equal to zero
     * and the y component equals one. Also known as a 'unit' or 'normalized'
     * vector.
     */
    public transient static final Vec4f UNIT_Y = new Vec4f(0f, 1f, 0f, 0f);

    /**
     * Vector in which the sum of the x, y, and w components are equal to zero
     * and the z component equals one. Also known as a 'unit' or 'normalized'
     * vector.
     */
    public transient static final Vec4f UNIT_Z = new Vec4f(0f, 0f, 1f, 0f);

    /**
     * Vector in which the sum of the x, y, and z components are equal to zero
     * and the w component equals one. Also known as a 'unit' or 'normalized'
     * vector.
     */
    public transient static final Vec4f UNIT_W = new Vec4f(0f, 0f, 0f, 1f);

    /**
     * Four floating-point components that define this vector.
     */
    protected float x, y, z, w;

    /**
     * Constructs a vector in which the sum of its components equal zero. Also
     * known as a zero length or empty vector.
     */
    public Vec4f() {
        x = y = z = w = 0;
    }

    /**
     * Constructs a vector from four floating-point values.
     *
     * @param xToSet the x component of this vector.
     * @param yToSet the y component of this vector.
     * @param zToSet the z component of this vector.
     * @param wToSet the w component of this vector.
     */
    public Vec4f(final float xToSet, final float yToSet, final float zToSet, final float wToSet) {
        x = xToSet;
        y = yToSet;
        z = zToSet;
        w = wToSet;
    }

    /**
     * Constructs a four dimensional vector defined by copying the values from
     * another vector. The provided vector is not internally modified in any
     * way. Passing null arguments into this constructor is not permitted.
     *
     * @param toCopy the vector from which to copy.
     * @throws NullPointerException if the vector argument passed into this
     * constructor is null.
     */
    public Vec4f(final Vec4f toCopy) {
        x = toCopy.x;
        y = toCopy.y;
        z = toCopy.z;
        w = toCopy.w;
    }

    /**
     * Generates a vector defined by adding a vector to this vector. Neither
     * this vector nor the provided vector are internally modified in any way.
     * Passing null arguments into this method is not permitted. This method
     * creates and returns a new vector object each time it is called.
     * <br /><br />
     * Simple description: new vec4f(this.x + other.x, this.y + other.y, this.z
     * + this.z, this.w + other.w)
     *
     * @param toAdd the vector in which to add to this vector.
     * @return a vector equal to the sum of this vector and another vector. F
     * @throws NullPointerException if the vector argument passed into this
     * method is null.
     */
    public final Vec4f add(final Vec4f toAdd) {
        return new Vec4f(x + toAdd.x, y + toAdd.y, z + toAdd.z, w + toAdd.w);
    }

    /**
     * Generates a vector defined by adding four floating-point values to this
     * vector. This vector is not internally modified in any way by this method.
     * A new vector object is created and returned each time this method is
     * called.
     * <br /><br />
     * Simple description: new vec4f(this.x + xToAdd, this.y + yToAdd, zToAdd +
     * z)
     *
     * @param xToAdd amount to add to the x component of this vector.
     * @param yToAdd amount to add to the y component of this vector.
     * @param zToAdd amount to add to the z component of this vector.
     * @param wToAdd amount to add to the w component of this vector.
     * @return the generated vector.
     */
    public final Vec4f add(final float xToAdd, final float yToAdd, final float zToAdd, final float wToAdd) {
        return new Vec4f(x + xToAdd, y + yToAdd, z + zToAdd, w + wToAdd);
    }

    /**
     * Calculates the sum of this vector and another vector, storing the result
     * in the second 'storage' vector. Neither this vector nor the first vector
     * argument are internally modified in any way by this method. Passing null
     * arguments into this method is not permitted. Use this method whenever
     * possible to avoid creating new vector objects. A reference to the second
     * 'storage' vector is returned for the purpose of call chaining.
     * <br /><br />
     * Simple description: storage(this.x + other.x, this.y + other.y, this.z +
     * other.z, this.w + other.w)
     *
     * @param toAdd the vector in which to add to this vector.
     * @param toStore the vector in which to store the result.
     * @return the stored vector.
     * @throws NullPointerException if the vector argument passed into this
     * method is null.
     */
    public final Vec4f add(final Vec4f toAdd, final Vec4f toStore) {
        return toStore.set(x + toAdd.x, y + toAdd.y, z + toAdd.z, w + toAdd.w);
    }

    /**
     * Generates a vector defined by adding a value to each component of this
     * vector. This vector is not internally modified in any way by this method.
     * A new vector object is created and returned each time this method is
     * called.
     * <br /><br />
     * Simple description: new vec4f(this.x + amount, this.y + amount, this.z +
     * amount, this.w + amount)
     *
     * @param amount the amount to add to each component of this vector.
     * @return the generated vector.
     */
    public final Vec4f add(final float amount) {
        return new Vec4f(x + amount, y + amount, z + amount, w + amount);
    }

    /**
     * Internally adds a vector to this vector. The vector passed into this
     * method is not internally modified in any way. Passing null arguments into
     * this method is not permitted. This method does not create any additional
     * objects and returns a reference to this vector for the purpose of call
     * chaining.
     * <br /><br />
     * Simple description:<br />this(this.x + other.x, this.y + other.y, this.z
     * + other.z, this.w + other.w)
     *
     * @param toAdd the vector in which to add to this vector.
     * @return this vector.
     * @throws NullPointerException if the vector argument passed into this
     * method is null.
     */
    public final Vec4f addLocal(final Vec4f toAdd) {
        x += toAdd.x;
        y += toAdd.y;
        z += toAdd.z;
        w += toAdd.w;
        return this;
    }

    /**
     * Internally adds four floating-point values to this vector. This method
     * does not create any additional objects and returns a reference to this
     * vector for the purpose of call chaining.
     * <br /><br />
     * Simple description:<br />this(this.x + xToAdd, this.y + yToAdd, this.z +
     * zToAdd, this.w + wToAdd)
     *
     * @param xToAdd the amount to add to the x component of this vector.
     * @param yToAdd the amount to add to the y component of this vector.
     * @param zToAdd the amount to add to the z component of this vector.
     * @param wToAdd the amount to add to the w component of this vector.
     * @return this vector
     */
    public final Vec4f addLocal(final float xToAdd, final float yToAdd, final float zToAdd, final float wToAdd) {
        x += xToAdd;
        y += yToAdd;
        z += zToAdd;
        w += wToAdd;
        return this;
    }

    /**
     * Internally adds a value to each component of this vector. This method
     * does not create any additional objects and returns a reference to this
     * vector for the purpose of call chaining.
     * <br /><br />
     * Simple description:<br />this(this.x + amount, this.y + amount, this.z +
     * amount, this.w + amount)
     *
     * @param amount the amount to add to each component of this vector.
     * @return this vector.
     */
    public final Vec4f addLocal(final float amount) {
        x += amount;
        y += amount;
        z += amount;
        w += amount;
        return this;
    }

    /**
     * Generates a vector defined by the difference between this vector and
     * another vector. Neither this vector nor the provided vector are
     * internally modified in any way. Passing null arguments into this method
     * is not permitted. This method creates and returns a new vector object
     * each time it is called.
     * <br /><br />
     * Simple description: new vec4f(this.x - other.x, this.y - other.y, this.z
     * - other.z, this.w - other.w)
     *
     * @param toSubtract the vector in which to subtract from this vector.
     * @return the generated vector.
     * @throws NullPointerException if the vector argument passed into this
     * method is null.
     */
    public final Vec4f subtract(final Vec4f toSubtract) {
        return new Vec4f(x - toSubtract.x, y - toSubtract.y, z - toSubtract.z, w - toSubtract.w);
    }

    /**
     * Generates a vector defined by the difference between this vector and four
     * floating-point values. This vector is not internally modified in any way
     * by this method. A new vector object is created and returned each time
     * this method is called.
     * <br /><br />
     * Simple description: new vec2f(this.x - xToSubtract, this.y + yToSubtract,
     * this.z - zToSubtract, this.w - wToSubtract)
     *
     * @param xToSubtract amount to subtract from the x component.
     * @param yToSubtract amount to subtract from the y component.
     * @param zToSubtract amount to subtract from the z component.
     * @param wToSubtract amount to subtract from the w component.
     * @return the generated vector.
     */
    public final Vec4f subtract(final float xToSubtract, final float yToSubtract, final float zToSubtract, final float wToSubtract) {
        return new Vec4f(x - xToSubtract, y - yToSubtract, z - zToSubtract, w - wToSubtract);
    }

    /**
     * Generates a vector defined by subtracting a value from each component of
     * this vector. This vector is not internally modified in any way by this
     * method. A new vector object is created and returned each time this method
     * is called.
     * <br /><br />
     * Simple description: new vec4f(this.x - amount, this.y - amount, this.z -
     * amount, this.w - amount)
     *
     * @param amount the amount to subtract from each component of this vector.
     * @return the generated vector.
     */
    public final Vec4f subtract(final float amount) {
        return new Vec4f(x - amount, y - amount, z - amount, w - amount);
    }

    /**
     * Calculates the difference between this vector and another vector, storing
     * the result in the second 'storage' vector. Neither this vector nor the
     * first vector argument are internally modified in any way by this method.
     * Passing null arguments into this method is not permitted. Use this method
     * whenever possible to avoid creating new vector objects. A reference to
     * the second 'storage' vector is returned for the purpose of call chaining.
     * <br /><br />
     * Simple description: storage(this.x - other.x, this.y - other.y, this.z -
     * other.z, this.w - other.w)
     *
     * @param toSubtract the vector to subtract from this vector.
     * @param toStore the vector in which to store the result.
     * @return the stored vector.
     * @throws NullPointerException if the vector argument passed into this
     * method is null.
     */
    public final Vec4f subtract(final Vec4f toSubtract, final Vec4f toStore) {
        return toStore.set(x - toSubtract.x, y - toSubtract.y, z - toSubtract.z, w - toSubtract.w);

    }

    /**
     * Internally subtracts a vector from this vector. The vector passed into
     * this method is not internally modified in any way. Passing null arguments
     * into this method is not permitted. This method does not create any
     * additional objects and returns a reference to this vector for the purpose
     * of call chaining.
     * <br /><br />
     * Simple description:<br />this(this.x - other.x, this.y - other.y, this.z
     * - other.z, this.w - other.w)
     *
     * @param toSubtract the vector to subtract from this vector.
     * @return this vector.
     * @throws NullPointerException if the vector argument passed into this
     * method is null.
     */
    public final Vec4f subtractLocal(final Vec4f toSubtract) {
        x -= toSubtract.x;
        y -= toSubtract.y;
        z -= toSubtract.z;
        w -= toSubtract.w;
        return this;
    }

    /**
     * Internally subtracts four floating-point values from this vector. This
     * method does not create any additional objects and returns a reference to
     * this vector for the purpose of call chaining.
     * <br /><br />
     * Simple description:<br />this(this.x + xToAdd, this.y + yToAdd, this.z -
     * zToSubtract, this.w - wToSubtract))
     *
     * @param xToSubtract the amount to subtract from the x component.
     * @param yToSubtract the amount to subtract from the y component.
     * @param zToSubtract the amount to subtract from the z component.
     * @param wToSubtract the amount to subtract from the w component.
     * @return this vector
     */
    public final Vec4f subtractLocal(final float xToSubtract, final float yToSubtract, final float zToSubtract, final float wToSubtract) {
        x -= xToSubtract;
        y -= yToSubtract;
        z -= zToSubtract;
        w -= wToSubtract;
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
    public final Vec4f subtractLocal(final float amount) {
        x -= amount;
        y -= amount;
        z -= amount;
        w -= amount;
        return this;
    }

    /**
     * Generates a vector defined by multiplying this vector by another vector.
     * Neither this vector nor the provided vector are internally modified in
     * any way. Passing null arguments into this method is not permitted. This
     * method creates and returns a new vector object each time it is called.
     * <br /><br />
     * Simple description: new vec4f(this.x * other.x, this.y * other.y, z *
     * other.z, w * other.w)
     *
     * @param toMult the vector by which to multiply this vector.
     * @return a vector equal to the product of this vector and another vector.
     * @throws NullPointerException if the vector argument passed into this
     * method is null.
     */
    public final Vec4f mult(final Vec4f toMult) {
        return new Vec4f(x * toMult.x, y * toMult.y, z * toMult.z, w * toMult.w);
    }

    /**
     * Generates a vector defined by multiplying this vector by four
     * floating-point values. This vector is not internally modified in any way
     * by this method. A new vector object is created and returned each time
     * this method is called.
     * <br /><br />
     * Simple description: new vec4f(this.x * xToMult, this.y * yToMult, this.z
     * * zToMult, this.w * wToMult))
     *
     * @param xToMult amount by which to multiply the x component.
     * @param yToMult amount by which to multiply the y component.
     * @param zToMult amount by which to multiply the z component.
     * @param wToMult amount by which to multiply the w component.
     * @return the generated vector.
     */
    public final Vec4f mult(final float xToMult, final float yToMult, final float zToMult, final float wToMult) {
        return new Vec4f(x * xToMult, y * yToMult, z * zToMult, w * wToMult);
    }

    /**
     * Generates a vector defined by multiplying each component by a scalar
     * amount. This vector is not internally modified in any way by this method.
     * A new vector object is created and returned each time this method is
     * called.
     * <br /><br />
     * Simple description: new vec4f(this.x * amount, this.y * amount, this.z *
     * amount, this.w * amount).
     *
     * @param amount the amount by which to multiply each component.
     * @return the generated vector.
     */
    public final Vec4f mult(final float amount) {
        return new Vec4f(x * amount, y * amount, z * amount, w * amount);
    }

    /**
     * Calculates the product of this vector and another vector, storing the
     * result in the second 'storage' vector. Neither this vector nor the first
     * vector argument are internally modified in any way by this method.
     * Passing null arguments into this method is not permitted. Use this method
     * whenever possible to avoid creating new vector objects. A reference to
     * the second 'storage' vector is returned for the purpose of call chaining.
     * <br /><br />
     * Simple description: storage(this.x * other.x, this.y * other.y, this.z *
     * other.z, this.w * other.w)
     *
     * @param toMult the vector by which to multiply this vector.
     * @param toStore the vector in which to store the result.
     * @return the stored vector.
     * @throws NullPointerException if either vector argument passed into this
     * method is null.
     */
    public final Vec4f mult(final Vec4f toMult, final Vec4f toStore) {
        return toStore.set(x * toMult.x, y * toMult.y, z * toMult.z, w * toMult.w);
    }

    /**
     * Internally multiplies this vector by another vector. The vector passed
     * into this method is not internally modified in any way. Passing null
     * arguments into this method is not permitted. This method does not create
     * any additional objects and returns a reference to this vector for the
     * purpose of call chaining.
     * <br /><br />
     * Simple description:<br />this(this.x * other.x, this.y * other.y, this.z
     * * other.z, this.w * other.w)
     *
     * @param toMult the vector by which to multiply this vector.
     * @return this vector.
     * @throws NullPointerException if the vector argument passed into this
     * method is null.
     */
    public final Vec4f multLocal(final Vec4f toMult) {
        x *= toMult.x;
        y *= toMult.y;
        z *= toMult.z;
        w *= toMult.w;
        return this;
    }

    /**
     * Internally multiplies this vector by four floating-point values. This
     * method does not create any additional objects and returns a reference to
     * this vector for the purpose of call chaining.
     * <br /><br />
     * Simple description:<br />this(this.x * xToMult, this.y * yToMult, this.z
     * * zToMult, this.w * wToMult)
     *
     * @param xToMult the amount by which to multiply the x component.
     * @param yToMult the amount by which to multiply the y component.
     * @param zToMult the amount by which to multiply the z component.
     * @param wToMult the amount by which to multiply the w component.
     * @return this vector
     */
    public final Vec4f multLocal(final float xToMult, final float yToMult, final float zToMult, final float wToMult) {
        x *= xToMult;
        y *= yToMult;
        z *= zToMult;
        w *= wToMult;
        return this;
    }

    /**
     * Internally multiplies each component of this vector by a scalar. This
     * method does not create any additional objects and returns a reference to
     * this vector for the purpose of call chaining.
     * <br /><br />
     * Simple description:<br />this(this.x * amount, this.y * amount, this.z *
     * amount, this.w * amount)
     *
     * @param amount the amount by which to multiply each component.
     * @return this vector.
     */
    public final Vec4f multLocal(final float amount) {
        x *= amount;
        y *= amount;
        z *= amount;
        w *= amount;
        return this;
    }

    /**
     * Generates a vector defined by dividing this vector by another vector.
     * Neither this vector nor the provided vector are internally modified in
     * any way. Passing null arguments into this method is not permitted. This
     * method creates and returns a new vector object each time it is called.
     * <br /><br />
     * Simple description: new vec4f(this.x / other.x, this.y / other.y, this.z
     * / other.z, this.w / other.w)
     *
     * @param toDiv the vector by which to divide this vector.
     * @return a vector equal to the quotient of this vector and another vector.
     * @throws NullPointerException if the vector argument passed into this
     * method is null.
     */
    public final Vec4f divide(final Vec4f toDiv) {
        return new Vec4f(x / toDiv.x, y / toDiv.y, z / toDiv.z, w / toDiv.w);
    }

    /**
     * Generates a vector defined by dividing this vector by four floating-point
     * values. This vector is not internally modified in any way by this method.
     * A new vector object is created and returned each time this method is
     * called.
     * <br /><br />
     * Simple description: new vec4f(this.x / xToDiv, this.y / yToDiv, this.z /
     * zToDiv, this.w / wToDiv)
     *
     * @param xToDiv amount by which to divide the x component.
     * @param yToDiv amount by which to divide the y component.
     * @param zToDiv amount by which to divide the z component.
     * @param wToDiv amount by which to divide the w component.
     * @return the generated vector.
     */
    public final Vec4f divide(final float xToDiv, final float yToDiv, final float zToDiv, final float wToDiv) {
        return new Vec4f(x / xToDiv, y / yToDiv, z / zToDiv, w / wToDiv);
    }

    /**
     * Generates a vector defined by dividing each component by a value. This
     * vector is not internally modified in any way by this method. A new vector
     * object is created and returned each time this method is called.
     * <br /><br />
     * Simple description: new vec4f(this.x / amount, this.y / amount, this.z /
     * amount, this.w / amount)
     *
     * @param amount the amount by which to divide each component.
     * @return the generated vector.
     */
    public final Vec4f divide(final float amount) {
        return new Vec4f(x / amount, y / amount, z / amount, w / amount);
    }

    /**
     * Calculates the quotient of this vector and another vector, storing the
     * result in the second 'storage' vector. Neither this vector nor the first
     * vector argument are internally modified in any way by this method.
     * Passing null arguments into this method is not permitted. Use this method
     * whenever possible to avoid creating new vector objects. A reference to
     * the second 'storage' vector is returned for the purpose of call chaining.
     * <br /><br />
     * Simple description: storage(this.x / other.x, this.y / other.y, this.z /
     * other.z, this.w / other.w)
     *
     * @param toDiv the vector by which to divide this vector.
     * @param toStore the vector in which to store the result.
     * @return the stored vector.
     * @throws NullPointerException if either vector argument passed into this
     * method is null.
     */
    public final Vec4f divide(final Vec4f toDiv, final Vec4f toStore) {
        return toStore.set(x / toDiv.x, y / toDiv.y, z / toDiv.z, w / toDiv.w);
    }

    /**
     * Internally divides this vector by another vector. The vector passed into
     * this method is not internally modified in any way. Passing null arguments
     * into this method is not permitted. This method does not create any
     * additional objects and returns a reference to this vector for the purpose
     * of call chaining.
     * <br /><br />
     * Simple description:<br />this(this.x / other.x, this.y / other.y, this.z
     * / other.z, this.w / other.w)
     *
     * @param toDiv the vector by which to divide this vector.
     * @return this vector.
     * @throws NullPointerException if the vector argument passed into this
     * method is null.
     */
    public final Vec4f divideLocal(final Vec4f toDiv) {
        x /= toDiv.x;
        y /= toDiv.y;
        z /= toDiv.z;
        w /= toDiv.w;
        return this;
    }

    /**
     * Internally divides this vector by four floating-point values. This method
     * does not create any additional objects and returns a reference to this
     * vector for the purpose of call chaining.
     * <br /><br />
     * Simple description:<br />this(this.x / xToDiv, this.y / yToDiv, this.z /
     * zToDiv, this.w / wToDiv)
     *
     * @param xToDiv the amount by which to divide the x component.
     * @param yToDiv the amount by which to divide the y component.
     * @param zToDiv the amount by which to divide the z component.
     * @param wToDiv the amount by which to divide the w component.
     * @return this vector
     */
    public final Vec4f divideLocal(final float xToDiv, final float yToDiv, final float zToDiv, final float wToDiv) {
        x /= xToDiv;
        y /= yToDiv;
        z /= zToDiv;
        w /= wToDiv;
        return this;
    }

    /**
     * Internally divides each component of this vector by a value. This method
     * does not create any additional objects and returns a reference to this
     * vector for the purpose of call chaining.
     * <br /><br />
     * Simple description:<br />this(this.x / amount, this.y / amount, this.z /
     * amount, this.w / amount)
     *
     * @param amount the amount by which to divide each component.
     * @return this vector.
     */
    public final Vec4f divideLocal(final float amount) {
        x /= amount;
        y /= amount;
        z /= amount;
        w /= amount;
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
    public final Vec4f interpolate(final Vec4f toVector, final float interpolateAmount) {
        return new Vec4f((1f - interpolateAmount) * x + interpolateAmount * toVector.x, (1f - interpolateAmount) * y + interpolateAmount * toVector.y, (1f - interpolateAmount) + interpolateAmount * toVector.z, (1f - interpolateAmount) + interpolateAmount * toVector.w);
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
    public final Vec4f interpolateLocal(final Vec4f toVector, final float interpolateAmount) {
        x = (1f - interpolateAmount) * x + interpolateAmount * toVector.x;
        y = (1f - interpolateAmount) * y + interpolateAmount * toVector.y;
        z = (1f - interpolateAmount) * z + interpolateAmount * toVector.z;
        w = (1f - interpolateAmount) * w + interpolateAmount * toVector.w;
        return this;
    }

    /**
     * Creates a copy of this vector and multiplies it by negative one. A new
     * vector object is created and returned each time this method is called.
     *
     * @return a copy of this vector multiplied by -1.
     */
    public final Vec4f negate() {
        return new Vec4f(-x, -y, -z, -w);
    }

    /**
     * Internally multiplies each component of this vector by negative one. This
     * method does not create any additional objects and returns a reference to
     * this vector for the purpose of call chaining.
     *
     * @return this vector.
     */
    public final Vec4f negateLocal() {
        setX(-x);
        setY(-y);
        setZ(-z);
        setW(-w);
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
    public final Vec4f truncate(final float limit) {
        if (length() <= limit) {
            return new Vec4f(this);
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
    public final Vec4f truncateLocal(final float limit) {
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
    public final Vec4f normalize() {
        return new Vec4f(this).truncateLocal(1f);
    }

    /**
     * Internally normalizes this vector so that the sum of all its components
     * equal one. This method is equivalent to calling truncateLocal(1f).
     *
     * @return this vector.
     */
    public final Vec4f normalizeLocal() {
        truncateLocal(1f);
        return this;
    }

    /**
     * Generates a copy of this value with the components rounded to the closest
     * integer with ties rounding up.
     *
     * @return the generated vector.
     */
    public final Vec4f round() {
        return new Vec4f(FastMath.round(x), FastMath.round(y), FastMath.round(z), FastMath.round(w));
    }

    /**
     * Internally rounds the components of this vector to the closest integer
     * with ties rounding up.
     *
     * @return this vector.
     */
    public final Vec4f roundLocal() {
        x = FastMath.round(x);
        y = FastMath.round(y);
        z = FastMath.round(z);
        w = FastMath.round(w);
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
    public final float dot(final Vec4f toDot) {
        return (x * toDot.x) + (y * toDot.y) + (z * toDot.z) + (w * toDot.w);
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
    public final float angleBetween(final Vec4f other) {
        return FastMath.acos(dot(other));
    }

    /**
     * Calculates the length of this vector.
     *
     * @return the length (magnitude) of this vector.
     */
    public final float length() {
        return FastMath.sqrt((x * x) + (y * y) + (z * z) + (w * w));
    }

    /**
     * Calculates the length of this vector, squared.
     *
     * @return the length (magnitude) of this vector, squared.
     */
    public final float lengthSquared() {
        return (x * x) + (y * y) + (z * z) + (w * w);
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
    public final float distance(final Vec4f otherVector) {
        return (x - otherVector.x) + (y - otherVector.y) + (z - otherVector.z) + (w * otherVector.w);
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
    public final float distanceSquared(final Vec4f otherVector) {
        return ((x - otherVector.x) * (x - otherVector.x)) + ((y - otherVector.y) * (y - otherVector.y)) + ((z - otherVector.z) * (z - otherVector.z) + ((w - otherVector.w) * (w - otherVector.w)));
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
     * Internally sets the x, y, z, and w components of this vector. Returns a
     * reference to this vector for the purpose of call chaining.
     *
     * @param xToSet the x component to set.
     * @param yToSet the y component to set.
     * @param zToSet the z component to set.
     * @param wToSet the w component to set.
     * @return this vector.
     */
    public final Vec4f set(final float xToSet, final float yToSet, final float zToSet, final float wToSet) {
        x = xToSet;
        y = yToSet;
        z = zToSet;
        w = wToSet;
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
    public final Vec4f set(final Vec4f toCopy) {
        x = toCopy.x;
        y = toCopy.y;
        z = toCopy.z;
        w = toCopy.w;
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
     * @return the x component of this vector.
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
     * Internally sets the z component of this vector.
     *
     * @param zToSet the Z component of this vector to set.
     */
    public final void setZ(final float zToSet) {
        z = zToSet;
    }

    /**
     * Returns the z component of this vector.
     *
     * @return the z component of this vector.
     */
    public final float getZ() {
        return z;
    }

    /**
     * Internally sets the w component of this vector.
     *
     * @param wToSet the w component of this vector to set.
     */
    public final void setW(final float wToSet) {
        w = wToSet;
    }

    /**
     * Returns the w component of this vector.
     *
     * @return the w component of this vector.
     */
    public final float getW() {
        return w;
    }

    /**
     * Indicates if the specified object is equal to this vector. This method
     * returns true if and only if the provided object is an instance of
     * Vector4f and its {@link #x X}, {@link #y Y}, {@link #z Z}, and
     * {@link #w W} components are equal to the X, Y, Z, and W components of
     * this vector.
     *
     * @param obj the object to compare with this vector.
     * @return true if the object is an instance of Vector4f and is equal to
     * this vector, false otherwise.
     */
    @Override
    public final boolean equals(final Object obj) {
        if (obj instanceof Vec4f) {
            Vec4f v = (Vec4f) obj;
            return v.x == x && v.y == y && v.z == z && v.w == w;
        }
        return false;
    }

    /**
     * Hash code generated based on the values of the internal
     * {@link #x X}, {@link #y Y}, {@link #z Z}, and {@link #w W} fields.
     *
     * @return the hash code calculated for this vector.
     */
    @Override
    public int hashCode() {
        int hash = 3;
        hash = 59 * hash + Float.floatToIntBits(this.x);
        hash = 59 * hash + Float.floatToIntBits(this.y);
        hash = 59 * hash + Float.floatToIntBits(this.z);
        hash = 59 * hash + Float.floatToIntBits(this.w);
        return hash;
    }

    /**
     * Returns a String representation of this vector.
     *
     * @return a String representation of the vector as (I.E (x,y,z,w)).
     */
    @Override
    public final String toString() {
        return "(" + x + "," + y + "," + z + "," + w + ")";
    }
}
