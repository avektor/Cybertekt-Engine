package net.cybertekt.math;

import net.cybertekt.util.FastMath;

/**
 * Vec3f - (C) Cybertekt Software.
 *
 * Defines a vector that consists of three floating-point components stored
 * internally as {@link #x X}, {@link #y Y}, and {@link #z Z}. Methods are
 * provided for performing mathematical operations between this vector and other
 * three dimensional vectors.
 *
 * @version 1.0.0
 * @since 1.0.0
 * @author Andrew Vektor
 */
public final class Vec3f {

    /**
     * Vector in which the sum of its components equal zero. Also known as a
     * zero length or empty vector.
     */
    public transient static final Vec3f ZERO = new Vec3f(0f, 0f, 0f);

    /**
     * Vector in which the sum of the y and z components are equal to zero and
     * the x component equals one. Also known as a 'unit' or 'normalized'
     * vector.
     */
    public transient static final Vec3f UNIT_X = new Vec3f(1f, 0f, 0f);

    /**
     * Vector in which the sum of the x and z components are equal to zero and
     * the y component equals one. Also known as a 'unit' or 'normalized'
     * vector.
     */
    public transient static final Vec3f UNIT_Y = new Vec3f(0f, 1f, 0f);

    /**
     * Vector in which the sum of the x and y components are equal to zero and
     * the z component equals one. Also known as a 'unit' or 'normalized'
     * vector.
     */
    public transient static final Vec3f UNIT_Z = new Vec3f(0f, 0f, 1f);

    /**
     * Three floating-point components that define this vector.
     */
    protected float x, y, z;

    /**
     * Constructs a vector in which the sum of its components equal zero. Also
     * known as a zero length or empty vector.
     */
    public Vec3f() {
        x = y = z = 0;
    }

    /**
     * Constructs a vector from the three floating-point values.
     *
     * @param xToSet the x component of the vector.
     * @param yToSet the Y component of the vector.
     * @param zToSet the Z component of the vector.
     */
    public Vec3f(final float xToSet, final float yToSet, final float zToSet) {
        x = xToSet;
        y = yToSet;
        z = zToSet;
    }

    /**
     * Constructs a three dimensional vector defined by copying the values from
     * another vector. The provided vector is not internally modified in any
     * way. Passing null arguments into this constructor is not permitted.
     *
     * @param toCopy the vector from which to copy.
     * @throws NullPointerException if the vector argument passed into this
     * constructor is null.
     */
    public Vec3f(final Vec3f toCopy) {
        x = toCopy.x;
        y = toCopy.y;
        z = toCopy.z;
    }

    /**
     * Generates a vector defined by adding a vector to this vector. Neither
     * this vector nor the provided vector are internally modified in any way.
     * Passing null arguments into this method is not permitted. This method
     * creates and returns a new vector object each time it is called.
     * <br /><br />
     * Simple description: new vec3f(this.x + other.x, this.y + other.y, this.z
     * + this.z)
     *
     * @param toAdd the vector in which to add to this vector.
     * @return a vector equal to the sum of this vector and another vector. F
     * @throws NullPointerException if the vector argument passed into this
     * method is null.
     */
    public final Vec3f add(final Vec3f toAdd) {
        return new Vec3f(x + toAdd.x, y + toAdd.y, z + toAdd.z);
    }

    /**
     * Generates a vector defined by adding three floating-point values to this
     * vector. This vector is not internally modified in any way by this method.
     * A new vector object is created and returned each time this method is
     * called.
     * <br /><br />
     * Simple description: new vec3f(this.x + xToAdd, this.y + yToAdd, zToAdd +
     * z)
     *
     * @param xToAdd amount to add to the x component of this vector.
     * @param yToAdd amount to add to the y component of this vector.
     * @param zToAdd amount to add to the z component of this vector.
     * @return the generated vector.
     */
    public final Vec3f add(final float xToAdd, final float yToAdd, final float zToAdd) {
        return new Vec3f(x + xToAdd, y + yToAdd, z + zToAdd);
    }

