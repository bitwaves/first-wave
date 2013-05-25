package ch.bitwave.lang.delphi.ast;

import java.util.List;

public class RecordFieldDeclaration extends Node {
	private FixedPart fixedPart;
	private VariantPart variantPart;

	public FixedPart getFixedPart() {
		return this.fixedPart;
	}

	public void setFixedPart(final FixedPart fixedPart) {
		this.fixedPart = fixedPart;
	}

	public VariantPart getVariantPart() {
		return this.variantPart;
	}

	public void setVariantPart(final VariantPart variantPart) {
		this.variantPart = variantPart;
	}

	@Override
	public void remove(final Node node) {
		if (this.fixedPart == node) {
			this.fixedPart = null;
		} else if (this.variantPart == node) {
			this.variantPart = null;
		}
	}

	@Override
	protected boolean internalAdd(final Node node) throws InvalidNodeException {
		if (node instanceof FixedPart) {
			this.fixedPart = (FixedPart) node;
			return true;
		}
		if (node instanceof VariantPart) {
			this.variantPart = (VariantPart) node;
			return true;
		}
		return false;
	}

	@Override
	protected void contributeDetails(final List<Node> details) {
		contribute(details, this.fixedPart, this.variantPart);
	}

	@Override
	public void accept(final DelphiSyntaxVisitor visitor) {
		visitor.visitRecordFieldDeclaration(this);
	}

	@Override
	public String toString() {
		return "RecordFieldDeclaration";
	}

}
