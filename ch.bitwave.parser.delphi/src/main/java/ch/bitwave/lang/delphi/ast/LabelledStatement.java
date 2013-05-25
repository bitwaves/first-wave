package ch.bitwave.lang.delphi.ast;

import java.util.List;

public class LabelledStatement extends Statement {
	private Label label;
	private UnlabelledStatement statement;

	public Label getLabel() {
		return this.label;
	}

	public void setLabel(final Label label) {
		this.label = label;
	}

	public UnlabelledStatement getStatement() {
		return this.statement;
	}

	public void setStatement(final UnlabelledStatement statement) {
		this.statement = statement;
	}

	@Override
	public void remove(final Node node) {
		if (this.label == node) {
			this.label = null;
		} else if (this.statement == node) {
			this.statement = null;
		}
	}

	@Override
	protected boolean internalAdd(final Node node) throws InvalidNodeException {
		if (this.label == null) {
			assertNodeType(node, Label.class);
			this.label = (Label) node;
			return true;
		}
		if (this.statement == null) {
			assertNodeType(node, UnlabelledStatement.class);
			this.statement = (UnlabelledStatement) node;
			return true;
		}
		return false;
	}

	@Override
	protected void contributeDetails(final List<Node> details) {
		contribute(details, this.label, this.statement);
	}

	@Override
	public void accept(final DelphiSyntaxVisitor visitor) {
		visitor.visitLabelledStatement(this);
	}

}
