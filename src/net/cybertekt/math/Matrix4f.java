package net.cybertekt.math;

import net.cybertekt.util.FastMath;

/**
 * Matrix4f - (C) Cybertekt Software.
 * <p />
 * Defines a four dimension (4x4) matrix of floats and provides methods for
 * generating matrices and performing mathematical operations between them. A
 * matrix may represent a projection mode, translation (position), rotation, or
 * scale.
 *
 * @version 1.0.0
 * @since 1.0.0
 * @author Andrew Vektor
 */
public final class Matrix4f {

    /**
     * First row of matrix data.
     */
    protected float Xx, Xy, Xz, Xw;

    /**
     * Second row of matrix data.
     */
    protected float Yx, Yy, Yz, Yw;

    /**
     * Third row of matrix data.
     */
    protected float Zx, Zy, Zz, Zw;

    /**
     * Fourth row of matrix data.
     */
    protected float Wx, Wy, Wz, Ww;

    /**
     * Constructs an orthographic (2D) projection matrix.
     *
     * @param left the left vertical clipping pane
     * @param right the right vertical clipping pane
     * @param bottom the bottom horizontal clipping pane
     * @param top the bottom horizontal clipping pane
     * @param near the near depth clipping pane
     * @param far the far depth clipping pane
     * @return the orthographic projection matrix generated.
     */
    public final static Matrix4f orthographic(final float left, final float right, final float bottom, final float top, final float near, final float far) {
        Matrix4f m = new Matrix4f();
        m.Xx = 2f / (right - left);
        m.Yy = 2f / (top - bottom);
        m.Zz = -2f / (far - near);
        m.Xw = -(right + left) / (right - left);
        m.Yw = -(top + bottom) / (top - bottom);
        m.Zw = -(far + near) / (far - near);
        return m;
    }

    /**
     * Generates a perspective (3D) projection matrix.
     *
     * @param aspectRatio screen aspect ratio.
     * @param fov y field of view.
     * @param near the near depth clipping plane.
     * @param far the far depth clipping plane.
     * @return the perspective matrix generated.
     */
    public final static Matrix4f perspective(final float aspectRatio, final float fov, final float near, final float far) {
        Matrix4f m = new Matrix4f();
        //float tan = 1f / FastMath.tan(yFov * FastMath.DEG_TO_RAD / 2f);

        m.Xx = 0f;
        m.Xy = 0f;
        m.Xz = 0f;
        m.Xw = 0f;

        m.Yx = 0f;
        m.Yy = 0f;
        m.Yz = 0f;
        m.Yw = 0f;

        m.Zx = 0f;
        m.Zy = 0f;
        m.Zz = 0f;
        m.Zw = 0f;

        m.Wx = 0f;
        m.Wy = 0f;
        m.Wz = 0f;
        m.Ww = 0f;

        return m;
    }

    /**
     * Generates a frustum matrix.
     *
     * @param left the left vertical clipping pane
     * @param right the right vertical clipping pane
     * @param bottom the bottom horizontal clipping pane
     * @param top the bottom horizontal clipping pane
     * @param near the near depth clipping pane
     * @param far the far depth clipping pane
     * @return the frustum matrix generated.
     */
    public final static Matrix4f frustum(final float left, final float right, final float bottom, final float top, final float near, final float far) {
        Matrix4f m = new Matrix4f();
        m.Xx = (2f * near) / (right - left);
        m.Yy = (2f * near) / (top - bottom);
        m.Xz = (right + left) / (right - left);
        m.Yz = (top + bottom) / (top - bottom);
        m.Zz = -(far + near) / (far - near);
        m.Wz = -1f;
        m.Zw = -(2f * far * near) / (far - near);
        m.Ww = 0f;
        return m;
    }

    /**
     * Generates a translation matrix from a translation vector.
     *
     * @param translation the translation vector.
     * @return the translation matrix generated.
     */
    public final static Matrix4f translation(final Vec3f translation) {
        Matrix4f m = new Matrix4f();
        m.Xw = translation.x;
        m.Yw = translation.y;
        m.Zw = translation.z;
        return m;
    }

    /**
     * Generates a rotation matrix from an angle and axis of rotation.
     *
     * @param angle the rotation angle in degrees from which to generate.
     * @param rotation the rotation axis.
     * @return the rotation matrix generated.
     */
    public final static Matrix4f rotation(float angle, final Vec3f rotation) {
        Matrix4f m = new Matrix4f();
        float cos = FastMath.cos(angle * FastMath.DEG_TO_RAD);
        float sin = FastMath.sin(angle * FastMath.DEG_TO_RAD);
        if (!rotation.isNormalized()) {
            rotation.normalizeLocal();
        }
        m.Xx = rotation.x * rotation.x * (1f - cos) + cos;
        m.Yx = rotation.y * rotation.x * (1f - cos) + rotation.z * sin;
        m.Zx = rotation.x * rotation.z * (1f - cos) - rotation.y * sin;
        m.Xy = rotation.x * rotation.y * (1f - cos) - rotation.z * sin;
        m.Yy = rotation.y * rotation.y * (1f - cos) + cos;
        m.Zy = rotation.y * rotation.z * (1f - cos) + rotation.x * sin;
        m.Xz = rotation.x * rotation.z * (1f - cos) + rotation.y * sin;
        m.Yz = rotation.y * rotation.z * (1f - cos) - rotation.x * sin;
        m.Zz = rotation.z * rotation.z * (1f - cos) + cos;
        return m;
    }

