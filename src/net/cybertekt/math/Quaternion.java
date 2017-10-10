package net.cybertekt.math;

import net.cybertekt.util.FastMath;

/**
 * Quaternion - (C) Cybertekt Software.
 *
 * Defines a rotation or orientation in three dimensional space. Unlike Euler
 * angles, quaternions are simpler to compose and not susceptible to gimbal
 * lock. They are also more compact and can be calculated faster than rotation
 * matrices. A feature of quaternions is that multiplication of two quaternions
 * is non-commutative. This class provides methods for performing various
 * mathematical operations with on and between quaternions.
 *
 * @version 1.0.0
 * @since 1.0.0
 * @author Andrew Vektor
 */
public final class Quaternion {

    /**
     * Defines an identity quaternion (0f, 0f, 0f, 1f).
     */
    public static final Quaternion Identity = new Quaternion();

    /**
     * Defines a zero quaternion (0f, 0f, 0f, 0f).
     */
    public static final Quaternion Zero = new Quaternion(0f, 0f, 0f, 0f);

    /**
     * Four components of the quaternion: x, y, z, and w.
     */
    protected float x, y, z, w;

    /**
     * Constructs an identity quaternion (0f, 0f, 0f, 1f).
     */
    public Quaternion() {
        x = y = z = 0;
        w = 1;
    }

    /**
     * Constructs a quaternion from four floating-point values.
     *
     * @param xToSet the x component to set.
     * @param yToSet the y component to set.
     * @param zToSet the z component to set.
     * @param wToSet the w component to set.
     */
    public Quaternion(final float xToSet, final float yToSet, final float zToSet, final float wToSet) {
        x = xToSet;
        y = yToSet;
        z = zToSet;
        w = wToSet;
    }

    /**
     * Constructs a quaternion defined by copying the values from another
     * quaternion. The provided quaternion is not internally modified in any
     * way.
     *
     * @param toCopy the quaternion from which to copy.
     */
    public Quaternion(final Quaternion toCopy) {
        x = toCopy.x;
        y = toCopy.y;
        z = toCopy.z;
        w = toCopy.w;
    }

    /**
     * Constructs a quaternion from three Euler rotation angles in degrees.
     *
     * @param pitch angle, in degrees, of rotation around the x axis.
     * @param yaw angle, in degrees, of rotation around the y axis.
     * @param roll angle, in degrees, of rotation around the z axis.
     */
    public Quaternion(final float pitch, final float yaw, final float roll) {
        float xAngle = FastMath.DEG_TO_RAD * pitch;
        float yAngle = FastMath.DEG_TO_RAD * yaw;
        float zAngle = FastMath.DEG_TO_RAD * roll;

        float sinX = FastMath.sin(xAngle * 0.5f);
        float sinY = FastMath.sin(yAngle * 0.5f);
        float sinZ = FastMath.sin(zAngle * 0.5f);

        float cosX = FastMath.cos(xAngle * 0.5f);
        float cosY = FastMath.cos(yAngle * 0.5f);
        float cosZ = FastMath.cos(zAngle * 0.5f);

        w = ((cosY * cosZ) * cosX - (sinY * sinZ) * sinX);
        x = ((cosY * cosZ) * sinX + (sinY * sinZ) * cosX);
        y = ((sinY * cosZ) * cosX + (cosY * sinZ) * sinX);
        z = ((cosY * sinZ) * cosX - (sinY * cosZ) * sinX);

        normalizeLocal();
    }

    /**
     * Constructs a quaternion from a rotation angle and rotation axis.
     *
     * @param angle the amount to rotate in degrees.
     * @param axis the axis of which to rotate around.
     */
    public Quaternion(final float angle, final Vec3f axis) {
        fromAngleAxis(angle, axis);
    }

    /**
     * Constructs a quaternion by interpolating between two other quaternions.
     *
     * @param q1 the first quaternion.
     * @param q2 the second quaternion.
     * @param amt the amount to interpolate between the quaternions.
     */
    public Quaternion(final Quaternion q1, final Quaternion q2, final float amt) {
        set(q1);
        slerp(q2, amt);
    }

