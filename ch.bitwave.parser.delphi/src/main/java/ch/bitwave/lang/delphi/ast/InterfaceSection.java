package ch.bitwave.lang.delphi.ast;

public class InterfaceSection extends UnitSection {

	@Override
	protected boolean internalAdd(final Node node) throws InvalidNodeException {
		if (super.internalAdd(node))
			return true;
		if (node instanceof ProceduralDeclaration) {
			addProceduralDeclaration((ProceduralDeclaration) node);
			return true;
		}
		return false;
	}

	public void addProceduralDeclaration(final ProceduralDeclaration node) {
		insertNode(node);
	}

	@Override
	public String toString() {
		return "InterfaceSection";
	}

	@Override
	public void accept(final DelphiSyntaxVisitor visitor) {
		visitor.visitInterfaceSection(this);
	}

}
