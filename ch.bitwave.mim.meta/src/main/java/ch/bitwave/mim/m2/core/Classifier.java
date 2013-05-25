package ch.bitwave.mim.m2.core;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;

import javax.annotation.Nonnull;

/**
 * A classifier is a classification of instances — it describes a set of
 * instances that have features in common.
 */
public abstract class Classifier extends Namespace {
	private List<GeneralizationEnd> generals;
	private List<GeneralizationEnd> specifics;
	private boolean abstractClassifier;
	private List<Feature> nonOwnedFeatures;

	public Classifier() {
		super();
		this.nonOwnedFeatures = new ArrayList<Feature>();
		this.generals = new ArrayList<GeneralizationEnd>();
		this.specifics = new ArrayList<GeneralizationEnd>();
	}

	public boolean isAbstract() {
		return this.abstractClassifier;
	}

	public void setAbstract(final boolean value) {
		this.abstractClassifier = value;
	}

	public void addGeneral(final GeneralizationEnd genEnd) {
		this.generals.add(genEnd);
	}

	public void removeGeneral(final GeneralizationEnd genEnd) {
		this.generals.remove(genEnd);
	}

	public void addSpecific(final GeneralizationEnd genEnd) {
		this.specifics.add(genEnd);
	}

	public void removeSpecific(final GeneralizationEnd genEnd) {
		this.specifics.remove(genEnd);
	}

	/**
	 * Returns the list of features directly introduced by this classifier.
	 * 
	 * @return the list of features.
	 */
	public List<Feature> getFeatures() {
		List<Feature> result = new ArrayList<Feature>();
		contributeFeatures(result);
		return result;
	}

	protected void contributeFeatures(final List<Feature> list) {
		list.addAll(this.nonOwnedFeatures);
	}

	/**
	 * The query allFeatures() gives all of the features in the namespace of the
	 * classifier. In general, through mechanisms such as inheritance, this will
	 * be a larger set than feature.
	 * 
	 * @return the complete list of features attributed to this classifier.
	 */
	public List<Feature> getAllFeatures() {
		List<Feature> result = getFeatures();
		for (Classifier parent : getAllParents()) {
			parent.contributeFeatures(result);
		}
		return result;
	}

	public List<Generalization> getGeneralizations() {
		List<Generalization> result = new ArrayList<Generalization>();
		for (GeneralizationEnd general : this.generals) {
			result.add(general.getGeneralization());
		}
		return result;
	}

	public List<Generalization> getSpecializations() {
		List<Generalization> result = new ArrayList<Generalization>();
		for (GeneralizationEnd general : this.specifics) {
			result.add(general.getGeneralization());
		}
		return result;
	}

	/**
	 * Specifies the more general classifiers in the generalization hierarchy
	 * for this Classifier.
	 * 
	 * @return the list of generals.
	 */
	public List<Classifier> getGenerals() {
		List<Classifier> result = new ArrayList<Classifier>();
		for (GeneralizationEnd genend : this.generals) {
			result.add(genend.getGeneralization().getGeneral());
		}
		return result;
	}

	/**
	 * Specifies the more general classifiers in the generalization hierarchy
	 * for this Classifier.
	 * 
	 * @return the list of generals.
	 */
	public List<Classifier> getSpecifics() {
		List<Classifier> result = new ArrayList<Classifier>();
		for (GeneralizationEnd genend : this.specifics) {
			result.add(genend.getGeneralization().getSpecific());
		}
		return result;
	}

	/**
	 * Returns all of the immediate ancestors of this Classifier. Synonymous for
	 * {@link #getGenerals()}.
	 * 
	 * @return the list of direct parents.
	 */
	public List<Classifier> getParents() {
		return getGenerals();
	}

	/**
	 * Returns all of the direct and indirect ancestors of this Classifier.
	 * 
	 * @return the list of all parents, including indirect ones.
	 */
	public List<Classifier> getAllParents() {
		List<Classifier> result = new ArrayList<Classifier>();
		this.contributeParents(result);
		return result;
	}

	protected void contributeParents(final List<Classifier> list) {
		list.add(this);
		for (Classifier parent : getParents()) {
			parent.contributeParents(list);
		}
	}

	@Override
	protected void contributeOwnedElements(final List<MimElement> result) {
		super.contributeOwnedElements(result);
		result.addAll(getFeatures());
	}

	/**
	 * Specifies all elements inherited by this classifier from the general
	 * classifiers. Subsets the namespace members.
	 * 
	 * @return
	 */
	public List<NamedElement> getInheritedMembers() {
		List<NamedElement> result = new ArrayList<NamedElement>();
		List<Classifier> generals = getGenerals();
		for (Classifier general : generals) {
			result.addAll(general.getAllFeatures());
		}
		return result;
	}

	public boolean isStereotype(final Stereotype stereotype) {
		List<Property> properties = getOwnedMembersOfType(Property.class);
		for (Property property : properties) {
			Relationship rel = property.getRelationship();
			if (rel instanceof Extension) {
				Extension ext = (Extension) rel;
				if (stereotype == ext.getMetaClass()) {
					return true;
				}
			}
		}
		return false;
	}

	@SuppressWarnings("unchecked")
	public <T> List<T> getFeaturesOfType(final java.lang.Class<T> featureType) {
		List<T> result = new ArrayList<T>();
		for (Feature feature : getFeatures()) {
			if (featureType.isAssignableFrom(feature.getClass())) {
				result.add((T) feature);
			}
		}
		return result;
	}

	public void addNonOwnedFeature(@Nonnull final Feature feature) {
		this.nonOwnedFeatures.add(feature);
	}

	/**
	 * Checks whether this classifier is a specialization of the given
	 * classifier.
	 * 
	 * @param general
	 *            the classifier to test as a general of this classifier.
	 * @return true if general is an ancestor of this classifier.
	 */
	public boolean isSpecializationOf(final Classifier general) {
		for (GeneralizationEnd genend : this.generals) {
			if (genend.getGeneralization().getGeneral() == general) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Checks whether this classifier is a generalization of the given
	 * classifier.
	 * 
	 * @param specific
	 *            the classifier to test as a specific of this classifier.
	 * @return true if general derives from this classifier.
	 */
	public boolean isGeneralizationOf(final Classifier specific) {
		for (GeneralizationEnd genend : this.specifics) {
			if (genend.getGeneralization().getSpecific() == specific) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Tells whether this classifier has a feature (including inherited
	 * features) whose (unqualified) name matches the given matcher.
	 * 
	 * @param matcher
	 *            the matcher to judge the names by.
	 * @return true if the classifier has a matching feature.
	 */
	public boolean hasFeatureMatching(@Nonnull final Matcher matcher) {
		for (Feature feature : getAllFeatures()) {
			String name = feature.getName();
			if (name != null) {
				matcher.reset(name);
				if (matcher.matches())
					return true;
			}
		}
		return false;
	}

	/**
	 * Tells whether this classifier introduces a feature whose (unqualified)
	 * name matches the given matcher.
	 * 
	 * @param matcher
	 *            the matcher to judge the names by.
	 * @return true if the classifier introduces a matching feature.
	 */
	public boolean introducesFeatureMatching(@Nonnull final Matcher matcher) {
		for (Feature feature : getFeatures()) {
			String name = feature.getName();
			if (name != null) {
				matcher.reset(name);
				if (matcher.matches())
					return true;
			}
		}
		return false;
	}
}
