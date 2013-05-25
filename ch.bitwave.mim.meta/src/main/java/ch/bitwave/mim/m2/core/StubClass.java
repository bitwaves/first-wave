package ch.bitwave.mim.m2.core;

import java.util.ArrayList;
import java.util.List;

public class StubClass extends MimClass {
	private String stubReason;
	private List<MimElement> referers;

	public StubClass() {
		this.referers = new ArrayList<MimElement>();
	}

	public String getStubReason() {
		return this.stubReason;
	}

	public void setStubReason(final String stubReason) {
		this.stubReason = stubReason;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("StubClass ").append(getName()).append(": ").append(this.stubReason);
		return builder.toString();
	}

	/**
	 * Makes all relationship ends pointing at this stub point at the given
	 * class instead.
	 * 
	 * @param resolved
	 */
	public void replaceWith(final MimClass replacement) {
		if (replacement.isStub())
			throw new MetaModelRuntimeException(String.format(
					"Cannot replace stub %s with stub %s.", this, replacement));
		for (MimElement element : this.referers) {
			if (element instanceof Generalization) {
				((Generalization) element).setGeneral(replacement);
			}
		}
		MimPackage owner = getOwnerAs(MimPackage.class);
		owner.removeOwnedMember(this);
	}

	@Override
	protected void contributeOwnedMembers(final List<NamedElement> list) {
	}

	public void addReferer(final MimElement element) {
		this.referers.add(element);
	}

	@Override
	public boolean isStub() {
		return true;
	}

}