    /**
     * Generates a vector defined by adding a value to each component of this
     * vector. This vector is not internally modified in any way by this method.
     * A new vector object is created and returned each time this method is
     * called.
     * <br /><br />
     * Simple description: new vec3f(this.x + amount, this.y + amount, this.z +
     * amount)
     *
     * @param amount the amount to add to each component of this vector.
     * @return the generated vector.
     */
    public final Vec3f add(final float amount) {
        return new Vec3f(x + amount, y + amount, z + amount);
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
     * other.z)
     *
     * @param toAdd the vector in which to add to this vector.
     * @param toStore the vector in which to store the result.
     * @return the stored vector.
     * @throws NullPointerException if the vector argument passed into this
     * method is null.
     */
    public final Vec3f add(final Vec3f toAdd, final Vec3f toStore) {
        return toStore.set(x + toAdd.x, y + toAdd.y, z + toAdd.z);
    }

    /**
     * Internally adds a vector to this vector. The vector passed into this
     * method is not internally modified in any way. Passing null arguments into
     * this method is not permitted. This method does not create any additional
     * objects and returns a reference to this vector for the purpose of call
     * chaining.
     * <br /><br />
     * Simple description:<br />this(this.x + other.x, this.y + other.y, this.z
     * + other.z)
     *
     * @param toAdd the vector in which to add to this vector.
     * @return this vector.
     * @throws NullPointerException if the vector argument passed into this
     * method is null.
     */
    public final Vec3f addLocal(final Vec3f toAdd) {
        x += toAdd.x;
        y += toAdd.y;
        z += toAdd.z;
        return this;
    }

    /**
     * Internally adds three floating-point values to this vector. This method
     * does not create any additional objects and returns a reference to this
     * vector for the purpose of call chaining.
     * <br /><br />
     * Simple description:<br />this(this.x + xToAdd, this.y + yToAdd, this.z +
     * zToAdd)
     *
     * @param xToAdd the amount to add to the x component of this vector.
     * @param yToAdd the amount to add to the y component of this vector.
     * @param zToAdd the amount to add to the z component of this vector.
     * @return this vector
     */
    public final Vec3f addLocal(final float xToAdd, final float yToAdd, final float zToAdd) {
        x += xToAdd;
        y += yToAdd;
        z += zToAdd;
        return this;
    }

    /**
     * Internally adds a value to each component of this vector. This method
     * does not create any additional objects and returns a reference to this
     * vector for the purpose of call chaining.
     * <br /><br />
     * Simple description:<br />this(this.x + amount, this.y + amount, this.z +
     * amount)
     *
     * @param amount the amount to add to each component of this vector.
     * @return this vector.
     */
    public final Vec3f addLocal(final float amount) {
        x += amount;
        y += amount;
        z += amount;
        return this;
    }

    /**
     * Generates a vector defined by the difference between this vector and
     * another vector. Neither this vector nor the provided vector are
     * internally modified in any way. Passing null arguments into this method
     * is not permitted. This method creates and returns a new vector object
     * each time it is called.
     * <br /><br />
     * Simple description: new vec3f(this.x - other.x, this.y - other.y, this.z
     * - other.z)
     *
     * @param toSubtract the vector in which to subtract from this vector.
     * @return the generated vector.
     * @throws NullPointerException if the vector argument passed into this
     * method is null.
     */
    public final Vec3f subtract(final Vec3f toSubtract) {
        return new Vec3f(x - toSubtract.x, y - toSubtract.y, z - toSubtract.z);
    }

    /**
     * Generates a vector defined by the difference between this vector and
     * three floating-point values. This vector is not internally modified in
     * any way by this method. A new vector object is created and returned each
     * time this method is called.
     * <br /><br />
     * Simple description: new vec2f(this.x - xToSubtract, this.y + yToSubtract,
     * this.z - zToSubtract)
     *
     * @param xToSubtract amount to subtract from the x component.
     * @param yToSubtract amount to subtract from the y component.
     * @param zToSubtract amount to subtract from the z component.
     * @return the generated vector.
     */
    public final Vec3f subtract(final float xToSubtract, final float yToSubtract, final float zToSubtract) {
        return new Vec3f(x - xToSubtract, y - yToSubtract, z - zToSubtract);
    }

