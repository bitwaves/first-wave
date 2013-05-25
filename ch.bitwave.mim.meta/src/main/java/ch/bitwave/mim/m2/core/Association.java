package ch.bitwave.mim.m2.core;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * An association describes a set of tuples whose values refer to typed
 * instances. An instance of an association is called a link. A link is a tuple
 * with one value for each end of the association, where each value is an
 * instance of the type of the end.
 * </p>
 * 
 * <p>
 * An association specifies a semantic relationship that can occur between typed
 * instances. It has at least two ends represented by properties, each of which
 * is connected to the type of the end. More than one end of an association may
 * have the same type.
 * </p>
 * <p>
 * An end property of an association that is owned by an end class or that is a
 * navigable owned end of the association indicates that the association is
 * navigable from the opposite ends, otherwise the association is not navigable
 * from the opposite ends.
 * </p>
 */
public class Association extends Classifier implements Relationship {

	private List<Property> ownedEnds;
	private List<Property> memberEnds;

	public Association() {
		this.ownedEnds = new ArrayList<Property>();
		this.memberEnds = new ArrayList<Property>();
	}

	public Namespace getOwningNamespace() {
		return getOwnerAs(Namespace.class);
	}

	public void setOwningNamespace(final Namespace owningNamespace) {
		this.setOwner(owningNamespace);
	}

	/**
	 * The ends that are owned by the association itself. This is an ordered
	 * association. Subsets {@link Association#getMemberEnds()},
	 * {@link Classifier#getFeatures()} and {@link Namespace#getOwnedMembers()}.
	 * 
	 * @return
	 */
	public List<Property> getOwnedEnds() {
		return this.ownedEnds;
	}

	/**
	 * Each end represents participation of instances of the classifier
	 * connected to the end in links of the association. This is an ordered
	 * association and a subset of {@link #getMembers()}.
	 * 
	 * @return
	 */
	public List<Property> getMemberEnds() {
		return this.memberEnds;
	}

	/**
	 * References the classifiers that are used as types of the ends of the
	 * association.
	 * 
	 * @return
	 */
	public List<MimType> getEndTypes() {
		List<MimType> result = new ArrayList<MimType>();
		for (Property element : getMemberEnds()) {
			result.add(element.getType());
		}
		return result;
	}

	@Override
	public List<MimElement> getRelatedElements() {
		ArrayList<MimElement> result = new ArrayList<MimElement>(this.ownedEnds);
		result.addAll(this.memberEnds);
		return result;
	}

	/**
	 * The navigable ends that are owned by the association itself. Subsets
	 * {@link #getOwnedEnds()}.
	 * 
	 * @return
	 */
	public List<Property> getNavigableOwnedEnds() {
		// TODO: filter by navigability.
		return this.ownedEnds;
	}

	@Override
	protected void contributeFeatures(final List<Feature> list) {
		super.contributeFeatures(list);
		list.addAll(this.ownedEnds);
	}

	@Override
	protected void contributeOwnedMembers(final List<NamedElement> list) {
		list.addAll(this.ownedEnds);
	}

	@Override
	public void addOwnedMember(final NamedElement member) {
		Property prop = assertProperty(member);
		prop.setOwner(this);
		prop.setRelationship(this);
		this.memberEnds.add(prop);
		this.ownedEnds.add(prop);
	}

	@Override
	public void removeOwnedMember(final NamedElement member) {
		Property prop = assertProperty(member);
		if (prop.getOwner() == this) {
			prop.setOwner(null);
		}
		if (prop.getRelationship() == this) {
			prop.setRelationship(null);
		}
		this.memberEnds.remove(prop);
		this.ownedEnds.remove(prop);
	}

	protected Property assertProperty(final NamedElement member) {
		if (!(member instanceof Property)) {
			throw new RuntimeException(
					String.format(
							"An association can only own elements of type Property, but the given element is of type %s.",
							member.getClass().getSimpleName()));
		}
		Property prop = (Property) member;
		return prop;
	}

	public void addMember(final NamedElement member) {
		if (!(member instanceof Property)) {
			throw new RuntimeException(
					String.format(
							"An association can only have members of type Property, but the given element is of type %s.",
							member.getClass().getSimpleName()));
		}
		Property prop = (Property) member;
		prop.setRelationship(this);
		this.memberEnds.add(prop);
	}

}
