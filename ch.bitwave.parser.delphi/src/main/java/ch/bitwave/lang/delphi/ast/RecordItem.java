package ch.bitwave.lang.delphi.ast;

import java.util.ArrayList;
import java.util.List;

public class RecordItem extends Node {

	private List<RecordField> fields;

	public RecordItem() {
		this.fields = new ArrayList<RecordField>();
	}

	@Override
	public void remove(final Node node) {
		this.fields.remove(node);
	}

	@Override
	protected boolean internalAdd(final Node node) throws InvalidNodeException {
		if (node instanceof RecordField) {
			this.fields.add((RecordField) node);
			return true;
		}
		return false;
	}

	@Override
	protected void contributeDetails(final List<Node> details) {
		details.addAll(this.fields);
	}

	@Override
	public void accept(final DelphiSyntaxVisitor visitor) {
		visitor.visitRecordItem(this);
	}

	@Override
	public String toString() {
		return "RecordItem";
	}

}
