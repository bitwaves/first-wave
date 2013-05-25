package ch.bitwave.mim.m2.core;

import java.util.ArrayList;
import java.util.List;

/**
 * Element is an abstract metaclass with no superclass. It is used as the common
 * superclass for all metaclasses in the infrastructure library. Element has a
 * derived composition association to itself to support the general capability
 * for elements to own other elements.
 */
public abstract class MimElement {

	private List<Comment> ownedComments;
	private MimElement owner;

	public MimElement getOwner() {
		return this.owner;
	}

	public void setOwner(final MimElement owner) {
		this.owner = owner;
	}

	@SuppressWarnings("unchecked")
	public <T> T getOwnerAs(final java.lang.Class<T> elementType) {
		if (this.owner == null)
			return null;
		if (!elementType.isAssignableFrom(this.owner.getClass())) {
			throw new RuntimeException(String.format(
					"The owner of %s is not of the desired type \"%s\".", toString(), elementType
							.getClass().getSimpleName()));
		}
		return (T) this.owner;
	}

	public final List<MimElement> getOwnedElements() {
		List<MimElement> result = new ArrayList<MimElement>();
		contributeOwnedElements(result);
		return result;
	}

	protected void contributeOwnedElements(final List<MimElement> result) {
		result.addAll(this.ownedComments);
	}

	public boolean isStub() {
		return false;
	}

}
