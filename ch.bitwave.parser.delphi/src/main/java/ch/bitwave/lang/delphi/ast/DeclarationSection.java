package ch.bitwave.lang.delphi.ast;

public abstract class DeclarationSection extends Section {

	public boolean isOnUnit() {
		return this.getParent() instanceof UnitSection;
	}

}
