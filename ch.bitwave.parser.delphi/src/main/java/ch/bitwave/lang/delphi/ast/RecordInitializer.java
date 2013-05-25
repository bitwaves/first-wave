package ch.bitwave.lang.delphi.ast;

import java.util.ArrayList;
import java.util.List;

public class RecordInitializer extends Node {

	private List<RecordItem> items;

	public RecordInitializer() {
		this.items = new ArrayList<RecordItem>();
	}

	@Override
	public void remove(final Node node) {
		this.items.remove(node);
	}

	@Override
	protected boolean internalAdd(final Node node) throws InvalidNodeException {
		if (node instanceof RecordItem) {
			this.items.add((RecordItem) node);
			return true;
		}
		return false;
	}

	@Override
	protected void contributeDetails(final List<Node> details) {
		details.addAll(this.items);
	}

	@Override
	public void accept(final DelphiSyntaxVisitor visitor) {
		visitor.visitRecordInitializer(this);
	}

	@Override
	public String toString() {
		return "RecordInitializer";
	}

}
