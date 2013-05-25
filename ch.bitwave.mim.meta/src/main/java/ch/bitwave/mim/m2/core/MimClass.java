package ch.bitwave.mim.m2.core;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * <p>
 * A class is a type that has objects as its instances.
 * </p>
 * 
 * <p>
 * Classes have attributes and operations and participate in inheritance
 * hierarchies. Multiple inheritance is allowed. The instances of a class are
 * objects. When a class is abstract it cannot have any direct instances. Any
 * direct instance of a concrete (i.e., non-abstract) class is also an indirect
 * instance of its class's superclasses. An object has a slot for each of its
 * class's direct and inherited attributes. An object permits the invocation of
 * operations defined in its class and its class's superclasses. The context of
 * such an invocation is the invoked object.
 * </p>
 * 
 * <p>
 * A class cannot access private features of another class, or protected
 * features on another class that is not its supertype. When creating and
 * deleting associations, at least one end must allow access to the class.
 * </p>
 * 
 * <p>
 * The purpose of a class is to specify a classification of objects and to
 * specify the features that characterize the structure and behavior of those
 * objects.
 * </p>
 * <p>
 * Objects of a class must contain values for each attribute that is a member of
 * that class, in accordance with the characteristics of the attribute, for
 * example its type and multiplicity. When an object is instantiated in a class,
 * for every attribute of the class that has a specified default, if an initial
 * value of the attribute is not specified explicitly for the instantiation,
 * then the default value specification is evaluated to set the initial value of
 * the attribute for the object.
 * </p>
 * <p>
 * Operations of a class can be invoked on an object, given a particular set of
 * substitutions for the parameters of the operation. An operation invocation
 * may cause changes to the values of the attributes of that object. It may also
 * return a value as a result, where a result type for the operation has been
 * defined. Operation invocations may also cause changes in value to the
 * attributes of other objects that can be navigated to, directly or indirectly,
 * from the object on which the operation is invoked, to its output parameters,
 * to objects navigable from its parameters, or to other objects in the scope of
 * the operation’s execution. Operation invocations may also cause the creation
 * and deletion of objects.
 * </p>
 */
public class MimClass extends Classifier implements MimType, Dependent {

	private List<Property> ownedAttributes;
	private List<Operation> ownedOperations;
	private boolean abstractFlag;
	private Dependencies dependencies;

	public MimClass() {
		this.ownedAttributes = new ArrayList<Property>();
		this.ownedOperations = new ArrayList<Operation>();
	}

	@Override
	public boolean isAbstract() {
		return this.abstractFlag;
	}

	@Override
	public void setAbstract(final boolean value) {
		this.abstractFlag = value;
	}

	public MimPackage getOwningPackage() {
		return getOwnerAs(MimPackage.class);
	}

	public void setOwningPackage(final MimPackage owningPackage) {
		this.setOwner(owningPackage);
	}

	public List<Property> getOwnedAttributes() {
		return this.ownedAttributes;
	}

	public List<Operation> getOwnedOperations() {
		return this.ownedOperations;
	}

	@Override
	public boolean conformsTo(final MimType other) {
		return false;
	}

	public List<MimClass> getSuperClasses() {
		List<MimClass> result = new ArrayList<MimClass>();
		List<Classifier> generals = getGenerals();
		for (Classifier general : generals) {
			if (general instanceof MimClass) {
				result.add((MimClass) general);
			}
		}
		return result;
	}

	@Override
	protected void contributeFeatures(final List<Feature> list) {
		super.contributeFeatures(list);
		list.addAll(this.ownedAttributes);
		list.addAll(this.ownedOperations);
	}

	@Override
	protected void contributeOwnedMembers(final List<NamedElement> list) {
		list.addAll(this.ownedAttributes);
		list.addAll(this.ownedOperations);
	}

	@Override
	public MimPackage getPackage() {
		return getOwningPackage();
	}

	@Override
	public void setPackage(final MimPackage owner) {
		this.setOwner(owner);
	}

	@Override
	public void addOwnedMember(final NamedElement member) {
		if (member instanceof Property) {
			this.ownedAttributes.add((Property) member);
		} else if (member instanceof Operation) {
			this.ownedOperations.add((Operation) member);
		} else {
			throw new RuntimeException(
					String.format(
							"A class can only own elements of type Property or Operation, but the given element is of type %s.",
							member.getClass().getSimpleName()));
		}
	}

	@Override
	public void removeOwnedMember(final NamedElement member) {
		this.ownedAttributes.remove(member);
		this.ownedOperations.remove(member);
	}

	/**
	 * Adds the given stereotype as a new stereotype of this class.
	 * 
	 * @param stereotype
	 *            the stereotype to add.
	 */
	public void addStereotype(final Stereotype stereotype) {
		Extension ext = new Extension();
		ExtensionEnd ee = new ExtensionEnd();
		ee.setType(stereotype);
		Property prop = new Property();
		prop.setType(this);
		ext.addMember(ee);
		ext.addOwnedMember(prop);
		addOwnedMember(prop);
		stereotype.addNonOwnedFeature(ee);
		getOwnerAs(Namespace.class).addOwnedMember(ext);
	}

	@Override
	public boolean depdendsOnIdentifier(final String name) {
		if (this.dependencies == null) {
			return false;
		}
		return this.dependencies.depdendsOnIdentifier(name);
	}

	@Override
	public void addIdentifierDependency(final String name) {
		if (this.dependencies == null) {
			this.dependencies = new Dependencies();
		}
		this.dependencies.addIdentifierDependency(name);
	}

	public Set<String> getIdentifierDependencies() {
		if (this.dependencies == null) {
			return new HashSet<String>();
		}
		return this.dependencies.getIdentifierDependencies();
	}
}