    /**
     * Internally sets the components of this quaternion from an angle (in
     * degrees) and an axis of rotation. The vector argument is not modified in
     * any way by this method. Null as an argument for the axis vector is not
     * permitted. Returns a reference to this quaternion for the purpose of call
     * chaining.
     *
     * @param angle the rotation amount in degrees.
     * @param axis the axis to rotate about.
     * @return this quaternion.
     */
    public final Quaternion fromAngleAxis(final float angle, final Vec3f axis) {
        Vec3f rotationAxis;
        float rotationAngle = FastMath.DEG_TO_RAD * angle;

        if (axis.isNormalized()) {
            rotationAxis = axis;
        } else {
            rotationAxis = axis.normalize();
        }

        if (rotationAxis.length() == 0) {
            x = y = z = 0;
            w = 1;
        } else {
            float sin = FastMath.sin(rotationAngle * 0.5f);
            w = FastMath.cos(rotationAngle * 0.5f);
            x = sin * rotationAxis.x;
            y = sin * rotationAxis.y;
            z = sin * rotationAxis.z;
        }
        return this;
    }

    /**
     * Internally calculate the components of this quaternion from three Euler
     * angles specified in degrees. Passing null as an argument into this method
     * is not permitted. The array passed into this method must have a length of
     * exactly three. The array itself will not be internally modified by this
     * method. No additional objects are created as a result of calling this
     * method. Returns a reference to this quaternion for the purpose of call
     * chaining.
     *
     * @param angles the array of angles from which to set the components.
     * @return this quaternion.
     * @throws IllegalArgumentException if the array is null or the wrong size.
     */
    public final Quaternion fromAngles(float[] angles) {
        if (angles == null || angles.length != 3) {
            throw new IllegalArgumentException("Angles array must have three elements.");
        }
        return fromAngles(angles[0], angles[1], angles[2]);
    }

    /**
     * Internally calculates the components of this quaternion from three Euler
     * angles specified in degrees. No additional objects are created as a
     * result of calling this method. Returns a reference to this quaternion for
     * the purpose of call chaining.
     *
     * @param xAngle the amount of rotation about the x-axis, in degrees.
     * @param yAngle the amount of rotation about the y-axis, in degrees.
     * @param zAngle the amount of rotation about the z-axis, in degrees.
     * @return this quaternion.
     */
    public final Quaternion fromAngles(final float xAngle, final float yAngle, final float zAngle) {
        float angle;
        float sinY, sinZ, sinX, cosY, cosZ, cosX;
        angle = zAngle * 0.5f;
        sinZ = FastMath.sin(angle);
        cosZ = FastMath.cos(angle);
        angle = yAngle * 0.5f;
        sinY = FastMath.sin(angle);
        cosY = FastMath.cos(angle);
        angle = xAngle * 0.5f;
        sinX = FastMath.sin(angle);
        cosX = FastMath.cos(angle);

        float cosYXcosZ = cosY * cosZ;
        float sinYXsinZ = sinY * sinZ;
        float cosYXsinZ = cosY * sinZ;
        float sinYXcosZ = sinY * cosZ;

        w = (cosYXcosZ * cosX - sinYXsinZ * sinX);
        x = (cosYXcosZ * sinX + sinYXsinZ * cosX);
        y = (sinYXcosZ * cosX + cosYXsinZ * sinX);
        z = (cosYXsinZ * cosX - sinYXcosZ * sinX);

        normalizeLocal();
        return this;
    }

