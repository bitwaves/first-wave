package ch.bitwave.lang.delphi.ast;

import java.util.ArrayList;
import java.util.List;

public class LabelSection extends DeclarationSection {

	private List<Label> labels;

	public LabelSection() {
		this.labels = new ArrayList<Label>();
	}

	@Override
	public void remove(final Node node) {
		this.labels.remove(node);
	}

	@Override
	protected boolean internalAdd(final Node node) throws InvalidNodeException {
		assertNodeType(node, Label.class);
		this.labels.add((Label) node);
		return true;
	}

	@Override
	protected void contributeDetails(final List<Node> details) {
		details.addAll(this.labels);
	}

	@Override
	public void accept(final DelphiSyntaxVisitor visitor) {
		visitor.visitLabelSection(this);
	}

}
