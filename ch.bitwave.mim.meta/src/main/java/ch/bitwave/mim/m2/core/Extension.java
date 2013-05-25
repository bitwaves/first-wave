package ch.bitwave.mim.m2.core;

/**
 * <p>
 * An extension is used to indicate that the properties of a metaclass are
 * extended through a stereotype, and gives the ability to flexibly add (and
 * later remove) stereotypes to classes.
 * </p>
 * 
 * <p>
 * Extension is a kind of Association. One end of the Extension is an ordinary
 * Property and the other end is an ExtensionEnd. The former ties the Extension
 * to a Class, while the latter ties the Extension to a Stereotype that extends
 * the Class.
 * </p>
 */
public class Extension extends Association {

	public MimClass getMetaClass() {
		return (MimClass) getFirstNonOwnedEnd().getType();
	}

	private Property getFirstNonOwnedEnd() {
		for (Property prop : getMemberEnds()) {
			if (!prop.isOwnedBy(this)) {
				return prop;
			}
		}
		throw new MetaModelRuntimeException(String.format(
				"%s does not contain a non-owned member end.", getName()));
	}

}
