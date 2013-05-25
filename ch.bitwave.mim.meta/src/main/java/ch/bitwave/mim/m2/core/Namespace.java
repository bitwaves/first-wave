package ch.bitwave.mim.m2.core;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nonnull;

/**
 * A namespace is a named element that can own other named elements. Each named
 * element may be owned by at most one namespace. A namespace provides a means
 * for identifying named elements by name. Named elements can be identified by
 * name in a namespace either by being directly owned by the namespace or by
 * being introduced into the namespace by other means (e.g., importing or
 * inheriting). Namespace is an abstract metaclass.
 */
public abstract class Namespace extends NamedElement {

	private List<PackageImport> packageImports;
	private List<PackageImport> importingPackages;
	private NamingStrategy namingStrategy;

	public Namespace() {
		super();
		this.packageImports = new ArrayList<PackageImport>();
		this.importingPackages = new ArrayList<PackageImport>();
		this.namingStrategy = ExactNamingStrategy.getInstance();
	}

	public NamingStrategy getNamingStrategy() {
		return this.namingStrategy;
	}

	public void setNamingStrategy(final NamingStrategy namingStrategy) {
		this.namingStrategy = namingStrategy;
	}

	public List<PackageImport> getPackageImports() {
		return this.packageImports;
	}

	public List<PackageImport> getImportingPackages() {
		return this.importingPackages;
	}

	public void addPackageImport(@Nonnull final PackageImport imp) {
		this.packageImports.add(imp);
	}

	public void removePackageImport(@Nonnull final PackageImport imp) {
		this.packageImports.remove(imp);
	}

	public void addImportingPackage(@Nonnull final PackageImport imp) {
		this.importingPackages.add(imp);
	}

	public void removeImportingPackage(@Nonnull final PackageImport imp) {
		this.importingPackages.remove(imp);
	}

	/**
	 * Returns the elements directly owned by this namespace.
	 * 
	 * @return the list of owned elements.
	 */
	public List<NamedElement> getOwnedMembers() {
		List<NamedElement> result = new ArrayList<NamedElement>();
		contributeOwnedMembers(result);
		return result;
	}

	protected abstract void contributeOwnedMembers(final List<NamedElement> list);

	/**
	 * A collection of NamedElements identifiable within the Namespace, either
	 * by being owned or by being introduced by importing or inheritance. This
	 * is a derived union.
	 * 
	 * @return the complete list of members contained in this namespace.
	 */
	public List<NamedElement> getMembers() {
		List<NamedElement> result = new ArrayList<NamedElement>();
		contributeMembers(result);
		return result;
	}

	protected List<NamedElement> getAllMembers() {
		List<NamedElement> result = new ArrayList<NamedElement>();
		contributeAllMembers(result);
		return result;
	}

	public List<MimPackage> getImportedPackages() {
		List<MimPackage> result = new ArrayList<MimPackage>();
		for (PackageImport imp : this.packageImports) {
			if (imp.getImportedPackage() == null) {
				throw new MetaModelRuntimeException(
						"Invalid package import: Imported package is null.");
			}
			result.add(imp.getImportedPackage());
		}
		return result;
	}

	protected void contributeMembers(final List<NamedElement> list) {
		list.addAll(getOwnedMembers());
		for (MimPackage importedPackage : getImportedPackages()) {
			list.addAll(importedPackage.getOwnedMembers());
		}
	}

	protected void contributeAllMembers(final List<NamedElement> list) {
		contributeMembers(list);
	}

	public NamedElement getMemberByName(final String name) throws UnknownMemberException {
		NamedElement member = findMember(name);
		if (member == null) {
			throw new UnknownMemberException(String.format(
					"Element \"%s\" does not have a member element with name \"%s\".", getName(),
					name));
		}
		return member;
	}

	public <T extends NamedElement> T getMemberOfTypeByName(final java.lang.Class<T> type,
			final String name) throws UnknownMemberException {
		T member = findMemberOfType(type, name);
		if (member == null) {
			throw new UnknownMemberException(String.format(
					"Element \"%s\" does not have a member element with name \"%s\".", getName(),
					name));
		}
		return member;
	}

	public NamedElement getOwnedMemberByName(final String name) throws UnknownMemberException {
		NamedElement member = findOwnedMember(name);
		if (member == null) {
			throw new UnknownMemberException(String.format(
					"Element \"%s\" does not own an element with name \"%s\".", getName(), name));
		}
		return member;
	}

	public NamedElement findMember(final String name) {
		return findByName(getMembers(), name);
	}

	public <T extends NamedElement> T findMemberOfType(final java.lang.Class<T> type,
			final String name) {
		List<T> elements = getMembersOfType(type);
		return findByName(elements, name);
	}

	public <T extends NamedElement> List<T> getMembersOfType(final java.lang.Class<T> type) {
		@SuppressWarnings("unchecked")
		List<T> elements = (List<T>) getMembers();
		return filterByElementType(elements, type);
	}

	public <T extends NamedElement> List<T> getAllMembersOfType(final java.lang.Class<T> type) {
		@SuppressWarnings("unchecked")
		List<T> elements = (List<T>) getAllMembers();
		return filterByElementType(elements, type);
	}

	protected <T extends NamedElement> T findByName(final List<T> elements, final String name) {
		for (T element : elements) {
			if (this.namingStrategy.isSameName(name, element.getName())) {
				return element;
			}
		}
		return null;
	}

	public NamedElement findOwnedMember(final String name) {
		for (NamedElement element : getOwnedMembers()) {
			if (this.namingStrategy.isSameName(name, element.getName())) {
				return element;
			}
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	public <T> T findOwnedMemberAs(final String name, final java.lang.Class<T> elementType) {
		NamedElement detailMember = findOwnedMember(name);
		if (detailMember != null) {
			if (!elementType.isAssignableFrom(detailMember.getClass())) {
				throw new RuntimeException(String.format(
						"Element \"%s\" contained in \"%s\" is not of type %s.", name, getName(),
						elementType.getClass().getSimpleName()));
			}
			return (T) detailMember;
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	public <T extends NamedElement> List<T> getOwnedMembersOfType(
			final java.lang.Class<T> memberClass) {
		List<T> members = (List<T>) getOwnedMembers();
		return filterByElementType(members, memberClass);
	}

	@SuppressWarnings("unchecked")
	protected <T extends NamedElement> List<T> filterByElementType(final List<T> elements,
			final java.lang.Class<T> memberClass) {
		List<T> result = new ArrayList<T>();
		for (NamedElement element : elements) {
			if (memberClass.isAssignableFrom(element.getClass())) {
				result.add((T) element);
			}
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	protected <T extends MimType> List<T> filterByType(final List<NamedElement> elements,
			final java.lang.Class<T> typeClass) {
		List<T> result = new ArrayList<T>();
		for (NamedElement element : elements) {
			if (typeClass.isAssignableFrom(element.getClass())) {
				result.add((T) element);
			}
		}
		return result;
	}

	public abstract void addOwnedMember(NamedElement member);

	public abstract void removeOwnedMember(NamedElement member);

	public MimType findType(final String typeName) {
		NamedElement elem = findMember(typeName);
		if (elem instanceof MimType) {
			return (MimType) elem;
		}
		return null;
	}
}
