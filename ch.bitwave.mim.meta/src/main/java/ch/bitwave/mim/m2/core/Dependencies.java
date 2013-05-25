package ch.bitwave.mim.m2.core;

import java.util.HashSet;
import java.util.Set;

public class Dependencies implements Dependent {
	private Set<String> identifierDependencies;

	public Dependencies() {
		this.identifierDependencies = new HashSet<String>();
	}

	@Override
	public boolean depdendsOnIdentifier(final String name) {
		if (this.identifierDependencies == null) {
			return false;
		}
		return this.identifierDependencies.contains(name);
	}

	@Override
	public void addIdentifierDependency(final String name) {
		if (this.identifierDependencies == null) {
			this.identifierDependencies = new HashSet<String>();
		}
		this.identifierDependencies.add(name);
	}

	public Set<String> getIdentifierDependencies() {
		return this.identifierDependencies;
	}

}
