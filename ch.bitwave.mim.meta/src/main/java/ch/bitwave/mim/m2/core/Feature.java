package ch.bitwave.mim.m2.core;

/**
 * A feature declares a behavioral or structural characteristic of instances of
 * classifiers.
 */
public abstract class Feature extends NamedElement {
	private Classifier featuringClassifier;

	public Classifier getFeaturingClassifier() {
		return this.featuringClassifier;
	}

	public void setFeaturingClassifier(final Classifier featuringClassifier) {
		this.featuringClassifier = featuringClassifier;
	}

}