    /**
     * Generates an array of floats that contain the rotation of the quaternion
     * converted to Euler rotation angles. The array will have a length of three
     * which each component representing an axis of rotation stored in the
     * order: x, y, and then z. A float array object is created and returned
     * each time this method is called.
     *
     * @return the float array containing the rotation of this quaternion
     * converted to Euler rotation angles.
     */
    public final float[] toAngles() {
        float[] angles = new float[3];
        float sqw = FastMath.squared(w);
        float sqx = FastMath.squared(x);
        float sqy = FastMath.squared(y);
        float sqz = FastMath.squared(z);
        float unit = sqx + sqy + sqz + sqw;
        float test = x * y + z * w;
        if (test > 0.499f * unit) {
            angles[0] = 0;
            angles[1] = 2f * FastMath.atan2(x, w);
            angles[2] = FastMath.HALF_PI;
        } else if (test < -0.499f * unit) {
            angles[0] = 0;
            angles[1] = -2f * FastMath.atan2(x, w);
            angles[2] = -FastMath.HALF_PI;
        } else {
            angles[0] = FastMath.atan2(2f * x * w - 2 * y * z, -sqx + sqy - sqz + sqw);
            angles[1] = FastMath.atan2(2f * y * w - 2 * x * z, sqx - sqy - sqz + sqw);
            angles[2] = FastMath.asin(2 * test / unit);
        }
        return angles;
    }

    /**
     * Generates a quaternion defined by adding another quaternion to this
     * quaternion. Neither this quaternion nor the provided quaternion are
     * internally modified in any way by this method. Passing null arguments
     * into this method is not permitted. This method creates and returns a new
     * quaternion object each time it is called.
     *
     * @param toAdd the quaternion to add to this quaternion.
     * @return the generated quaternion.
     * @throws NullPointerException if any argument passed into this method is
     * null.
     */
    public final Quaternion add(final Quaternion toAdd) {
        return new Quaternion(x + toAdd.x, y + toAdd.y, z + toAdd.z, w + toAdd.w);
    }

    /**
     * Internally adds another quaternion to this quaternion. The provided
     * quaternion is not internally modified in any way by this method. Passing
     * null arguments into this method is not permitted. Returns a reference to
     * this quaternion for the purpose of call chaining.
     *
     * @param toAdd the quaternion to add to this quaternion.
     * @return this quaternion.
     */
    public final Quaternion addLocal(final Quaternion toAdd) {
        x += toAdd.x;
        y += toAdd.y;
        z += toAdd.z;
        w += toAdd.w;
        return this;
    }

    /**
     * Generates a quaternion defined by subtracting another quaternion from
     * this quaternion. Neither this quaternion nor the provided quaternion are
     * internally modified in any way by this method. Passing null arguments
     * into this method is not permitted. This method creates and returns a new
     * quaternion object each time it is called.
     *
     * @param toSubtract the quaternion to subtract from this quaternion.
     * @return the generated quaternion.
     * @throws NullPointerException if any argument passed into this method is
     * null.
     */
    public final Quaternion subtract(final Quaternion toSubtract) {
        return new Quaternion(x - toSubtract.x, y - toSubtract.y, z - toSubtract.z, w - toSubtract.w);
    }

    /**
     * Internally subtracts another quaternion from this quaternion. The
     * provided quaternion is not internally modified in any way by this method.
     * Passing null arguments into this method is not permitted. Returns a
     * reference to this quaternion for the purpose of call chaining.
     *
     * @param toSubtract the quaternion to subtract from this quaternion.
     * @return this quaternion.
     */
    public final Quaternion subtractLocal(final Quaternion toSubtract) {
        x -= toSubtract.x;
        y -= toSubtract.y;
        z -= toSubtract.z;
        w -= toSubtract.w;
        return this;
    }

    /**
     * Generates a quaternion defined by multiplying this quaternion by another
     * quaternion. Neither this quaternion nor the provided quaternion are
     * internally modified in any way by this method. Passing null arguments
     * into this method is not permitted. This method creates and returns a new
     * quaternion object each time it is called.
     *
     * @param toMult the quaternion by which to multiply this quaternion.
     * @return the generated quaternion.
     * @throws NullPointerException if any argument passed into this method is
     * null.
     */
    public final Quaternion mult(final Quaternion toMult) {
        float rx, ry, rz, rw;
        rx = x * toMult.w + y * toMult.z - z * toMult.y + w * toMult.x;
        ry = -x * toMult.z + y * toMult.w + z * toMult.x + w * toMult.y;
        rz = x * toMult.y - y * toMult.x + z * toMult.w + w * toMult.z;
        rw = -x * toMult.x - y * toMult.y - z * toMult.z - w * toMult.w;
        return new Quaternion(rx, ry, rz, rw);
    }

