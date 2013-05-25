package ch.bitwave.lang.delphi.ast;

import java.util.ArrayList;
import java.util.List;

public class VariantPart extends IdentifiedNode {

	private TypeIdentifier type;
	private List<Variant> variants;

	public VariantPart() {
		this.variants = new ArrayList<Variant>();
	}

	public TypeIdentifier getType() {
		return this.type;
	}

	public void setType(final TypeIdentifier type) {
		this.type = type;
	}

	public List<Variant> getVariants() {
		return this.variants;
	}

	public void setVariants(final List<Variant> variants) {
		this.variants = variants;
	}

	@Override
	public void remove(final Node node) {
		super.remove(node);
		if (this.type == node) {
			this.type = null;
		}
	}

	@Override
	protected boolean internalAdd(final Node node) throws InvalidNodeException {
		if (super.internalAdd(node))
			return true;
		if (node instanceof TypeIdentifier) {
			this.type = (TypeIdentifier) node;
			return true;
		}
		if (node instanceof Variant) {
			this.variants.add((Variant) node);
			return true;
		}
		return false;
	}

	@Override
	protected void contributeDetails(final List<Node> details) {
		super.contributeDetails(details);
		contribute(details, this.type);
		details.addAll(this.variants);
	}

	@Override
	public void accept(final DelphiSyntaxVisitor visitor) {
		visitor.visitVariantPart(this);
	}

	@Override
	public String toString() {
		return "VariantPart";
	}

}
