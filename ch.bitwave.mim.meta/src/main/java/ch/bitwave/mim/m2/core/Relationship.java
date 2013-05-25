package ch.bitwave.mim.m2.core;

import java.util.List;

/**
 * Relationship is an abstract concept that specifies some kind of relationship
 * between elements.
 */
public interface Relationship {

	List<MimElement> getRelatedElements();

}