    /**
     * Generates a vector defined by subtracting a value from each component of
     * this vector. This vector is not internally modified in any way by this
     * method. A new vector object is created and returned each time this method
     * is called.
     * <br /><br />
     * Simple description: new vec3f(this.x - amount, this.y - amount, this.z -
     * amount)
     *
     * @param amount the amount to subtract from each component of this vector.
     * @return the generated vector.
     */
    public final Vec3f subtract(final float amount) {
        return new Vec3f(x - amount, y - amount, z - amount);
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
     * other.z)
     *
     * @param toSubtract the vector to subtract from this vector.
     * @param toStore the vector in which to store the result.
     * @return the stored vector.
     * @throws NullPointerException if the vector argument passed into this
     * method is null.
     */
    public final Vec3f subtract(final Vec3f toSubtract, final Vec3f toStore) {
        return toStore.set(x - toSubtract.x, y - toSubtract.y, z - toSubtract.z);
    }

    /**
     * Internally subtracts a vector from this vector. The vector passed into
     * this method is not internally modified in any way. Passing null arguments
     * into this method is not permitted. This method does not create any
     * additional objects and returns a reference to this vector for the purpose
     * of call chaining.
     * <br /><br />
     * Simple description:<br />this(this.x - other.x, this.y - other.y, this.z
     * - other.z)
     *
     * @param toSubtract the vector to subtract from this vector.
     * @return this vector.
     * @throws NullPointerException if the vector argument passed into this
     * method is null.
     */
    public final Vec3f subtractLocal(final Vec3f toSubtract) {
        x -= toSubtract.x;
        y -= toSubtract.y;
        z -= toSubtract.z;
        return this;
    }

