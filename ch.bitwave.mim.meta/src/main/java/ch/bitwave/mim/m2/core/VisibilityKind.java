package ch.bitwave.mim.m2.core;

import java.util.Set;

/**
 * VisibilityKind is intended for use in the specification of visibility in
 * conjunction with, for example, the Imports, Generalizations, Packages, and
 * Classifiers packages. Detailed semantics are specified with those mechanisms.
 * If the Visibility package is used without those packages, these literals will
 * have different meanings, or no meanings.
 * <ol>
 * <li>A public element is visible to all elements that can access the contents
 * of the namespace that owns it.</li>
 * <li>A private element is only visible inside the namespace that owns it.</li>
 * <li>A protected element is visible to elements that have a generalization
 * relationship to the namespace that owns it.</li>
 * <li>A package element is owned by a namespace that is not a package, and is
 * visible to elements that are in the same package as its owning namespace.
 * Only named elements that are not owned by packages can be marked as having
 * package visibility. Any element marked as having package visibility is
 * visible to all elements within the nearest enclosing package (given that
 * other owning elements have proper visibility). Outside the nearest enclosing
 * package, an element marked as having package visibility is not visible.</li>
 * </ol>
 * In circumstances where a named element ends up with multiple visibilities,
 * for example by being imported multiple times, public visibility overrides
 * private visibility, i.e., if an element is imported twice into the same
 * namespace, once using public import and once using private import, it will be
 * public.
 */
public enum VisibilityKind {
	PRIVATE, PROTECTED, PACKAGE, PUBLIC;

	/**
	 * Examines a set of VisibilityKinds, and returns PUBLIC as the preferred
	 * visibility. If PUBLIC is not in the set, PRIVATE is returned.
	 * 
	 * @param set
	 *            the set to examine.
	 * @return the best visibility.
	 */
	public static VisibilityKind getBestVisibility(final Set<VisibilityKind> set) {
		if (set.contains(VisibilityKind.PUBLIC)) {
			return VisibilityKind.PUBLIC;
		}
		return VisibilityKind.PRIVATE;
	}
}
