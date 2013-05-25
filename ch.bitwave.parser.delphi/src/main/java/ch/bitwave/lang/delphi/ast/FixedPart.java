package ch.bitwave.lang.delphi.ast;

import java.util.ArrayList;
import java.util.List;

public class FixedPart extends Node {

	private List<RecordSection> sections;

	public FixedPart() {
		this.sections = new ArrayList<RecordSection>();
	}

	public List<RecordSection> getSections() {
		return this.sections;
	}

	@Override
	public void remove(final Node node) {
		this.sections.remove(node);
	}

	@Override
	protected boolean internalAdd(final Node node) throws InvalidNodeException {
		if (node instanceof RecordSection) {
			this.sections.add((RecordSection) node);
			return true;
		}
		return false;
	}

	@Override
	protected void contributeDetails(final List<Node> details) {
		details.addAll(this.sections);
	}

	@Override
	public void accept(final DelphiSyntaxVisitor visitor) {
		visitor.visitFixedPart(this);
	}

	@Override
	public String toString() {
		return "FixedPart";
	}

}