    /**
     * Calculates the product of this quaternion and another quaternion, storing
     * the result in the second 'storage' quaternion. Neither this quaternion
     * nor the first quaternion argument are internally modified in any way by
     * this method. Passing null arguments into this method is not permitted. No
     * additional objects are created by this method. A reference to the second
     * 'storage' quaternion is returned for the purpose of call chaining.
     *
     * @param toMult the quaternion by which to multiply this vector.
     * @param toStore the quaternion in which to store the result.
     * @return the store quaternion.
     * @throws NullPointerException if either vector argument passed into this
     * method is null.
     */
    public final Quaternion mult(final Quaternion toMult, Quaternion toStore) {
        float qw = toMult.w, qx = toMult.x, qy = toMult.y, qz = toMult.z;
        toStore.x = x * qw + y * qz - z * qy + w * qx;
        toStore.y = -x * qz + y * qw + z * qx + w * qy;
        toStore.z = x * qy - y * qx + z * qw + w * qz;
        toStore.w = -x * qx - y * qy - z * qz + w * qw;
        return toStore;
    }

    /**
     * Internally multiplies this quaternion by another quaternion. The provided
     * quaternion is not internally modified in any way by this method. Passing
     * null arguments into this method is not permitted. Returns a reference to
     * this quaternion for the purpose of call chaining.
     *
     * @param toMult the quaternion by which to multiply this quaternion.
     * @return this quaternion.
     */
    public final Quaternion multLocal(final Quaternion toMult) {
        float rx, ry, rz, rw;
        rx = x * toMult.w + y * toMult.z - z * toMult.y + w * toMult.x;
        ry = -x * toMult.z + y * toMult.w + z * toMult.x + w * toMult.y;
        rz = x * toMult.y - y * toMult.x + z * toMult.w + w * toMult.z;
        rw = -x * toMult.x - y * toMult.y - z * toMult.z + w * toMult.w;
        set(rx, ry, rz, rw);
        return this;
    }

    public final Vec3f mult(final Vec3f toMult) {
        return mult(toMult, new Vec3f());
    }

    /**
     * Multiplies a vector by this quaternion and stores the result in the store
     * argument.
     *
     * @param v
     * @param store
     * @return
     */
    public final Vec3f mult(final Vec3f v, Vec3f store) {
        if (v.x == 0 && v.y == 0 && v.z == 0) {
            store.set(0, 0, 0);
        } else {
            float vx = v.x, vy = v.y, vz = v.z;
            store.x = w * w * vx + 2 * y * w * vz - 2 * z * w * vy + x * x
                    * vx + 2 * y * x * vy + 2 * z * x * vz - z * z * vx - y
                    * y * vx;
            store.y = 2 * x * y * vx + y * y * vy + 2 * z * y * vz + 2 * w
                    * z * vx - z * z * vy + w * w * vy - 2 * x * w * vz - x
                    * x * vy;
            store.z = 2 * x * z * vx + 2 * y * z * vy + z * z * vz - 2 * w
                    * y * vx - y * y * vz + 2 * w * x * vy - x * x * vz + w
                    * w * vz;
        }
        return store;
    }