    /**
     * Generates a rotation matrix from a quaternion.
     *
     * @param rot the rotation quaternion from which to construct the rotation
     * matrix.
     * @return the rotation matrix generated from the rotation quaternion.
     */
    public final static Matrix4f rotation(final Quaternion rot) {
        Matrix4f m = new Matrix4f();
        m.setRotation(rot);
        return m;
    }

    /**
     * Generates a scalar matrix from a scale vector.
     *
     * @param scale the scale vector.
     * @return the scalar matrix generated.
     */
    public final static Matrix4f scalar(final Vec3f scale) {
        Matrix4f m = new Matrix4f();
        m.Xx = scale.x;
        m.Yy = scale.y;
        m.Zz = scale.z;
        return m;
    }

    /**
     * Constructs a transformation matrix from a translation (position),
     * rotation (orientation), and scale (size).
     *
     * @param translation the translation component to set.
     * @param rotation the rotation component to set.
     * @param scale the scale component to set.
     * @return the transformation matrix.
     */
    public final static Matrix4f transform(final Vec3f translation, final Quaternion rotation, final Vec3f scale) {
        Matrix4f m = new Matrix4f();
        m.setTranslation(translation);
        m.setRotation(rotation);
        m.setScale(scale);
        return m;
    }

    /**
     * Constructs a transformation matrix from the translation (position),
     * rotation (orientation), and scale (size) of a transform.
     *
     * @param transform the transform to define the matrix.
     * @return the transformation matrix.
     */
    public final static Matrix4f transform(final Transform transform) {
        Matrix4f m = new Matrix4f();
        m.setTranslation(transform.getTranslation());
        m.setRotation(transform.getRotation());
        m.setScale(transform.getScale());
        return m;
    }

    /**
     * Constructs an identity matrix.
     */
    public Matrix4f() {
        Xx = Yy = Zz = Ww = 1f;
    }

    /**
     * Constructs a 4x4 matrix defined by copying the values from another
     * matrix. The provided matrix is not internally modified in any way.
     * Passing null arguments into this constructor is not permitted.
     *
     * @param toCopy the matrix from which to copy.
     * @throws NullPointerException if the matrix argument passed into this
     * constructor is null.
     */
    public Matrix4f(final Matrix4f toCopy) {
        Xx = toCopy.Xx;
        Xy = toCopy.Xy;
        Xz = toCopy.Xz;
        Xw = toCopy.Xw;

        Yx = toCopy.Yx;
        Yy = toCopy.Yy;
        Yz = toCopy.Yz;
        Yw = toCopy.Yw;

        Zx = toCopy.Zx;
        Zy = toCopy.Zy;
        Zz = toCopy.Zz;
        Zw = toCopy.Zw;

        Wx = toCopy.Wx;
        Wy = toCopy.Wy;
        Wz = toCopy.Wz;
        Ww = toCopy.Ww;
    }

    /**
     * Constructs a matrix from an array of floats. The array must not be null
     * and have a length of exactly 16. Transpose should be false if the array
     * is to be read in column-major order or true if the array should be read
     * in row-major order.
     *
     * @param src the array that defines this matrix.
     * @param transpose true if the array is in row-major order.
     */
    public Matrix4f(final float[] src, final boolean transpose) {
        if (src == null) {
            throw new IllegalArgumentException("Cannot create matrix from null source array.");
        } else if (src.length != 16) {
            throw new IllegalArgumentException("Cannot create matrix from array. Length of array must be exactly 16.");
        } else {
            if (transpose) {
                Xx = src[0];
                Xy = src[1];
                Xz = src[2];
                Xw = src[3];

                Yx = src[4];
                Yy = src[5];
                Yz = src[6];
                Yw = src[7];

                Zx = src[8];
                Zy = src[9];
                Zz = src[10];
                Zw = src[11];

                Wx = src[12];
                Wy = src[13];
                Ww = src[14];
                Wz = src[15];
            } else {
                Xx = src[0];
                Yx = src[1];
                Zx = src[2];
                Wx = src[3];

                Xy = src[4];
                Yy = src[5];
                Zy = src[6];
                Wy = src[7];

                Xz = src[8];
                Yz = src[9];
                Zz = src[10];
                Wz = src[11];

                Xw = src[12];
                Yw = src[13];
                Zw = src[14];
                Ww = src[15];
            }
        }
    }

