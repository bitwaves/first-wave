package ch.bitwave.lang.delphi.ast;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.CheckForNull;

public abstract class UnitSection extends Section {

	private List<Node> contents;

	public UnitSection() {
		this.contents = new ArrayList<Node>();
	}

	@CheckForNull
	public UsesSection getUsesSection() {
		return findDetailOfType(UsesSection.class);
	}

	@CheckForNull
	public List<TypeSection> getTypeSections() {
		return getDetailsOfType(TypeSection.class);
	}

	@Override
	public void remove(final Node node) {
		this.contents.remove(node);
	}

	protected final void insertNode(final ProceduralDeclaration node) {
		this.contents.add(node);
	}

	@Override
	protected boolean internalAdd(final Node node) throws InvalidNodeException {
		if (node instanceof Section) {
			this.contents.add(node);
			return true;
		}
		return false;
	}

	@Override
	protected void contributeDetails(final List<Node> details) {
		details.addAll(this.contents);
	}

}