    public Vec3f multLocal(Vec3f toMult) {
        float tX, tY;
        tX = w * w * toMult.x + 2 * y * w * toMult.z - 2 * z * w * toMult.y + x * x * toMult.x
                + 2 * y * x * toMult.y + 2 * z * x * toMult.z - z * z * toMult.x - y * y * toMult.x;
        tY = 2 * x * y * toMult.x + y * y * toMult.y + 2 * z * y * toMult.z + 2 * w * z
                * toMult.x - z * z * toMult.y + w * w * toMult.y - 2 * x * w * toMult.z - x * x
                * toMult.y;
        toMult.setZ(2 * x * z * toMult.x + 2 * y * z * toMult.y + z * z * toMult.z - 2 * w * y * toMult.x
                - y * y * toMult.z + 2 * w * x * toMult.y - x * x * toMult.z + w * w * toMult.z);
        toMult.setX(tX);
        toMult.setY(tY);
        return toMult;
    }

    /**
     * Returns a new quaternion scaled by the parameter scalar.
     *
     * @param scalar the amount to scale the quaternion.
     * @return a new quaternion scaled by the parameter scalar.
     */
    public final Quaternion scale(final float scalar) {
        return new Quaternion(x * scalar, y * scalar, z * scalar, w * scalar);
    }

    /**
     * Internally scales this quaternion by the parameter scalar. Returns a
     * reference to this quaternion for easy call chaining.
     *
     * @param scalar the amount to scale this quaternion.
     * @return this quaternion.
     */
    public final Quaternion scaleLocal(final float scalar) {
        x *= scalar;
        y *= scalar;
        z *= scalar;
        w *= scalar;
        return this;
    }

    /**
     * Constructs and returns a normalized copy of this quaternion.
     *
     * @return a normalized copy of this quaternion.
     */
    public Quaternion normalize() {
        float n = 1f / length();
        return new Quaternion(x * n, y * n, z * n, w * n);
    }

    /**
     * Internally normalizes the quaternion. Returns a reference to this
     * quaternion for easy call chaining.
     *
     * @return this quaternion normalized.
     */
    public final Quaternion normalizeLocal() {
        float n = 1f / length();
        x *= n;
        y *= n;
        z *= n;
        w *= n;
        return this;
    }

    /**
     * Negates each component of the quaternion and returns the result as a new
     * quaternion.
     *
     * @return the negated quaternion.
     */
    public final Quaternion negate() {
        return new Quaternion(-x, -y, -z, -w);
    }

    /**
     * Internally negates each component of the quaternion. Returns a reference
     * to this quaternion for easy call chaining.
     *
     * @return this quaternion.
     */
    public final Quaternion negateLocal() {
        x = -x;
        y = -y;
        z = -z;
        w = -w;
        return this;
    }

    /**
     * Returns the dot product of this quaternion with itself.
     *
     * @return the dot product of this quaternion with itself.
     */
    public final float norm() {
        return w * w + x * x + y * y + z * z;
    }

    /**
     * Internally performs a spherical linear interpolation from the values of
     * this quaternion towards the values of the parameter quaternion by the
     * parameter amount. Returns a reference to this quaternion for easy call
     * chaining.
     *
     * @param otherQuaternion the quaternion to interpolate towards.
     * @param amount the amount to interpolate.
     * @return this quaternion.
     */
    public final Quaternion slerp(final Quaternion otherQuaternion, final float amount) {
        Quaternion q2 = new Quaternion(otherQuaternion);

        if (x == q2.x && y == q2.y && z == q2.z && w == q2.w) {
            return this;
        }

        float dot = dot(q2);

        if (dot < 0.0f) {
            q2.negateLocal();
            dot = -dot;
        }

        float scale0 = 1f - amount;
        float scale1 = amount;

        if ((1 - dot) > 0.1f) {
            float theta = FastMath.acos(dot);
            float invSinTheta = 1f / FastMath.sin(theta);

            scale0 = FastMath.sin(((1f - amount) * theta) * invSinTheta);
            scale1 = FastMath.sin((amount * theta) * invSinTheta);
        }

        x = (scale0 * x) + (scale1 * q2.x);
        y = (scale0 * y) + (scale1 * q2.y);
        z = (scale0 * z) + (scale1 * q2.z);
        w = (scale0 * w) + (scale1 * q2.w);
        return this;
    }

