package ch.bitwave.mim.m2.core;

public class GeneralizationEnd extends MimElement {

	private Classifier classifier;
	private Generalization generalization;

	public void setGeneralization(final Generalization generalization) {
		this.generalization = generalization;
	}

	public Classifier getClassifier() {
		return this.classifier;
	}

	public void setClassifier(final Classifier classifier) {
		this.classifier = classifier;
	}

	public Generalization getGeneralization() {
		return this.generalization;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("GeneralizationEnd [classifier=").append(this.classifier).append("]");
		return builder.toString();
	}

}