    /**
     * Internally subtracts three floating-point values from this vector. This
     * method does not create any additional objects and returns a reference to
     * this vector for the purpose of call chaining.
     * <br /><br />
     * Simple description:<br />this(this.x + xToAdd, this.y + yToAdd, this.z -
     * zToSubtract)
     *
     * @param xToSubtract the amount to subtract from the x component.
     * @param yToSubtract the amount to subtract from the y component.
     * @param zToSubtract the amount to subtract from the z component.
     * @return this vector
     */
    public final Vec3f subtractLocal(final float xToSubtract, final float yToSubtract, final float zToSubtract) {
        x -= xToSubtract;
        y -= yToSubtract;
        z -= zToSubtract;
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
    public final Vec3f subtractLocal(final float amount) {
        x -= amount;
        y -= amount;
        z -= amount;
        return this;
    }

    /**
     * Generates a vector defined by multiplying this vector by another vector.
     * Neither this vector nor the provided vector are internally modified in
     * any way. Passing null arguments into this method is not permitted. This
     * method creates and returns a new vector object each time it is called.
     * <br /><br />
     * Simple description: new vec3f(this.x * other.x, this.y * other.y, this.z
     * * other.z)
     *
     * @param toMult the vector by which to multiply this vector.
     * @return a vector equal to the product of this vector and another vector.
     * @throws NullPointerException if the vector argument passed into this
     * method is null.
     */
    public final Vec3f mult(final Vec3f toMult) {
        return new Vec3f(x * toMult.x, y * toMult.y, z * toMult.z);
    }

    /**
     * Generates a vector defined by multiplying this vector by three
     * floating-point values. This vector is not internally modified in any way
     * by this method. A new vector object is created and returned each time
     * this method is called.
     * <br /><br />
     * Simple description: new vec3f(this.x * xToMult, this.y * yToMult, this.z
     * * zToMult)
     *
     * @param xToMult amount by which to multiply the x component.
     * @param yToMult amount by which to multiply the y component.
     * @param zToMult amount by which to multiply the z component.
     * @return the generated vector.
     */
    public final Vec3f mult(final float xToMult, final float yToMult, final float zToMult) {
        return new Vec3f(x * xToMult, y * yToMult, z * zToMult);
    }

    /**
     * Generates a vector defined by multiplying each component by a scalar
     * amount. This vector is not internally modified in any way by this method.
     * A new vector object is created and returned each time this method is
     * called.
     * <br /><br />
     * Simple description: new vec3f(this.x * amount, this.y * amount, this.z *
     * amount)
     *
     * @param amount the amount by which to multiply each component.
     * @return the generated vector.
     */
    public final Vec3f mult(final float amount) {
        return new Vec3f(x * amount, y * amount, z * amount);
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
     * other.z)
     *
     * @param toMult the vector by which to multiply this vector.
     * @param toStore the vector in which to store the result.
     * @return the stored vector.
     * @throws NullPointerException if either vector argument passed into this
     * method is null.
     */
    public final Vec3f mult(final Vec3f toMult, final Vec3f toStore) {
        return toStore.set(x * toMult.x, y * toMult.y, z * toMult.z);
    }

    /**
     * Internally multiplies this vector by another vector. The vector passed
     * into this method is not internally modified in any way. Passing null
     * arguments into this method is not permitted. This method does not create
     * any additional objects and returns a reference to this vector for the
     * purpose of call chaining.
     * <br /><br />
     * Simple description:<br />this(this.x * other.x, this.y * other.y, this.z
     * * other.z)
     *
     * @param toMult the vector by which to multiply this vector.
     * @return this vector.
     * @throws NullPointerException if the vector argument passed into this
     * method is null.
     */
    public final Vec3f multLocal(final Vec3f toMult) {
        x *= toMult.x;
        y *= toMult.y;
        z *= toMult.z;
        return this;
    }

    /**
     * Internally multiplies this vector by three floating-point values. This
     * method does not create any additional objects and returns a reference to
     * this vector for the purpose of call chaining.
     * <br /><br />
     * Simple description:<br />this(this.x * xToMult, this.y * yToMult, this.z
     * * zToMult)
     *
     * @param xToMult the amount by which to multiply the x component.
     * @param yToMult the amount by which to multiply the y component.
     * @param zToMult the amount by which to multiply the z component.
     * @return this vector
     */
    public final Vec3f multLocal(final float xToMult, final float yToMult, final float zToMult) {
        x *= xToMult;
        y *= yToMult;
        z *= zToMult;
        return this;
    }

    /**
     * Internally multiplies each component of this vector by a scalar. This
     * method does not create any additional objects and returns a reference to
     * this vector for the purpose of call chaining.
     * <br /><br />
     * Simple description:<br />this(this.x * amount, this.y * amount, this.z *
     * amount)
     *
     * @param amount the amount by which to multiply each component.
     * @return this vector.
     */
    public final Vec3f multLocal(final float amount) {
        x *= amount;
        y *= amount;
        z *= amount;
        return this;
    }

    /**
     * Generates a vector defined by dividing this vector by another vector.
     * Neither this vector nor the provided vector are internally modified in
     * any way. Passing null arguments into this method is not permitted. This
     * method creates and returns a new vector object each time it is called.
     * <br /><br />
     * Simple description: new vec3f(this.x / other.x, this.y / other.y, this.z
     * / other.z)
     *
     * @param toDiv the vector by which to divide this vector.
     * @return a vector equal to the quotient of this vector and another vector.
     * @throws NullPointerException if the vector argument passed into this
     * method is null.
     */
    public final Vec3f divide(final Vec3f toDiv) {
        return new Vec3f(x / toDiv.x, y / toDiv.y, z / toDiv.z);
    }

    /**
     * Generates a vector defined by dividing this vector by three
     * floating-point values. This vector is not internally modified in any way
     * by this method. A new vector object is created and returned each time
     * this method is called.
     * <br /><br />
     * Simple description: new vec3f(this.x / xToDiv, this.y / yToDiv, this.z /
     * zToDiv)
     *
     * @param xToDiv amount by which to divide the x component.
     * @param yToDiv amount by which to divide the y component.
     * @param zToDiv amount by which to divide the z component.
     * @return the generated vector.
     */
    public final Vec3f divide(final float xToDiv, final float yToDiv, final float zToDiv) {
        return new Vec3f(x / xToDiv, y / yToDiv, z / zToDiv);
    }

    /**
     * Generates a vector defined by dividing each component by a value. This
     * vector is not internally modified in any way by this method. A new vector
     * object is created and returned each time this method is called.
     * <br /><br />
     * Simple description: new vec3f(this.x / amount, this.y / amount, this.z /
     * amount)
     *
     * @param amount the amount by which to divide each component.
     * @return the generated vector.
     */
    public final Vec3f divide(final float amount) {
        return new Vec3f(x / amount, y / amount, z / amount);
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
     * other.z)
     *
     * @param toDiv the vector by which to divide this vector.
     * @param toStore the vector in which to store the result.
     * @return the stored vector.
     * @throws NullPointerException if either vector argument passed into this
     * method is null.
     */
    public final Vec3f divide(final Vec3f toDiv, final Vec3f toStore) {
        return toStore.set(x / toDiv.x, y / toDiv.y, z / toDiv.z);
    }

    /**
     * Internally divides this vector by another vector. The vector passed into
     * this method is not internally modified in any way. Passing null arguments
     * into this method is not permitted. This method does not create any
     * additional objects and returns a reference to this vector for the purpose
     * of call chaining.
     * <br /><br />
     * Simple description:<br />this(this.x / other.x, this.y / other.y, this.z
     * / other.z)
     *
     * @param toDiv the vector by which to divide this vector.
     * @return this vector.
     * @throws NullPointerException if the vector argument passed into this
     * method is null.
     */
    public final Vec3f divideLocal(final Vec3f toDiv) {
        x /= toDiv.x;
        y /= toDiv.y;
        z /= toDiv.z;
        return this;
    }

    /**
     * Internally divides this vector by three floating-point values. This
     * method does not create any additional objects and returns a reference to
     * this vector for the purpose of call chaining.
     * <br /><br />
     * Simple description:<br />this(this.x / xToDiv, this.y / yToDiv, this.z /
     * zToDiv)
     *
     * @param xToDiv the amount by which to divide the x component.
     * @param yToDiv the amount by which to divide the y component.
     * @param zToDiv the amount by which to divide the z component.
     * @return this vector
     */
    public final Vec3f divideLocal(final float xToDiv, final float yToDiv, final float zToDiv) {
        x /= xToDiv;
        y /= yToDiv;
        z /= zToDiv;
        return this;
    }

    /**
     * Internally divides each component of this vector by a value. This method
     * does not create any additional objects and returns a reference to this
     * vector for the purpose of call chaining.
     * <br /><br />
     * Simple description:<br />this(this.x / amount, this.y / amount, this.z /
     * amount)
     *
     * @param amount the amount by which to divide each component.
     * @return this vector.
     */
    public final Vec3f divideLocal(final float amount) {
        x /= amount;
        y /= amount;
        z /= amount;
        return this;
    }

    /**
     * Generates a vector equal to this vector crossed with another vector.
     * Neither this vector nor the first vector argument are internally modified
     * in any way by this method. Passing null arguments into this method is not
     * permitted. A new vector object is created and returned each time this
     * method is called.
     *
     * @param vecToCross the vector to cross with this vector.
     * @return the generated vector.
     */
    public final Vec3f cross(final Vec3f vecToCross) {
        return new Vec3f((y * vecToCross.z) - (z * vecToCross.y), (z * vecToCross.x) - (x * vecToCross.z), (x * vecToCross.y) - (y * vecToCross.x));
    }

    /**
     * Internally crosses this vector with another vector. The first vector
     * argument is not internally modified in any way by this method. Passing
     * null arguments into this method is not permitted. No additional objects
     * are created by this method. A reference to this vector is returned for
     * the purpose of call chaining.
     *
     * @param vecToCross the vector to cross with this vector.
     * @return the generated vector.
     */
    public final Vec3f crossLocal(final Vec3f vecToCross) {
        x = (y * vecToCross.z) - (z * vecToCross.y);
        y = (z * vecToCross.x) - (x * vecToCross.z);
        z = (x * vecToCross.y) - (y * vecToCross.x);
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
    public final Vec3f interpolate(final Vec3f toVector, final float interpolateAmount) {
        return new Vec3f((1f - interpolateAmount) * x + interpolateAmount * toVector.x, (1f - interpolateAmount) * y + interpolateAmount * toVector.y, (1f - interpolateAmount) + interpolateAmount * toVector.z);
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
    public final Vec3f interpolateLocal(final Vec3f toVector, final float interpolateAmount) {
        x = (1f - interpolateAmount) * x + interpolateAmount * toVector.x;
        y = (1f - interpolateAmount) * y + interpolateAmount * toVector.y;
        z = (1f - interpolateAmount) * z + interpolateAmount * toVector.z;
        return this;
    }

    /**
     * Creates a copy of this vector and multiplies it by negative one. A new
     * vector object is created and returned each time this method is called.
     *
     * @return a copy of this vector multiplied by -1.
     */
    public final Vec3f negate() {
        return new Vec3f(-x, -y, -z);
    }

    /**
     * Internally multiplies each component of this vector by negative one. This
     * method does not create any additional objects and returns a reference to
     * this vector for the purpose of call chaining.
     *
     * @return this vector.
     */
    public final Vec3f negateLocal() {
        setX(-x);
        setY(-y);
        setZ(-z);
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
    public final Vec3f truncate(final float limit) {
        if (length() <= limit) {
            return new Vec3f(this);
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
    public final Vec3f truncateLocal(final float limit) {
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
    public final Vec3f normalize() {
        return new Vec3f(this).truncateLocal(1f);
    }

    /**
     * Internally normalizes this vector so that the sum of all its components
     * equal one. This method is equivalent to calling truncateLocal(1f).
     *
     * @return this vector.
     */
    public final Vec3f normalizeLocal() {
        truncateLocal(1f);
        return this;
    }

    /**
     * Generates a copy of this value with the components rounded to the closest
     * integer with ties rounding up.
     *
     * @return the generated vector.
     */
    public final Vec3f round() {
        return new Vec3f(FastMath.round(x), FastMath.round(y), FastMath.round(z));
    }

    /**
     * Internally rounds the components of this vector to the closest integer
     * with ties rounding up.
     *
     * @return this vector.
     */
    public final Vec3f roundLocal() {
        x = FastMath.round(x);
        y = FastMath.round(y);
        z = FastMath.round(z);
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
    public final float dot(final Vec3f toDot) {
        return (x * toDot.x) + (y * toDot.y) + (z * toDot.z);
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
    public final float angleBetween(final Vec3f other) {
        return FastMath.acos(dot(other));
    }

    /**
     * Calculates the length of this vector.
     *
     * @return the length (magnitude) of this vector.
     */
    public final float length() {
        return FastMath.sqrt((x * x) + (y * y) + (z * z));
    }

    /**
     * Calculates the length of this vector, squared.
     *
     * @return the length (magnitude) of this vector, squared.
     */
    public final float lengthSquared() {
        return (x * x) + (y * y) + (z * z);
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
    public final float distance(final Vec3f otherVector) {
        return (x - otherVector.x) + (y - otherVector.y) + (z - otherVector.z);
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
    public final float distanceSquared(final Vec3f otherVector) {
        return ((x - otherVector.x) * (x - otherVector.x)) + ((y - otherVector.y) * (y - otherVector.y)) + ((z - otherVector.z) * (z - otherVector.z));
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
     * Internally sets the x, y, and z components of this vector. Returns a
     * reference to this vector for the purpose of call chaining.
     *
     * @param xToSet the x component to set.
     * @param yToSet the y component to set.
     * @param zToSet the z component to set.
     * @return this vector.
     */
    public final Vec3f set(final float xToSet, final float yToSet, final float zToSet) {
        x = xToSet;
        y = yToSet;
        z = zToSet;
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
    public final Vec3f set(final Vec3f toCopy) {
        x = toCopy.x;
        y = toCopy.y;
        z = toCopy.z;
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
     * Indicates if the specified object is equal to this vector. This method
     * returns true if and only if the provided object is an instance of
     * Vector3f and its {@link #x X}, {@link #y Y}, and {@link #z Z} components
     * are equal to the X, Y, and Z components of this vector.
     *
     * @param obj the object to compare with this vector.
     * @return true if the object is an instance of Vector3f and is equal to
     * this vector, false otherwise.
     */
    @Override
    public final boolean equals(final Object obj) {
        if (obj instanceof Vec3f) {
            Vec3f v = (Vec3f) obj;
            return v.x == x && v.y == y && v.z == z;
        }
        return false;
    }

    /**
     * Hash code generated based on the values of the internal
     * {@link #x X}, {@link #y Y}, and {@link #z Z} fields.
     *
     * @return the hash code calculated for this vector.
     */
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 29 * hash + Float.floatToIntBits(this.x);
        hash = 29 * hash + Float.floatToIntBits(this.y);
        hash = 29 * hash + Float.floatToIntBits(this.z);
        return hash;
    }

    /**
     * Returns a String representation of this vector.
     *
     * @return a String representation of the vector as (x,y,z)
     */
    @Override
    public final String toString() {
        return "(" + x + "," + y + "," + z + ")";
    }
}
