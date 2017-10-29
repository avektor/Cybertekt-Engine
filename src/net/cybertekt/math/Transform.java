package net.cybertekt.math;

import org.joml.Matrix3f;
import org.joml.Matrix4f;
import org.joml.Quaternionf;
import org.joml.Vector3f;

/**
 * Transform - (C) Cybertekt Software.
 *
 * Defines a translation (position), rotation (orientation), and scale (size).
 * Methods are provided for combining and perform mathematical operations on
 * transforms.
 *
 * @version 1.0.0
 * @since 1.0.0
 * @author Andrew Vektor
 */
public final class Transform {

    /**
     * Location (position) component.
     */
    private Vector3f translation = new Vector3f().zero();

    /**
     * Rotation (orientation) component.
     */
    private Quaternionf rotation = new Quaternionf().identity();

    /**
     * Scale (size) component.
     */
    private Vector3f scale = new Vector3f(1f, 1f, 1f);

    /**
     * Location (position) matrix defined as an instance variable to prevent the
     * creation of multiple translation matrix objects.
     */
    private final Matrix4f locationMatrix = new Matrix4f();

    /**
     * Rotation (orientation) matrix defined as an instance variable to prevent
     * the creation of multiple rotation matrix objects.
     */
    private final Matrix4f rotationMatrix = new Matrix4f();

    /**
     * Scale (size) matrix defined as an instance variable to prevent the
     * creation of multiple scale matrix objects.
     */
    private final Matrix4f scaleMatrix = new Matrix4f();

    /**
     * Matrix defined by the combination of the translation, rotation, and scale
     * matrices. Defined as an instance variable to prevent the creation of
     * multiple transform matrix objects.
     */
    private final Matrix4f transformMatrix = new Matrix4f();

    /**
     * Constructs a transform with a default translation, rotation, and scale.
     * Default translation is a zero vector, default rotation is an identity
     * quaternion, and default scale is one.
     */
    public Transform() {
    }

    /**
     * Constructs a new transform by providing a translation component and the
     * default rotation and scale components. The default rotation is an
     * identity quaternion and the default scale is one.
     *
     * @param translation the translation (location) component of this
     * transform.
     */
    public Transform(final Vector3f translation) {
        this.translation.set(translation);
    }

    /**
     * Constructs a new transform with the provided translation and rotation
     * components and the default scale component. The default scale is one.
     *
     * @param translation the translation (location) component of this
     * transform.
     * @param rotation the rotation (orientation) component of this transform.
     */
    public Transform(final Vector3f translation, final Quaternionf rotation) {
        this.translation.set(translation);
        this.rotation.set(rotation);
    }

    /**
     * Constructs a new transform with the provided translation, rotation, and
     * scale components.
     *
     * @param location the translation (location) component of this transform.
     * @param rotation the rotation (orientation) component of this transform.
     * @param scale the scale (size) component of this transform.
     */
    public Transform(final Vector3f location, final Quaternionf rotation, final Vector3f scale) {
        this.translation = location;
        this.rotation = rotation;
        this.scale = scale;
    }

    /**
     * Constructs a new transform defined by copying the values from another
     * transform. The parameter transform is not modified in any way. Passing a
     * null argument into this construction will cause a NullPointerException.
     *
     * @param toCopy the transform from which to copy.
     * @throws NullPointerException if the argument passed into this constructor
     * is null.
     */
    public Transform(final Transform toCopy) {
        this.translation.set(toCopy.getTranslation());
        this.rotation.set(toCopy.getRotation());
        this.scale.set(toCopy.getScale());
    }

    /**
     * Sets the translation, rotation, and scale of this transform equal to the
     * translation, rotation, and scale of the parameter transform. This
     * transform is internally modified by this operation. The parameter
     * transform is not modified in any way by this operation.
     *
     * @param toSet
     * @return
     */
    public final Transform set(final Transform toSet) {
        translation.set(toSet.getTranslation());
        rotation.set(toSet.getRotation());
        scale.set(toSet.getScale());
        return this;
    }

    /**
     * Combines the translation, rotation, and scale of this transform and a
     * parent transform. Used to combine the transforms of two spatial objects
     * with a parent/child relationship. This transform is internally modified
     * by this operation. The parameter transform is not modified in any way by
     * this operation.
     *
     * @param parent the parent transform to combine with this transform.
     * @return a reference to this transform for the purpose of call chaining.
     */
    public final Transform combine(final Transform parent) {

        /* Apply Parent Scale To Local Scale */
        scale.mul(parent.getScale());

        /* Apply Parent Rotation To Local Rotation */
        parent.getRotation().mul(rotation, rotation);

        /* Apply Parent Scale To Local Location */
        translation.mul(parent.getScale());

        /* Apply Parent Rotation to Local Location */
        translation.mul(parent.getRotation().get(new Matrix3f()));

        /* Apply Parent Location to Local Location */
        translation.add(parent.getTranslation());

        return this;
    }

