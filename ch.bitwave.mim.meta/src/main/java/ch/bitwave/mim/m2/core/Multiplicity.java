package ch.bitwave.mim.m2.core;

public class Multiplicity {
	// isOrdered
	// isUnique
	private int lower;
	private int upper;

	public Multiplicity() {
		this.lower = 1;
		this.upper = 1;
	}

	public int getLower() {
		return this.lower;
	}

	public void setLower(final int lower) {
		this.lower = lower;
	}

	public int getUpper() {
		return this.upper;
	}

	public void setUpper(final int upper) {
		this.upper = upper;
	}

	public boolean isMultiValued() {
		return this.upper == 0 || this.upper > 1;
	}

	public boolean includesCardinality(final int value) {
		boolean lowerMet = value >= this.lower;
		if (this.upper == 0) {
			return lowerMet;
		}
		return lowerMet && value <= this.upper;
	}

}
