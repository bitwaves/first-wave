package ch.bitwave.lang.delphi.ast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TypeSection extends DeclarationSection {

	private List<TypeDeclaration> declaredTypes;

	public TypeSection() {
		this.declaredTypes = new ArrayList<TypeDeclaration>();
	}

	@Override
	public void remove(final Node node) {

	}

	@Override
	protected boolean internalAdd(final Node node) throws InvalidNodeException {
		assertNodeType(node, TypeDeclaration.class);
		this.declaredTypes.add((TypeDeclaration) node);
		return true;
	}

	@Override
	protected void contributeDetails(final List<Node> details) {
		details.addAll(this.declaredTypes);
	}

	public List<TypeDeclaration> getDeclaredTypes() {
		return Collections.unmodifiableList(this.declaredTypes);
	}

	@Override
	public String toString() {
		return "TypeSection";
	}

	@Override
	public void accept(final DelphiSyntaxVisitor visitor) {
		visitor.visitTypeSection(this);
	}

}
