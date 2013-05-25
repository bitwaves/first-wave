package ch.bitwave.lang.delphi.ast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class UsesSection extends Section {

	private List<Identifier> imports;

	public UsesSection() {
		this.imports = new ArrayList<Identifier>();
	}

	@Override
	public void remove(final Node node) {
		this.imports.remove(node);
	}

	@Override
	protected boolean internalAdd(final Node node) {
		if (node instanceof Identifier) {
			this.imports.add((Identifier) node);
			return true;
		}
		return false;
	}

	protected void addImport(final Identifier ref) {
		internalAdd(ref);
	}

	public List<Identifier> getImports() {
		return Collections.unmodifiableList(this.imports);
	}

	@Override
	protected void contributeDetails(final List<Node> details) {
		details.addAll(this.imports);
	}

	@Override
	public String toString() {
		return "UsesSection";
	}

	@Override
	public void accept(final DelphiSyntaxVisitor visitor) {
		visitor.visitUsesSection(this);
	}

}
