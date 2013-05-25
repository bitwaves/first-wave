package ch.bitwave.mim.m2.core;

import java.util.List;

/**
 * A directed relationship references one or more source elements and one or
 * more target elements. DirectedRelationship is an abstract metaclass.
 */
public interface DirectedRelationship extends Relationship {

	List<MimElement> getSourceElements();

	List<MimElement> getTargetElements();

}
