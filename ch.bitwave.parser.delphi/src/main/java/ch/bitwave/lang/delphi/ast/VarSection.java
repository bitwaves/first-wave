package ch.bitwave.lang.delphi.ast;

import java.util.ArrayList;
import java.util.List;

public class VarSection extends DeclarationSection {

	List<VariableDeclaration> variables;

	public VarSection() {
		this.variables = new ArrayList<VariableDeclaration>();
	}

	@Override
	public void remove(final Node node) {
		this.variables.remove(node);
	}

	@Override
	protected boolean internalAdd(final Node node) throws InvalidNodeException {
		assertNodeType(node, VariableDeclaration.class);
		this.variables.add((VariableDeclaration) node);
		return true;
	}

	@Override
	protected void contributeDetails(final List<Node> details) {
		details.addAll(this.variables);
	}

	@Override
	public String toString() {
		return "VarSection";
	}

	@Override
	public void accept(final DelphiSyntaxVisitor visitor) {
		visitor.visitVarSection(this);
	}

}
