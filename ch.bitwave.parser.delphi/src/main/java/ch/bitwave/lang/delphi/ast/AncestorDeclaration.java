package ch.bitwave.lang.delphi.ast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AncestorDeclaration extends Node {

	private List<Identifier> ancestors;

	public AncestorDeclaration() {
		this.ancestors = new ArrayList<Identifier>();
	}

	@Override
	public void remove(final Node node) {
		this.ancestors.remove(node);
	}

	@Override
	protected boolean internalAdd(final Node node) {
		if (node instanceof Identifier) {
			this.ancestors.add((Identifier) node);
			return true;
		}
		return false;
	}

	public void addImport(final Identifier ref) {
		internalAdd(ref);
	}

	public List<Identifier> getAncestors() {
		return Collections.unmodifiableList(this.ancestors);
	}

	@Override
	protected void contributeDetails(final List<Node> details) {
		details.addAll(this.ancestors);
	}

	@Override
	public String toString() {
		return "AncestorDeclaration";
	}

	@Override
	public void accept(final DelphiSyntaxVisitor visitor) {
		visitor.visitAncestorDeclaration(this);
	}

}
