package ch.bitwave.mim.m2.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;

import javax.annotation.CheckForNull;

/**
 * Packages provide a way of grouping types and packages together, which can be
 * useful for understanding and managing a model. A package cannot contain
 * itself.
 */
public class MimPackage extends Namespace {

	private static final Logger LOGGER = Logger.getLogger(MimPackage.class.getName());
	private List<NamedElement> ownedTypes;

	public MimPackage() {
		this.ownedTypes = new ArrayList<NamedElement>();
	}

	public List<MimType> getOwnedTypes() {
		List<MimType> result = new ArrayList<MimType>();
		for (NamedElement element : getOwnedMembers()) {
			if (element instanceof MimType) {
				result.add((MimType) element);
			}
		}
		return result;
	}

	public List<MimType> getOwnedStereotypes() {
		List<MimType> result = new ArrayList<MimType>();
		for (NamedElement element : getOwnedMembers()) {
			if (element instanceof Stereotype) {
				result.add((Stereotype) element);
			}
		}
		return result;
	}

	public List<MimPackage> getNestedPackages() {
		List<MimPackage> result = new ArrayList<MimPackage>();
		for (NamedElement element : getOwnedMembers()) {
			if (element instanceof MimPackage) {
				result.add((MimPackage) element);
			}
		}
		return result;
	}

	public MimPackage getNestingPackage() {
		return getOwnerAs(MimPackage.class);
	}

	@Override
	protected void contributeOwnedMembers(final List<NamedElement> list) {
		list.addAll(this.ownedTypes);
	}

	@Override
	protected void contributeOwnedElements(final List<MimElement> result) {
		result.addAll(this.ownedTypes);
	}

	@Override
	protected void contributeAllMembers(final List<NamedElement> list) {
		super.contributeAllMembers(list);
		for (MimPackage nested : getNestedPackages()) {
			nested.contributeAllMembers(list);
		}
	}

	@Override
	public void addOwnedMember(final NamedElement member) {
		this.ownedTypes.add(member);
		member.setOwner(this);
	}

	@Override
	public void removeOwnedMember(final NamedElement member) {
		this.ownedTypes.remove(member);
		if (member.getOwner() == this) {
			member.setOwner(null);
		}
	}

	/**
	 * Adds a package import for the given package name, which makes the names
	 * of that package available within this package.
	 * 
	 * @param name
	 */
	public void addPackageImportStub(final String name) {
		PackageImport imp = new PackageImport();
		imp.setImportingNamespace(this);
		StubPackage target = new StubPackage(imp);
		target.setName(name);
		imp.setImportedPackage(target);
		addPackageImport(imp);
		target.addImportingPackage(imp);
	}

	/**
	 * Locates any package nested in this package by name.
	 * 
	 * @param name
	 *            the package name.
	 * @return a reference to the package with this name, or null.
	 */
	@CheckForNull
	public MimPackage findContainedPackage(final String name) {
		if (name.equalsIgnoreCase(getName())) {
			return this;
		}
		for (MimPackage detail : getOwnedMembersOfType(MimPackage.class)) {
			MimPackage found = detail.findContainedPackage(name);
			if (found != null) {
				return found;
			}
		}
		return null;
	}

	protected void collectOwnedClasses(final Set<MimClass> elementSet) {
		{
			List<MimClass> elements = getOwnedMembersOfType(MimClass.class);
			for (MimClass mimClass : elements) {
				elementSet.add(mimClass);
			}
		}
		for (MimPackage detail : getNestedPackages()) {
			detail.collectOwnedClasses(elementSet);
		}
	}

	public Set<MimClass> getAllOwnedClasses() {
		Set<MimClass> result = new LinkedHashSet<MimClass>();
		collectOwnedClasses(result);
		return result;
	}

	protected void collectPackages(final List<MimPackage> elementSet) {
		{
			List<MimPackage> elements = getOwnedMembersOfType(MimPackage.class);
			for (MimPackage mimClass : elements) {
				elementSet.add(mimClass);
			}
		}
		for (MimPackage detail : getNestedPackages()) {
			detail.collectPackages(elementSet);
		}
	}

	public List<MimPackage> getAllOwnedPackages() {
		List<MimPackage> result = new ArrayList<MimPackage>();
		collectPackages(result);
		return result;
	}

	public Map<String, MimPackage> getAllOwnedNonStubPackages(final boolean lowercaseKeys) {
		List<MimPackage> allPackages = getAllOwnedPackages();
		return removeStubs(allPackages, lowercaseKeys);
	}

	public Map<String, MimClass> getAllNonStubClasses(final boolean lowercaseKeys) {
		List<MimClass> allClasses = getAllMembersOfType(MimClass.class);
		return removeStubs(allClasses, lowercaseKeys);
	}

	protected <T extends NamedElement> Map<String, T> removeStubs(final List<T> elements,
			final boolean lowercaseKeys) {
		Map<String, T> result = new HashMap<String, T>();
		for (T element : elements) {
			if (!element.isStub()) {
				String key = element.getName();
				if (lowercaseKeys)
					key = key.toLowerCase();
				result.put(key, element);
			} else {
				LOGGER.info(String.format("Stub %s was filtered out.", element.getName()));
			}
		}
		return result;
	}

}
