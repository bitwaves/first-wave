package ch.bitwave.lang.delphi.ast;

import ch.bitwave.platform.codestyle.DBC;

public abstract class MemberDeclaration extends IdentifiedNode {

	private AccessModifierKind accessKind;

	public MemberDeclaration(final AccessModifierKind accessKind) {
		super();
		DBC.PRE.assertNotNull(accessKind, "member access modifier");
		this.accessKind = accessKind;
	}

	public AccessModifierKind getAccessKind() {
		return this.accessKind;
	}

	public void setAccessKind(final AccessModifierKind accessKind) {
		this.accessKind = accessKind;
	}

	public boolean isAbstract() {
		return false;
	}

}