    /**
     * Internally performs a spherical linear interpolation from the values of
     * this quaternion towards the values of the parameter quaternion by the
     * parameter amount. Returns a reference to this quaternion for easy call
     * chaining.
     *
     * @param otherQuaternion the quaternion to interpolate towards.
     * @param amount the amount to interpolate.
     * @return this quaternion.
     */
    public final Quaternion nlerp(final Quaternion otherQuaternion, final float amount) {
        float dot = dot(otherQuaternion);
        float scale = 1f - amount;

        if (dot < 0f) {
            x = (x * scale) - (otherQuaternion.x * scale);
            y = (y * scale) - (otherQuaternion.y * scale);
            z = (z * scale) - (otherQuaternion.z * scale);
            w = (w * scale) - (otherQuaternion.w * scale);
        } else {
            x = (x * scale) + (otherQuaternion.x * scale);
            y = (y * scale) + (otherQuaternion.y * scale);
            z = (z * scale) + (otherQuaternion.z * scale);
            w = (w * scale) + (otherQuaternion.w * scale);
        }
        normalizeLocal();
        return this;
    }

    /**
     * Returns the Euclidian length of this quaternion.
     *
     * @return the length of the quaternion.
     */
    public final float length() {
        return FastMath.sqrt(w * w + x * x + y * y + z * z);
    }

    /**
     * Calculates the dot product of this quaternion and another quaternion. The
     * quaternion passed into this method is not internally modified in any way.
     * Passing null arguments into this method is not permitted. This method
     * does not create any additional objects.
     *
     * @param toDot the quaternion to dot with this quaternion.
     * @return the dot product of this quaternion and the other quaternion.
     */
    public final float dot(final Quaternion toDot) {
        return (x * toDot.x) + (y * toDot.y) + (z * toDot.z) + (w * toDot.w);
    }

    /**
     * Internally sets the x, y, z, and w components of this quaternion. Returns
     * a reference to this quaternion for the purpose of call chaining.
     *
     * @param xToSet the x component to set.
     * @param yToSet the y component to set.
     * @param zToSet the z component to set.
     * @param wToSet the w component to set.
     * @return this quaternion.
     */
    public final Quaternion set(final float xToSet, final float yToSet, final float zToSet, final float wToSet) {
        x = xToSet;
        y = yToSet;
        z = zToSet;
        w = wToSet;
        return this;
    }

    /**
     * Internally sets the components of this quaternion to be equal to another
     * quaternion. Returns a reference to this quaternion for the purpose of
     * call chaining.
     *
     * @param toCopy the quaternion from which to copy.
     * @return this quaternion.
     */
    public final Quaternion set(final Quaternion toCopy) {
        x = toCopy.x;
        y = toCopy.y;
        z = toCopy.z;
        w = toCopy.w;
        return this;
    }

    /**
     * Returns the x component.
     *
     * @return the x component of this quaternion.
     */
    public final float getX() {
        return x;
    }

    /**
     * Internally sets the x component.
     *
     * @param xToSet the x component of this quaternion to set.
     */
    public final void setX(final float xToSet) {
        x = xToSet;
    }

    /**
     * Returns the y component.
     *
     * @return the y component of this quaternion.
     */
    public final float getY() {
        return y;
    }

    /**
     * Internally sets the y component.
     *
     * @param yToSet the y component of this quaternion to set.
     */
    public final void setY(final float yToSet) {
        y = yToSet;
    }

    /**
     * Returns the z component.
     *
     * @return the z component of this quaternion.
     */
    public final float getZ() {
        return z;
    }

    /**
     * Internally sets the z component.
     *
     * @param zToSet the z component of this quaternion to set.
     */
    public final void setZ(final float zToSet) {
        z = zToSet;
    }

    /**
     * Returns the w component.
     *
     * @return the w component of this quaternion.
     */
    public final float getW() {
        return w;
    }

    /**
     * Internally sets the w component.
     *
     * @param wToSet the w component of this quaternion to set.
     */
    public final void setW(final float wToSet) {
        w = wToSet;
    }
}
