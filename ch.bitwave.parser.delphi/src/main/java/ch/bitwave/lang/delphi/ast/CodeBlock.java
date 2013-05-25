package ch.bitwave.lang.delphi.ast;

import java.util.ArrayList;
import java.util.List;

public class CodeBlock extends Node {

	private List<Node> sections;
	private CompoundStatement content;

	public CodeBlock() {
		this.sections = new ArrayList<Node>();
	}

	public CompoundStatement getContent() {
		return this.content;
	}

	public void setContent(final CompoundStatement content) {
		this.content = content;
	}

	public List<Node> getSections() {
		return this.sections;
	}

	@Override
	public void remove(final Node node) {
		if (this.content == node) {
			this.content = null;
		}
		this.sections.remove(node);
	}

	@Override
	protected boolean internalAdd(final Node node) throws InvalidNodeException {
		if (node instanceof DeclarationSection || node instanceof ProceduralImplementation
				|| node instanceof ProceduralDeclaration) {
			this.sections.add(node);
			return true;
		}
		if (node instanceof CompoundStatement) {
			this.content = (CompoundStatement) node;
			return true;
		}
		return false;
	}

	@Override
	protected void contributeDetails(final List<Node> details) {
		details.addAll(this.sections);
		contribute(details, this.content);
	}

	@Override
	public String toString() {
		return "CodeBlock";
	}

	@Override
	public void accept(final DelphiSyntaxVisitor visitor) {
		visitor.visitCodeBlock(this);
	}
}