    /**
     * Generates a matrix defined by adding another matrix to this matrix.
     * Neither this matrix nor the provided matrix are internally modified in
     * any way. Passing null arguments into this method is not permitted. This
     * method creates and returns a new matrix object each time it is called.
     *
     * @param toAdd the matrix in which to add to this matrix.
     * @return a matrix equal to the sum of this matrix and another vector.
     * @throws NullPointerException if the matrix argument passed into this
     * method is null.
     */
    public final Matrix4f add(Matrix4f toAdd) {
        Matrix4f result = new Matrix4f();

        result.Xx = Xx + toAdd.Xx;
        result.Yx = Yx + toAdd.Yx;
        result.Zx = Zx + toAdd.Zx;
        result.Wx = Wx + toAdd.Wx;

        result.Xy = Xy + toAdd.Xy;
        result.Yy = Yy + toAdd.Yy;
        result.Zy = Zy + toAdd.Zy;
        result.Wy = Wy + toAdd.Wy;

        result.Xz = Xz + toAdd.Xz;
        result.Yz = Yz + toAdd.Yz;
        result.Zz = Zz + toAdd.Zz;
        result.Wz = Wz + toAdd.Wz;

        result.Xw = Xw + toAdd.Xw;
        result.Yw = Yw + toAdd.Yw;
        result.Zw = Zw + toAdd.Zw;
        result.Ww = Ww + toAdd.Ww;

        return result;
    }

    /**
     * Calculates the sum of this matrix and another matrix, storing the result
     * in the second 'storage' matrix. Neither this matrix nor the first matrix
     * argument are internally modified in any way by this method. Passing null
     * arguments into this method is not permitted. A reference to the second
     * 'storage' matrix is returned for the purpose of call chaining.
     *
     * @param toAdd the matrix in which to add to this matrix.
     * @param toStore the matrix in which to store the result.
     * @return the stored matrix.
     * @throws NullPointerException if the vector argument passed into this
     * method is null.
     */
    public final Matrix4f add(final Matrix4f toAdd, final Matrix4f toStore) {
        toStore.Xx = Xx + toAdd.Xx;
        toStore.Yx = Yx + toAdd.Yx;
        toStore.Zx = Zx + toAdd.Zx;
        toStore.Wx = Wx + toAdd.Wx;

        toStore.Xy = Xy + toAdd.Xy;
        toStore.Yy = Yy + toAdd.Yy;
        toStore.Zy = Zy + toAdd.Zy;
        toStore.Wy = Wy + toAdd.Wy;

        toStore.Xz = Xz + toAdd.Xz;
        toStore.Yz = Yz + toAdd.Yz;
        toStore.Zz = Zz + toAdd.Zz;
        toStore.Wz = Wz + toAdd.Wz;

        toStore.Xw = Xw + toAdd.Xw;
        toStore.Yw = Yw + toAdd.Yw;
        toStore.Zw = Zw + toAdd.Zw;
        toStore.Ww = Ww + toAdd.Ww;

        return toStore;
    }

    /**
     * Internally adds a matrix to this matrix. The matrix passed into this
     * method is not internally modified in any way. Passing null arguments into
     * this method is not permitted. This method does not create any additional
     * objects and returns a reference to this matrix for the purpose of call
     * chaining.
     *
     * @param toAdd the matrix in which to add to this matrix
     * @return this matrix.
     * @throws NullPointerException if the vector argument passed into this
     * method is null.
     */
    public final Matrix4f addLocal(Matrix4f toAdd) {
        Xx += toAdd.Xx;
        Yx += toAdd.Yx;
        Zx += toAdd.Zx;
        Wx += toAdd.Wx;

        Xy += toAdd.Xy;
        Yy += toAdd.Yy;
        Zy += toAdd.Zy;
        Wy += toAdd.Wy;

        Xz += toAdd.Xz;
        Yz += toAdd.Yz;
        Zz += toAdd.Zz;
        Wz += toAdd.Wz;

        Xw += toAdd.Xw;
        Yw += toAdd.Yw;
        Zw += toAdd.Zw;
        Wz += toAdd.Wz;

        return this;
    }

