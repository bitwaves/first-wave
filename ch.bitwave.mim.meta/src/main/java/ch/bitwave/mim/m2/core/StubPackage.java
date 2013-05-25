package ch.bitwave.mim.m2.core;

public class StubPackage extends MimPackage {

	private PackageImport owningImport;

	public StubPackage(final PackageImport owningImport) {
		super();
		this.owningImport = owningImport;
	}

	public PackageImport getOwningImport() {
		return this.owningImport;
	}

	public void replaceWith(final MimPackage resolved) {
		this.owningImport.setImportedPackage(resolved);
		removeImportingPackage(this.owningImport);
		resolved.addImportingPackage(this.owningImport);
	}

	@Override
	public boolean isStub() {
		return true;
	}

}
