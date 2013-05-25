package ch.bitwave.mim.m2.core;

public interface MimType {
	public abstract boolean conformsTo(MimType other);

	public MimPackage getPackage();

	public void setPackage(MimPackage owner);
}