    /**
     * Generates a matrix defined by the difference between this matrix and
     * another matrix. Neither this matrix nor the provided matrix are
     * internally modified in any way. Passing null arguments into this method
     * is not permitted. This method creates and returns a new matrix object
     * each time it is called.
     *
     * @param toSubtract the matrix to subtract from this matrix.
     * @return the generated matrix.
     * @throws NullPointerException if the matrix argument passed into this
     * method is null.
     */
    public final Matrix4f subtract(Matrix4f toSubtract) {
        Matrix4f result = new Matrix4f();

        result.Xx = Xx - toSubtract.Xx;
        result.Yx = Yx - toSubtract.Yx;
        result.Zx = Zx - toSubtract.Zx;
        result.Wx = Wx - toSubtract.Wx;

        result.Xy = Xy - toSubtract.Xy;
        result.Yy = Yy - toSubtract.Yy;
        result.Zy = Zy - toSubtract.Zy;
        result.Wy = Wy - toSubtract.Wy;

        result.Xz = Xz - toSubtract.Xz;
        result.Yz = Yz - toSubtract.Yz;
        result.Zz = Zz - toSubtract.Zz;
        result.Wz = Wz - toSubtract.Wz;

        result.Xw = Xw - toSubtract.Xw;
        result.Yw = Yw - toSubtract.Yw;
        result.Zw = Zw - toSubtract.Zw;
        result.Ww = Ww - toSubtract.Ww;

        return result;
    }

    /**
     * Calculates the difference between this matrix and another matrix, storing
     * the result in the second 'storage' matrix. Neither this matrix nor the
     * first matrix argument are internally modified in any way by this method.
     * Passing null arguments into this method is not permitted. A reference to
     * the second 'storage' matrix is returned for the purpose of call chaining.
     *
     * @param toSubtract the matrix to subtract from this matrix.
     * @param toStore the matrix in which to store the result.
     * @return the stored matrix.
     * @throws NullPointerException if the matrix argument passed into this
     * method is null.
     */
    public final Matrix4f subtract(final Matrix4f toSubtract, final Matrix4f toStore) {
        toStore.Xx = Xx - toSubtract.Xx;
        toStore.Yx = Yx - toSubtract.Yx;
        toStore.Zx = Zx - toSubtract.Zx;
        toStore.Wx = Wx - toSubtract.Wx;

        toStore.Xy = Xy - toSubtract.Xy;
        toStore.Yy = Yy - toSubtract.Yy;
        toStore.Zy = Zy - toSubtract.Zy;
        toStore.Wy = Wy - toSubtract.Wy;

        toStore.Xz = Xz - toSubtract.Xz;
        toStore.Yz = Yz - toSubtract.Yz;
        toStore.Zz = Zz - toSubtract.Zz;
        toStore.Wz = Wz - toSubtract.Wz;

        toStore.Xw = Xw - toSubtract.Xw;
        toStore.Yw = Yw - toSubtract.Yw;
        toStore.Zw = Zw - toSubtract.Zw;
        toStore.Ww = Ww - toSubtract.Ww;

        return toStore;
    }

    /**
     * Internally subtracts a matrix from this matrix. The matrix passed into
     * this method is not internally modified in any way. Passing null arguments
     * into this method is not permitted. This method does not create any
     * additional objects and returns a reference to this matrix for the purpose
     * of call chaining.
     *
     * @param toSubtract the matrix to subtract from this matrix.
     * @return this matrix.
     * @throws NullPointerException if the matrix argument passed into this
     * method is null.
     */
    public final Matrix4f subtractLocal(Matrix4f toSubtract) {
        Xx -= toSubtract.Xx;
        Yx -= toSubtract.Yx;
        Zx -= toSubtract.Zx;
        Wx -= toSubtract.Wx;

        Xy -= toSubtract.Xy;
        Yy -= toSubtract.Yy;
        Zy -= toSubtract.Zy;
        Wy -= toSubtract.Wy;

        Xz -= toSubtract.Xz;
        Yz -= toSubtract.Yz;
        Zz -= toSubtract.Zz;
        Wz -= toSubtract.Wz;

        Xw -= toSubtract.Xw;
        Yw -= toSubtract.Yw;
        Zw -= toSubtract.Zw;
        Wz -= toSubtract.Wz;

        return this;
    }

