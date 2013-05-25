package ch.bitwave.lang.delphi.ast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ImplementationSection extends UnitSection {

	private List<InvokableImplementation> implementations;

	public ImplementationSection() {
		this.implementations = new ArrayList<InvokableImplementation>();
	}

	@Override
	public String toString() {
		return "ImplementationSection";
	}

	public List<InvokableImplementation> getImplementations() {
		return Collections.unmodifiableList(this.implementations);
	}

	@Override
	public void remove(final Node node) {
		super.remove(node);
		this.implementations.remove(node);
	}

	@Override
	protected boolean internalAdd(final Node node) throws InvalidNodeException {
		if (super.internalAdd(node))
			return true;
		if (node instanceof InvokableImplementation) {
			this.implementations.add((InvokableImplementation) node);
			return true;
		}
		return false;
	}

	@Override
	protected void contributeDetails(final List<Node> details) {
		super.contributeDetails(details);
		details.addAll(this.implementations);
	}

	@Override
	public void accept(final DelphiSyntaxVisitor visitor) {
		visitor.visitImplementationSection(this);
	}

}
