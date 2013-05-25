package ch.bitwave.lang.delphi.ast;

import java.util.ArrayList;
import java.util.List;

public class ConstSection extends DeclarationSection {

	private List<ConstDeclaration> constants;

	public ConstSection() {
		this.constants = new ArrayList<ConstDeclaration>();
	}

	public List<ConstDeclaration> getConstants() {
		return this.constants;
	}

	@Override
	public void remove(final Node node) {
		this.constants.remove(node);
	}

	@Override
	protected boolean internalAdd(final Node node) {
		if (node instanceof ConstDeclaration) {
			this.constants.add((ConstDeclaration) node);
			return true;
		}
		return false;
	}

	@Override
	protected void contributeDetails(final List<Node> details) {
		details.addAll(this.constants);
	}

	@Override
	public void accept(final DelphiSyntaxVisitor visitor) {
		visitor.visitConstSection(this);
	}

	@Override
	public String toString() {
		return "ConstSection";
	}

}