    /**
     * Generates a matrix defined by multiplying this matrix by another matrix.
     * Neither this matrix nor the provided matrix are internally modified in
     * any way. Passing null arguments into this method is not permitted. This
     * method creates and returns a new matrix object each time it is called.
     *
     * @param toMult the matrix by which to multiply this matrix.
     * @return the generated matrix.
     * @throws NullPointerException if the matrix argument passed into this
     * method is null.
     */
    public Matrix4f mult(final Matrix4f toMult) {
        Matrix4f result = new Matrix4f();

        result.Xx = Xx * toMult.Xx + Xy * toMult.Yx + Xz * toMult.Zx + Xw * toMult.Wx;
        result.Yx = Yx * toMult.Xx + Yy * toMult.Yx + Yz * toMult.Zx + Yw * toMult.Wx;
        result.Zx = Zx * toMult.Xx + Zy * toMult.Yx + Zz * toMult.Zx + Zw * toMult.Wx;
        result.Wx = Wx * toMult.Xx + Wy * toMult.Yx + Wz * toMult.Zx + Ww * toMult.Wx;

        result.Xy = Xx * toMult.Xy + Xy * toMult.Yy + Xz * toMult.Zy + Xw * toMult.Wy;
        result.Yy = Yx * toMult.Xy + Yy * toMult.Yy + Yz * toMult.Zy + Yw * toMult.Wy;
        result.Zy = Zx * toMult.Xy + Zy * toMult.Yy + Zz * toMult.Zy + Zw * toMult.Wy;
        result.Wy = Wx * toMult.Xy + Wy * toMult.Yy + Wz * toMult.Zy + Ww * toMult.Wy;

        result.Xz = Xx * toMult.Xz + Xy * toMult.Yz + Xz * toMult.Zz + Xw * toMult.Wz;
        result.Yz = Yx * toMult.Xz + Yy * toMult.Yz + Yz * toMult.Zz + Yw * toMult.Wz;
        result.Zz = Zx * toMult.Xz + Zy * toMult.Yz + Zz * toMult.Zz + Zw * toMult.Wz;
        result.Wz = Wx * toMult.Xz + Wy * toMult.Yz + Wz * toMult.Zz + Ww * toMult.Wz;

        result.Xw = Xx * toMult.Xw + Xy * toMult.Yw + Xz * toMult.Zw + Xw * toMult.Ww;
        result.Yw = Yx * toMult.Xw + Yy * toMult.Yw + Yz * toMult.Zw + Yw * toMult.Ww;
        result.Zw = Zx * toMult.Xw + Zy * toMult.Yw + Zz * toMult.Zw + Zw * toMult.Ww;
        result.Ww = Wx * toMult.Xw + Wy * toMult.Yw + Wz * toMult.Zw + Ww * toMult.Ww;

        return result;
    }

    /**
     * Generates a matrix defined by multiplying this matrix by a scalar. This
     * matrix is not internally modified in any way by this method. A new matrix
     * object is created and returned each time this method is called.
     *
     * @param scalar the amount by which to scale the matrix.
     * @return the generated matrix.
     */
    public Matrix4f mult(final float scalar) {
        Matrix4f result = new Matrix4f();
        result.Xx = Xx * scalar;
        result.Yx = Yx * scalar;
        result.Zx = Zx * scalar;
        result.Wx = Wx * scalar;

        result.Xy = Xy * scalar;
        result.Yy = Yy * scalar;
        result.Zy = Zy * scalar;
        result.Wy = Wy * scalar;

        result.Xz = Xz * scalar;
        result.Yz = Yz * scalar;
        result.Zz = Zz * scalar;
        result.Wz = Wz * scalar;

        result.Xw = Xw * scalar;
        result.Yw = Yw * scalar;
        result.Zw = Zw * scalar;
        result.Ww = Ww * scalar;

        return result;
    }

    /**
     * Calculates the product of this matrix and another matrix, storing the
     * result in the second 'storage' matrix. Neither this matrix nor the first
     * matrix argument are internally modified in any way by this method.
     * Passing null arguments into this method is not permitted. A reference to
     * the second 'storage' matrix is returned for the purpose of call chaining.
     *
     * @param toMult the matrix by which to multiply this matrix.
     * @param toStore the matrix in which to store the result.
     * @return the stored matrix.
     * @throws NullPointerException if either matrix argument passed into this
     * method is null.
     */
    public Matrix4f mult(final Matrix4f toMult, final Matrix4f toStore) {
        toStore.Xx = Xx * toMult.Xx + Xy * toMult.Yx + Xz * toMult.Zx + Xw * toMult.Wx;
        toStore.Yx = Yx * toMult.Xx + Yy * toMult.Yx + Yz * toMult.Zx + Yw * toMult.Wx;
        toStore.Zx = Zx * toMult.Xx + Zy * toMult.Yx + Zz * toMult.Zx + Zw * toMult.Wx;
        toStore.Wx = Wx * toMult.Xx + Wy * toMult.Yx + Wz * toMult.Zx + Ww * toMult.Wx;

        toStore.Xy = Xx * toMult.Xy + Xy * toMult.Yy + Xz * toMult.Zy + Xw * toMult.Wy;
        toStore.Yy = Yx * toMult.Xy + Yy * toMult.Yy + Yz * toMult.Zy + Yw * toMult.Wy;
        toStore.Zy = Zx * toMult.Xy + Zy * toMult.Yy + Zz * toMult.Zy + Zw * toMult.Wy;
        toStore.Wy = Wx * toMult.Xy + Wy * toMult.Yy + Wz * toMult.Zy + Ww * toMult.Wy;

        toStore.Xz = Xx * toMult.Xz + Xy * toMult.Yz + Xz * toMult.Zz + Xw * toMult.Wz;
        toStore.Yz = Yx * toMult.Xz + Yy * toMult.Yz + Yz * toMult.Zz + Yw * toMult.Wz;
        toStore.Zz = Zx * toMult.Xz + Zy * toMult.Yz + Zz * toMult.Zz + Zw * toMult.Wz;
        toStore.Wz = Wx * toMult.Xz + Wy * toMult.Yz + Wz * toMult.Zz + Ww * toMult.Wz;

        toStore.Xw = Xx * toMult.Xw + Xy * toMult.Yw + Xz * toMult.Zw + Xw * toMult.Ww;
        toStore.Yw = Yx * toMult.Xw + Yy * toMult.Yw + Yz * toMult.Zw + Yw * toMult.Ww;
        toStore.Zw = Zx * toMult.Xw + Zy * toMult.Yw + Zz * toMult.Zw + Zw * toMult.Ww;
        toStore.Ww = Wx * toMult.Xw + Wy * toMult.Yw + Wz * toMult.Zw + Ww * toMult.Ww;

        return toStore;
    }

