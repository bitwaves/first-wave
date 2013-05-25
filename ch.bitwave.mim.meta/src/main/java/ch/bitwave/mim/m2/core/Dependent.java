package ch.bitwave.mim.m2.core;

/**
 * Interface to an element which is depending on some other elements. Provides a
 * list of elements the element depends on and a means of extending the list.
 */
public interface Dependent {

	boolean depdendsOnIdentifier(final String name);

	void addIdentifierDependency(final String name);

}
