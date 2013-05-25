package ch.bitwave.mim.m2.core;


/**
 * A named element represents elements that may have a name. The name is used
 * for identification of the named element within the namespace in which it is
 * defined. A named element also has a qualified name that allows it to be
 * unambiguously identified within a hierarchy of nested namespaces.
 * NamedElement is an abstract metaclass.
 * 
 * The name for a named element is optional. If specified, then any valid
 * string, including the empty string, may be used.
 */
public abstract class NamedElement extends MimElement {
	private String name;
	private VisibilityKind visibility;

	public VisibilityKind getVisibility() {
		return this.visibility;
	}

	public void setVisibility(final VisibilityKind visibility) {
		this.visibility = visibility;
	}

	public String getName() {
		return this.name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public Namespace getNamespace() {
		MimElement owner = getOwner();
		if (owner instanceof Namespace)
			return (Namespace) owner;
		return null;
	}

	/**
	 * A name which allows the NamedElement to be identified within a hierarchy
	 * of nested Namespaces. It is constructed from the names of the containing
	 * namespaces starting at the root of the hierarchy and ending with the name
	 * of the NamedElement itself. This is a derived attribute.
	 * 
	 * @return the qualified name.
	 */
	public String getQualifiedName() {
		Namespace ns = getNamespace();
		if (ns == null) {
			return this.name;
		}
		return ns.getQualifiedName() + '.' + this.name;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(getClass().getSimpleName()).append(" ").append(getQualifiedName());
		return builder.toString();
	}
}