    /**
     * Internally multiplies this matrix by another matrix. The matrix passed
     * into this method is not internally modified in any way. Passing null
     * arguments into this method is not permitted. This method does not create
     * any additional objects and returns a reference to this matrix for the
     * purpose of call chaining.
     *
     * @param toMult the matrix by which to multiply this matrix.
     * @return this matrix.
     * @throws NullPointerException if the matrix argument passed into this
     * method is null.
     */
    public Matrix4f multLocal(final Matrix4f toMult) {
        float tXx = Xx * toMult.Xx + Xy * toMult.Yx + Xz * toMult.Zx + Xw * toMult.Wx;
        float tYx = Yx * toMult.Xx + Yy * toMult.Yx + Yz * toMult.Zx + Yw * toMult.Wx;
        float tZx = Zx * toMult.Xx + Zy * toMult.Yx + Zz * toMult.Zx + Zw * toMult.Wx;
        float tWx = Wx * toMult.Xx + Wy * toMult.Yx + Wz * toMult.Zx + Ww * toMult.Wx;

        float tXy = Xx * toMult.Xy + Xy * toMult.Yy + Xz * toMult.Zy + Xw * toMult.Wy;
        float tYy = Yx * toMult.Xy + Yy * toMult.Yy + Yz * toMult.Zy + Yw * toMult.Wy;
        float tZy = Zx * toMult.Xy + Zy * toMult.Yy + Zz * toMult.Zy + Zw * toMult.Wy;
        float tWy = Wx * toMult.Xy + Wy * toMult.Yy + Wz * toMult.Zy + Ww * toMult.Wy;

        float tXz = Xx * toMult.Xz + Xy * toMult.Yz + Xz * toMult.Zz + Xw * toMult.Wz;
        float tYz = Yx * toMult.Xz + Yy * toMult.Yz + Yz * toMult.Zz + Yw * toMult.Wz;
        float tZz = Zx * toMult.Xz + Zy * toMult.Yz + Zz * toMult.Zz + Zw * toMult.Wz;
        float tWz = Wx * toMult.Xz + Wy * toMult.Yz + Wz * toMult.Zz + Ww * toMult.Wz;

        float tXw = Xx * toMult.Xw + Xy * toMult.Yw + Xz * toMult.Zw + Xw * toMult.Ww;
        float tYw = Yx * toMult.Xw + Yy * toMult.Yw + Yz * toMult.Zw + Yw * toMult.Ww;
        float tZw = Zx * toMult.Xw + Zy * toMult.Yw + Zz * toMult.Zw + Zw * toMult.Ww;
        float tWw = Wx * toMult.Xw + Wy * toMult.Yw + Wz * toMult.Zw + Ww * toMult.Ww;

        Xx = tXx;
        Xy = tXy;
        Xz = tXz;
        Xw = tXw;
        Yx = tYx;
        Yy = tYy;
        Yz = tYz;
        Yw = tYw;
        Zx = tZx;
        Zy = tZy;
        Zz = tZz;
        Zw = tZw;
        Wx = tWx;
        Wy = tWy;
        Wz = tWz;
        Ww = tWw;

        return this;
    }

    /**
     * Internally multiplies this matrix by a scalar. This method does not
     * create any additional objects and returns a reference to this matrix for
     * the purpose of call chaining.
     *
     * @param scalar the amount by which to scale this matrix.
     * @return this matrix.
     */
    public Matrix4f multLocal(final float scalar) {
        Xx *= scalar;
        Yx *= scalar;
        Zx *= scalar;
        Wx *= scalar;

        Xy *= scalar;
        Yy *= scalar;
        Zy *= scalar;
        Wy *= scalar;

        Xz *= scalar;
        Yz *= scalar;
        Zz *= scalar;
        Wz *= scalar;

        Xw *= scalar;
        Yw *= scalar;
        Zw *= scalar;
        Ww *= scalar;

        return this;
    }

