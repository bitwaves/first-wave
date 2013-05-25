package ch.bitwave.mim.m2.core;

public class StubType extends NamedElement implements DataType {
	private String message;

	public String getMessage() {
		return this.message;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("StubType ").append(getName()).append(": ").append(this.message);
		return builder.toString();
	}

	@Override
	public boolean conformsTo(final MimType other) {
		return false;
	}

	@Override
	public MimPackage getPackage() {
		return getOwnerAs(MimPackage.class);
	}

	@Override
	public void setPackage(final MimPackage owner) {
		setOwner(owner);
	}
}
