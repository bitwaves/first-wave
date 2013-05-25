package ch.bitwave.lang.delphi.ast;

import java.util.ArrayList;
import java.util.List;

public class RecordSection extends Node {

	private List<Identifier> identifiers;
	private Type type;

	public RecordSection() {
		this.identifiers = new ArrayList<Identifier>();
	}

	public List<Identifier> getIdentifiers() {
		return this.identifiers;
	}

	public Type getType() {
		return this.type;
	}

	public void setType(final Type type) {
		this.type = type;
	}

	@Override
	public void remove(final Node node) {
		this.identifiers.remove(node);
		if (this.type == node) {
			this.type = null;
		}
	}

	@Override
	protected boolean internalAdd(final Node node) throws InvalidNodeException {
		if (node instanceof Identifier) {
			this.identifiers.add((Identifier) node);
			return true;
		}
		if (node instanceof Type) {
			this.type = (Type) node;
			return true;
		}
		return false;
	}

	@Override
	protected void contributeDetails(final List<Node> details) {
		details.addAll(this.identifiers);
		contribute(details, this.type);
	}

	@Override
	public void accept(final DelphiSyntaxVisitor visitor) {
		visitor.visitRecordSection(this);
	}

	@Override
	public String toString() {
		return "RecordSection";
	}

}
