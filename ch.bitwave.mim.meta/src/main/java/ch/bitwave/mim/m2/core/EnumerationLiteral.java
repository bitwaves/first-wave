package ch.bitwave.mim.m2.core;

/**
 * An enumeration literal is a value of an enumeration.
 */
public class EnumerationLiteral extends NamedElement {

	private Enumeration enumeration;

	public Enumeration getEnumeration() {
		return this.enumeration;
	}

	public void setEnumeration(final Enumeration enumeration) {
		this.enumeration = enumeration;
	}

	@Override
	public MimElement getOwner() {
		return this.enumeration;
	}

}
