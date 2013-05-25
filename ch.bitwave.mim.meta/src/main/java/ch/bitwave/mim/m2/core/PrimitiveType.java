package ch.bitwave.mim.m2.core;

/**
 * A primitive type is a data type implemented by the underlying infrastructure
 * and made available for modeling.
 */
public class PrimitiveType extends NamedElement implements DataType {

	@Override
	public boolean conformsTo(final MimType other) {
		return this == other;
	}

	@Override
	public MimPackage getPackage() {
		return getOwnerAs(MimPackage.class);
	}

	@Override
	public void setPackage(final MimPackage owner) {
		setOwner(owner);
	}
}
