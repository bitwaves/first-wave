package ch.bitwave.lang.delphi.ast;

public abstract class Member extends Node {
	private AccessKind accessModifier;
	private String name;

	public AccessKind getAccessModifier() {
		return this.accessModifier;
	}

	public void setAccessModifier(final AccessKind accessModifier) {
		this.accessModifier = accessModifier;
	}

	public String getName() {
		return this.name;
	}

	public void setName(final String name) {
		this.name = name;
	}

}