    /**
     * Generates a transform equal to the sum of this transform and the
     * parameter transform. Neither this transform or the parameter transform
     * are modified by this operation. Calling this method with a null argument
     * is not permitted and will result in a null pointer exception being
     * thrown.
     *
     * @param toAdd the transform to add to this transform.
     * @return the new transform created by adding the parameter transform to
     * this transform.
     */
    public final Transform add(final Transform toAdd) {
        return new Transform(new Vector3f(translation).add(toAdd.getTranslation()), new Quaternionf(rotation).add(toAdd.getRotation()), new Vector3f(scale).add(toAdd.getScale()));
    }

    /**
     * Internally adds the translation, rotation, and scale components of the
     * parameter transform to the translation, rotation, and scale components of
     * this transform. This transform is internally modified by this operation.
     * The provided parameter transform is not modified in any way by this
     * operation. Calling this method with a null argument is not permitted and
     * will result in a null pointer exception being thrown.
     *
     * @param toAdd the transform to add to this transform.
     * @return this transform for the purpose of call chaining.
     */
    public final Transform addLocal(final Transform toAdd) {
        translation.add(toAdd.getTranslation());
        scale.add(toAdd.getScale());
        rotation.add(toAdd.getRotation());
        return this;
    }

    /**
     * Generates a transform equal to the product of this transform multiplied
     * by the parameter transform. Neither this transform nor the parameter
     * transform are modified by this operation. Passing a null argument into
     * this method is not permitted and will result in a null pointer exception
     * being thrown.
     *
     * @param toMult the transform by which to multiply this transform.
     * @return the new transform which is equal to the product of this transform
     * multiplied by the parameter transform.
     */
    public final Transform mult(final Transform toMult) {
        return new Transform(new Vector3f(translation).mul(toMult.getTranslation()), new Quaternionf(rotation).mul(toMult.getRotation()), new Vector3f(scale).mul(toMult.getScale()));
    }

    /**
     * Internally multiplies the components of this transform by the parameter
     * transform. This method modifies the internal fields of this transform and
     * does not make any internal changes to the parameter transform. Passing a
     * null argument into this method is not permitted and will result in a null
     * pointer exception being thrown.
     *
     * Mathematically equivalent to:
     * <i>This Translation * Other Translation, This Scale * Other Scale, This
     * Rotation * Other Rotation.</i>
     *
     * @param toMult the transform by which to multiply this transform.
     * @return this transform for the purpose of call chaining.
     */
    public final Transform multLocal(final Transform toMult) {
        translation.mul(toMult.getTranslation());
        scale.mul(toMult.getScale());
        rotation.mul(toMult.getRotation());
        return this;
    }

    /**
     * Sets the translation component of this transform.
     *
     * @param toSet the translation to set.
     */
    public final void setTranslation(final Vector3f toSet) {
        translation.set(toSet);
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
    public final Vector3f getTranslation() {
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
        return locationMatrix.translation(translation);
    }

    /**
     * Sets the rotation component of this transform.
     *
     * @param toSet the rotation component of this transform.
     */
    public final void setRotation(final Quaternionf toSet) {
        rotation.set(toSet);
    }

    /**
     * Returns the rotation component of this transform.
     *
     * @return the rotation rotation component of this transform.
     */
    public final Quaternionf getRotation() {
        return rotation;
    }

    /**
     * Generates a matrix based on the rotation component of this transform.
     *
     * @return the rotation matrix generated by the rotation component of this
     * transform.
     */
    public final Matrix4f getRotationMatrix() {
        return rotationMatrix.rotation(rotation);
    }

    /**
     * Sets the vector that defines the scale component of this transform.
     *
     * @param toSet the scale vector to set.
     */
    public final void setScale(final Vector3f toSet) {
        scale.set(toSet);
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
    public final Vector3f getScale() {
        return scale;
    }

    /**
     * Generates a scalar matrix from the scale component of this transform.
     *
     * @return the matrix generated from the scale component of this transform.
     */
    public final Matrix4f getScaleMatrix() {
        return scaleMatrix.scaling(scale);
    }

    /**
     * Generates a transformation matrix from the combined translation,
     * rotation, and scale components of this transform.
     *
     * Mathematically equivalent to:
     * <i>Translation Matrix * Rotation Matrix * Scale Matrix</i>
     *
     * @return the matrix defined by multiplying the translation, rotation, and
     * scale of components of this transform.
     */
    public final Matrix4f getTransformMatrix() {
        return transformMatrix.translation(translation).mul(getRotationMatrix()).mul(getScaleMatrix());
    }

}
