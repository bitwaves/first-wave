package ch.bitwave.mim.m2.core;

/**
 * Defines how names are compared with each other.
 */
public interface NamingStrategy {
	boolean isSameName(String firstName, String secondName);
}
