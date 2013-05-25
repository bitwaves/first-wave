package ch.bitwave.mim.m2.core;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * An enumeration defines a set of literals that can be used as its values.
 * </p>
 * 
 * <p>
 * An enumeration defines a finite ordered set of values, such as {red, green,
 * blue}. The values denoted by typed elements whose type is an enumeration must
 * be taken from this set.
 * </p>
 */
public class Enumeration extends NamedElement implements DataType {

	private Namespace owner;
	private List<EnumerationLiteral> ownedLiterals;

	public Enumeration() {
		this.ownedLiterals = new ArrayList<EnumerationLiteral>();
	}

	public List<EnumerationLiteral> getOwnedLiterals() {
		return this.ownedLiterals;
	}

	public void setOwner(final Namespace owner) {
		this.owner = owner;
	}

	@Override
	public boolean conformsTo(final MimType other) {
		return false;
	}

	@Override
	public MimElement getOwner() {
		return this.owner;
	}

	@Override
	public MimPackage getPackage() {
		return getOwnerAs(MimPackage.class);
	}

	@Override
	public void setPackage(final MimPackage owner) {
		this.setOwner(owner);
	}

}