    /**
     * Sets the translation of the matrix from three float values. This method
     * internally modifies this matrix and does not create any additional
     * objects.
     *
     * @param x the x-axis translation to set.
     * @param y the y-axis translation to set.
     * @param z the z-axis translation to set.
     */
    public final void setTranslation(final float x, final float y, final float z) {
        Xw = x;
        Yw = y;
        Zw = z;
    }

    /**
     * Sets the translation of this matrix from a vector. This method internally
     * modifies this matrix and does not create any additional objects.
     *
     * @param toSet the matrix translation to set.
     */
    public final void setTranslation(final Vec3f toSet) {
        setTranslation(toSet.x, toSet.y, toSet.z);
    }

    /**
     * Sets the matrix rotation from a rotation quaternion. This method
     * internally modifies this matrix and does not create any additional
     * objects.
     *
     * @param toSet the matrix rotation to set.
     */
    public final void setRotation(final Quaternion toSet) {
        float norm = toSet.norm();
        float s = (norm == 1f) ? 2f : (norm > 0f) ? 2f / norm : 0;
        float xs = toSet.x * s;
        float ys = toSet.y * s;
        float zs = toSet.z * s;
        float xx = toSet.x * xs;
        float xy = toSet.x * ys;
        float xz = toSet.x * zs;
        float xw = toSet.w * xs;
        float yy = toSet.y * ys;
        float yz = toSet.y * zs;
        float yw = toSet.w * ys;
        float zz = toSet.z * zs;
        float zw = toSet.w * zs;

        Xx = 1f - (yy + zz);
        Xy = xy - zw;
        Xz = xz + yw;

        Yx = xy + zw;
        Yy = 1f - (xx + zz);
        Yz = yz - xw;

        Zx = xz - yw;
        Zy = yz + xw;
        Zz = 1f - (xx + yy);
    }

    /**
     * Sets the scale of the matrix from three float values. This method
     * internally modifies this matrix and does not create any additional
     * objects.
     *
     * @param x the x-axis scale to set.
     * @param y the y-axis scale to set.
     * @param z the z-axis scale to set.
     */
    public final void setScale(final float x, final float y, final float z) {
        Vec3f v = new Vec3f();

        v.set(Xx, Yx, Zx);
        v.normalizeLocal();
        Xx = v.x * x;
        Yx = v.y * x;
        Zx = v.z * x;

        v.set(Xy, Yy, Zy);
        v.normalizeLocal();
        Xy = v.x * y;
        Yy = v.y * y;
        Zy = v.z * y;

        v.set(Xz, Yz, Zz);
        v.normalizeLocal();
        Xz = v.x * z;
        Yz = v.y * z;
        Zz = v.z * z;
    }

    /**
     * Sets the scale of the matrix from a scalar vector. This method internally
     * modifies this matrix and does not create any additional objects.
     *
     * @param toSet the matrix scale to set.
     */
    public final void setScale(final Vec3f toSet) {
        setScale(toSet.x, toSet.y, toSet.z);
    }

    /**
     * Sets the 2D projection matrix values from based on the boundaries of a
     * view port. This method internally modifies this matrix and does not
     * create any additional objects. A reference to this matrix is returned for
     * the purpose of call chaining.
     *
     * @param left the left vertical clipping pane
     * @param right the right vertical clipping pane
     * @param bottom the bottom horizontal clipping pane
     * @param top the bottom horizontal clipping pane
     * @param near the near depth clipping pane
     * @param far the far depth clipping pane
     */
    public final void fromOrthographic(final float left, final float right, final float bottom, final float top, final float near, final float far) {
        setIdentity();
        Xx = 2f / (right - left);
        Yy = 2f / (top - bottom);
        Zz = -2f / (far - near);
        Xw = -(right + left) / (right - left);
        Yw = -(top + bottom) / (top - bottom);
        Zw = -(far + near) / (far - near);
    }

    /**
     * Sets the projection matrix values from a field of view, aspect ratio,
     * near clip plane, and far clip plane. This method internally modifies this
     * matrix and does not create any additional objects. A reference to this
     * matrix is returned for the purpose of call chaining.
     *
     * @param yFov y field of view.
     * @param aspectRatio screen aspect ratio.
     * @param near the near depth clipping plane.
     * @param far the far depth clipping plane.
     */
    public final void fromPerspective(final float yFov, final float aspectRatio, final float near, final float far) {
        float tan = 1f / FastMath.tan(yFov * FastMath.DEG_TO_RAD / 2f);
        Xx = tan / aspectRatio;
        Yy = tan;
        Zz = (far + near) / (near - far);
        Wz = -1f;
        Zw = (2f * far * near) / (near - far);
        Ww = 0f;
    }

