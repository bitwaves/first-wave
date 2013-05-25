package ch.bitwave.mim.m2.core;

import javax.annotation.CheckForNull;

/**
 * <p>
 * A property represents an attribute of a class. A property has a type and a
 * multiplicity. When a property is paired with an opposite they represent two
 * mutually constrained attributes. The semantics of two properties that are
 * mutual opposites are the same as for bidirectionally navigable associations
 * in Constructs, with the exception that the association has no explicit links
 * as instances, and has no name.
 * </p>
 */
public class Property extends StructuralFeature implements TypedElement, MultiplicityElement {

	private Relationship owningRelationship;
	private Relationship relationship;
	private Multiplicity multiplicity;
	private String defaultValue;
	private boolean composite;
	private boolean derived;
	private boolean readOnly;
	private Property opposite;
	private MimType type;

	public Property() {
		this.multiplicity = new Multiplicity();
	}

	/**
	 * References the owning relationship of this property, if any.
	 * 
	 * @return
	 */
	public Relationship getOwningRelationship() {
		return this.owningRelationship;
	}

	/**
	 * References the Class that owns the Property. Subsets
	 * {@link NamedElement#getNamespace()},
	 * {@link Feature#getFeaturingClassifier()}.
	 * 
	 * @return
	 */
	public MimClass getOwningClass() {
		MimElement owner = this.getOwner();
		if (owner == null)
			return null;
		if (!(owner instanceof MimClass))
			throw new MetaModelRuntimeException(String.format("%s is not owned by a class.",
					toString()));
		return (MimClass) owner;
	}

	public void setOwningClass(final MimClass owningClass) {
		this.setOwner(owningClass);
	}

	/**
	 * References the relationship of which this property is a member, if any.
	 * 
	 * @return
	 */
	@CheckForNull
	public Relationship getRelationship() {
		return this.relationship;
	}

	public void setAssociation(final Association association) {
		this.relationship = association;
	}

	/**
	 * Two attributes attr1 and attr2 of two objects o1 and o2 (which may be the
	 * same object) may be paired with each other so that o1.attr1 refers to o2
	 * if and only if o2.attr2 refers to o1. In such a case attr1 is the
	 * opposite of attr2 and attr2 is the opposite of attr1.
	 * 
	 * @return
	 */
	public Property getOpposite() {
		return this.opposite;
	}

	public void setOpposite(final Property opposite) {
		this.opposite = opposite;
	}

	/**
	 * If isReadOnly is true, the attribute may not be written to after
	 * initialization. The default value is false.
	 * 
	 * @return
	 */
	public boolean isReadOnly() {
		return this.readOnly;
	}

	public void setReadOnly(final boolean readOnly) {
		this.readOnly = readOnly;
	}

	/**
	 * If isComposite is true, the object containing the attribute is a
	 * container for the object or value contained in the attribute. The default
	 * value is false.
	 * 
	 * @return
	 */
	public boolean isComposite() {
		return this.composite;
	}

	public void setComposite(final boolean composite) {
		this.composite = composite;
	}

	/**
	 * If isDerived is true, the value of the attribute is derived from
	 * information elsewhere. The default value is false.
	 * 
	 * @return
	 */
	public boolean isDerived() {
		return this.derived;
	}

	public void setDerived(final boolean derived) {
		this.derived = derived;
	}

	/**
	 * A string that is evaluated to give a default value for the attribute when
	 * an object of the owning class is instantiated.
	 * 
	 * @return
	 */
	public String getDefaultValue() {
		return this.defaultValue;
	}

	public void setDefaultValue(final String defaultValue) {
		this.defaultValue = defaultValue;
	}

	@Override
	public Multiplicity getMultiplicity() {
		return this.multiplicity;
	}

	@Override
	public MimType getType() {
		return this.type;
	}

	@Override
	public void setType(final MimType type) {
		this.type = type;
	}

	public boolean isOwnedBy(final Classifier classifier) {
		return getOwner() == classifier;
	}

	public void setRelationship(final Relationship relationship) {
		this.relationship = relationship;
	}

}
