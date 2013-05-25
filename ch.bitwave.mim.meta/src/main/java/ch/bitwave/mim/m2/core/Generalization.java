package ch.bitwave.mim.m2.core;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * A generalization is a taxonomic relationship between a more general
 * classifier and a more specific classifier. Each instance of the specific
 * classifier is also an instance of the general classifier. Thus, the specific
 * classifier indirectly has features of the more general classifier.
 * </p>
 * 
 * <p>
 * A generalization relates a specific classifier to a more general classifier,
 * and is owned by the specific classifier.
 * </p>
 */
public class Generalization extends MimElement implements DirectedRelationship {
	private GeneralizationEnd generalEnd;
	private GeneralizationEnd specificEnd;

	public Classifier getSpecific() {
		return this.specificEnd.getClassifier();
	}

	public void setSpecific(final Classifier specific) {
		if (this.specificEnd == null) {
			this.specificEnd = new GeneralizationEnd();
			this.specificEnd.setGeneralization(this);
		}
		if (this.specificEnd.getClassifier() != null) {
			this.specificEnd.getClassifier().removeGeneral(this.specificEnd);
		}
		this.specificEnd.setClassifier(specific);
		specific.addGeneral(this.specificEnd);
	}

	public Classifier getGeneral() {
		return this.generalEnd.getClassifier();
	}

	public void setGeneral(final Classifier general) {
		if (this.generalEnd == null) {
			this.generalEnd = new GeneralizationEnd();
			this.generalEnd.setGeneralization(this);
		}
		if (this.generalEnd.getClassifier() != null) {
			this.generalEnd.getClassifier().removeSpecific(this.generalEnd);
		}
		this.generalEnd.setClassifier(general);
		general.addSpecific(this.generalEnd);
	}

	public GeneralizationEnd getGeneralEnd() {
		return this.generalEnd;
	}

	public void setGeneralEnd(final GeneralizationEnd generalEnd) {
		this.generalEnd = generalEnd;
	}

	public GeneralizationEnd getSpecificEnd() {
		return this.specificEnd;
	}

	public void setSpecificEnd(final GeneralizationEnd specificEnd) {
		this.specificEnd = specificEnd;
	}

	@Override
	public List<MimElement> getRelatedElements() {
		List<MimElement> result = new ArrayList<MimElement>();
		result.add(getSpecific());
		result.add(getGeneral());
		return result;
	}

	@Override
	public List<MimElement> getSourceElements() {
		List<MimElement> result = new ArrayList<MimElement>();
		result.add(getSpecific());
		return result;
	}

	@Override
	public List<MimElement> getTargetElements() {
		List<MimElement> result = new ArrayList<MimElement>();
		result.add(getGeneral());
		return result;
	}

	@Override
	public String toString() {
		return "Generalization";
	}
}