    /**
     * Set the transformation matrix values from a translation, rotation, and
     * scale. This method internally modifies this matrix and does not create
     * any additional objects. A reference to this matrix is returned for the
     * purpose of call chaining.
     *
     * @param translation the matrix translation to set.
     * @param rotation the matrix rotation to set.
     * @param scale the matrix scale to set.
     * @return this matrix.
     */
    public final Matrix4f fromTransform(final Vec3f translation, final Quaternion rotation, final Vec3f scale) {
        setTranslation(translation);
        setRotation(rotation);
        setScale(scale);
        return this;
    }

    /**
     * Set the transformation matrix values from a transform using its
     * translation, rotation, and scale components. This method internally
     * modifies this matrix and does not create any additional objects. A
     * reference to this matrix is returned for the purpose of call chaining.
     *
     * @param toSet
     * @return this matrix.
     */
    public final Matrix4f fromTransform(final Transform toSet) {
        setTranslation(toSet.getTranslation());
        setRotation(toSet.getRotation());
        setScale(toSet.getScale());
        return this;
    }

    /**
     * Sets the matrix values from an array of floats. The array must not be
     * null and have a length of exactly 16. Transpose should be false if the
     * array is to be read in column-major order or true if the array should be
     * read in row-major order. This method internally modifies this matrix and
     * does not create any additional objects. A reference to this matrix is
     * returned for the purpose of call chaining.
     *
     * @param src the array that defines this matrix.
     * @param transpose true if the array is in row-major order.
     * @return this matrix.
     */
    public final Matrix4f fromArray(final float[] src, final boolean transpose) {
        if (src == null) {
            throw new IllegalArgumentException("Cannot set matrix from null source array.");
        } else if (src.length != 16) {
            throw new IllegalArgumentException("Cannot set matrix from array. Length of array must be exactly 16.");
        } else {
            if (transpose) {
                Xx = src[0];
                Xy = src[1];
                Xz = src[2];
                Xw = src[3];

                Yx = src[4];
                Yy = src[5];
                Yz = src[6];
                Yw = src[7];

                Zx = src[8];
                Zy = src[9];
                Zz = src[10];
                Zw = src[11];

                Wx = src[12];
                Wy = src[13];
                Ww = src[14];
                Wz = src[15];
            } else {
                Xx = src[0];
                Yx = src[1];
                Zx = src[2];
                Wx = src[3];

                Xy = src[4];
                Yy = src[5];
                Zy = src[6];
                Wy = src[7];

                Xz = src[8];
                Yz = src[9];
                Zz = src[10];
                Wz = src[11];

                Xw = src[12];
                Yw = src[13];
                Zw = src[14];
                Ww = src[15];
            }
        }
        return this;
    }

    /**
     * Creates a copy of this matrix and multiplies each value by negative one.
     * This matrix is not internally modified in any way by this method. A new
     * matrix object is created and returned each time this method is called.
     *
     * @return a negated copy of this matrix.
     */
    public Matrix4f negate() {
        return mult(-1f);
    }

    /**
     * Internally negates the values of this matrix by multiplying each value by
     * negative one. This method internally modifies this matrix and does not
     * create any additional objects. A reference to this matrix is returned for
     * the purpose of call chaining.
     *
     * @return this matrix.
     */
    public final Matrix4f negateLocal() {
        return multLocal(-1f);
    }

    /**
     * Sets the values of the matrix to equal an identity matrix.
     */
    public final void setIdentity() {
        Xy = Xz = Xw = Yx = Yz = Yw = Zx = Zy = Zw = Wx = Wy = Wz = 0f;
        Xx = Yy = Zz = Ww = 1f;
    }

    /**
     * Sets all matrix values to zero.
     */
    public final void clear() {
        Xx = Xy = Xz = Xw = 0f;
        Yx = Yy = Yz = Yw = 0f;
        Zx = Zy = Zz = Zw = 0f;
        Wx = Wy = Wz = Ww = 0f;
    }

    /**
     * Returns the matrix data as a float array in column-major order. The
     * returned array will always have a length of 16. This method does not
     * internally modify this matrix in any way and creates a new float array
     * object each time it is called.
     *
     * @param transpose true to return the data in row-major order.
     * @return a float array containing the matrix data.
     */
    public final float[] asArray(final boolean transpose) {
        if (transpose) {
            return new float[]{Xx, Xy, Xz, Xw, Yx, Yy, Yz, Yw, Zx, Zy, Zz, Zw, Wx, Wy, Wz, Ww};
        } else {
            return new float[]{Xx, Yx, Zx, Wx, Xy, Yy, Zy, Wy, Xz, Yz, Zz, Wz, Xw, Yw, Zw, Ww};
        }
    }
}
