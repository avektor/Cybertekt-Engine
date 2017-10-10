package net.cybertekt.math;

/**
 * Transform - (C) Cybertekt Software.
 *
 * Defines a translation (position), rotation (orientation), and scale (size).
 * Methods are provided for modifying and combining transforms.
 *
 * @version 1.0.0
 * @since 1.0.0
 * @author Andrew Vektor
 */
public final class Transform {

    /**
     * Translation (position) component.
     */
    private Vec3f translation = new Vec3f(0f, 0f, 0f);

    /**
     * Rotation (orientation) component.
     */
    private Quaternion rotation = new Quaternion();

    /**
     * Scale (size) component.
     */
    private Vec3f scale = new Vec3f(0f, 0f, 0f);

    /**
     * Constructs a transform with a default translation, rotation, and scale.
     * Default translation is a zero vector, default rotation is an identity
     * quaternion, and default scale is one.
     */
    public Transform() {
        this(new Vec3f(0f, 0f, 0f), new Quaternion(), new Vec3f(1f, 1f, 1f));
    }

    /**
     * Constructs a quaternion defined by copying the values from another
     * quaternion. The provided quaternion is not internally modified in any
     * way. Passing null arguments into this constructor is not permitted.
     *
     * @param toCopy the transform from which to copy.
     * @throws NullPointerException if the argument passed into this constructor
     * is null.
     */
    public Transform(final Transform toCopy) {
        this(new Vec3f(toCopy.getTranslation()), new Quaternion(toCopy.getRotation()), new Vec3f(toCopy.getScale()));
    }

    public Transform(final Vec3f translation, final Quaternion rotation, final Vec3f scale) {
        this(translation, scale, rotation);
    }

    public Transform(final Vec3f translation, final Vec3f scale, final Quaternion rotation) {
        setTranslation(translation);
        setScale(scale);
        setRotation(rotation);
    }

    /**
     * Generates a transform equal to the product of this transform multiplied
     * by the parameter transform.
     *
     * @param toMult the transform to multiply by.
     * @return
     */
    public final Transform mult(final Transform toMult) {
        return new Transform(translation.mult(toMult.getTranslation()), scale.mult(toMult.getScale()), rotation.mult(toMult.getRotation()));
    }

    /**
     * Internally multiplies the components of this transform by the parameter
     * transform. This method modifies the internal fields of this transform.
     * This method does not make any internal changes to the parameter
     * transform.<br /><br />
     * Mathematically equivalent to:
     * <i>This Translation * Other Translation, This Scale * Other Scale, This
     * Rotation * Other Rotation.</i>
     *
     * @param toMult the transform to multiply this transform by.
     * @return this transform.
     */
    public final Transform multLocal(final Transform toMult) {
        translation.multLocal(toMult.getTranslation());
        scale.multLocal(toMult.getScale());
        rotation.multLocal(toMult.getRotation());
        return this;
    }

    public final Transform combine(final Transform parent) {
        scale.multLocal(parent.scale);
        parent.rotation.mult(rotation, rotation);
        translation.multLocal(parent.scale);
        parent.rotation.multLocal(translation).addLocal(parent.translation);
        return this;
    }

    /**
     * Sets the translation component of this transform.
     *
     * @param toSet the translation to set.
     */
    public final void setTranslation(final Vec3f toSet) {
        translation = toSet;
    }

    /**
     * Sets the translation component of this transform.
     *
     * @param x the x-axis translation.
     * @param y the y-axis translation.
     * @param z the z-axis translation.
     */
    public final void setTranslation(final float x, final float y, final float z) {
        translation.set(x, y, z);
    }

    /**
     * Returns the translation component of this transform.
     *
     * @return the translation component of this transform.
     */
    public final Vec3f getTranslation() {
        return translation;
    }

    /**
     * Generates a translation matrix from the translation component of this
     * transform.
     *
     * @return the translation generated from the translation component of this
     * transform.
     */
    public final Matrix4f getTranslationMatrix() {
        return Matrix4f.translation(translation);
    }

    /**
     * Sets the rotation component of this transform.
     *
     * @param toSet the rotation component of this transform.
     */
    public final void setRotation(final Quaternion toSet) {
        rotation = toSet;
    }

    /**
     * Returns the rotation component of this transform.
     *
     * @return the rotation rotation component of this transform.
     */
    public final Quaternion getRotation() {
        return rotation;
    }

    /**
     * Generates a matrix based on the rotation component of this transform.
     *
     * @return the rotation matrix generated by the rotation component of this
     * transform.
     */
    public final Matrix4f getRotationMatrix() {
        return Matrix4f.rotation(rotation);
    }

    /**
     * Sets the vector that defines the scale component of this transform.
     *
     * @param toSet the scale vector to set.
     */
    public final void setScale(final Vec3f toSet) {
        scale = toSet;
    }

    /**
     * Sets the vector that defines the scale component of this transform.
     *
     * @param x the x-axis scale to set.
     * @param y the y-axis scale to set.
     * @param z the z-axis scale to set.
     */
    public final void setScale(final float x, final float y, final float z) {
        scale.set(x, y, z);
    }

    /**
     * Sets the scale component of this transform.
     *
     * @param scalar the scale component of this transform.
     */
    public final void setScale(final float scalar) {
        setScale(scalar, scalar, scalar);
    }

    /**
     * Returns the scale component of this transform.
     *
     * @return the scale component of this transform.
     */
    public final Vec3f getScale() {
        return scale;
    }

    /**
     * Generates a scalar matrix from the scale component of this transform.
     *
     * @return the matrix generated from the scale component of this transform.
     */
    public final Matrix4f getScaleMatrix() {
        return Matrix4f.scalar(scale);
    }

    /**
     * Generates a transformation matrix from the combined translation,
     * rotation, and scale components of this transform.
     * <br /><br />
     * Mathematically equivalent to:
     * <i>Translation Matrix * Rotation Matrix * Scale Matrix</i>
     *
     * @return the matrix generated from the translation, rotation, and scale of
     * components of this transform.
     */
    public final Matrix4f getTransformMatrix() {
        return Matrix4f.transform(translation, rotation, scale);
    }

}
