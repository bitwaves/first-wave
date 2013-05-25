package ch.bitwave.mim.m2.core;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * A package import is a relationship that allows the use of unqualified names
 * to refer to package members from other namespaces.
 * </p>
 * 
 * <p>
 * A package import is a relationship between an importing namespace and a
 * package, indicating that the importing namespace adds the names of the
 * members of the package to its own namespace. Conceptually, a package import
 * is equivalent to having an element import to each individual member of the
 * imported namespace, unless there is already a separately-defined element
 * import.
 * </p>
 */
public class PackageImport extends MimElement implements DirectedRelationship {
	private MimPackage importedPackage;

	/**
	 * Specifies the Package whose members are imported into a Namespace.
	 * Subsets {@link DirectedRelationship#getTargetElements()}.
	 * 
	 * @return
	 */
	public MimPackage getImportedPackage() {
		return this.importedPackage;
	}

	public void setImportedPackage(final MimPackage importedPackage) {
		this.importedPackage = importedPackage;
	}

	/**
	 * Specifies the Namespace that imports the members from a Package. Subsets
	 * {@link DirectedRelationship#getSourceElements()} and Element::owner.
	 * 
	 * @return
	 */
	public Namespace getImportingNamespace() {
		return this.getOwnerAs(Namespace.class);
	}

	public void setImportingNamespace(final Namespace importingNamespace) {
		this.setOwner(importingNamespace);
	}

	@Override
	public List<MimElement> getRelatedElements() {
		List<MimElement> result = new ArrayList<MimElement>();
		result.add(getImportingNamespace());
		result.add(getImportedPackage());
		return result;
	}

	@Override
	public List<MimElement> getSourceElements() {
		List<MimElement> result = new ArrayList<MimElement>();
		result.add(getImportingNamespace());
		return result;
	}

	@Override
	public List<MimElement> getTargetElements() {
		List<MimElement> result = new ArrayList<MimElement>();
		result.add(getImportedPackage());
		return result;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("PackageImport [importingNamespace=").append(getImportingNamespace())
				.append(", importedPackage=").append(getImportedPackage()).append("]");
		return builder.toString();
	}
}
