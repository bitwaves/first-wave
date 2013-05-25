package ch.bitwave.lang.delphi.ast;

import java.util.ArrayList;
import java.util.List;

public class ArrayType extends Type {
	private List<DimensionQualifier> qualifiers;
	private Type componentType;

	public Type getComponentType() {
		return this.componentType;
	}

	public void setComponentType(final Type componentType) {
		this.componentType = componentType;
	}

	@Override
	public void accept(final DelphiSyntaxVisitor visitor) {
		visitor.visitArrayType(this);
	}

	public ArrayType() {
		this.qualifiers = new ArrayList<DimensionQualifier>();
	}

	public List<DimensionQualifier> getQualifiers() {
		return this.qualifiers;
	}

	@Override
	public void remove(final Node node) {
		if (this.componentType == node) {
			this.componentType = null;
		} else {
			this.qualifiers.remove(node);
		}
	}

	@Override
	protected boolean internalAdd(final Node node) throws InvalidNodeException {
		if (node instanceof Type) {
			this.componentType = (Type) node;
			return true;
		}
		if (node instanceof DimensionQualifier) {
			this.qualifiers.add((DimensionQualifier) node);
			return true;
		}
		return false;
	}

	@Override
	protected void contributeDetails(final List<Node> details) {
		details.addAll(this.qualifiers);
		contribute(details, this.componentType);
	}

	@Override
	public String toString() {
		return "ArrayType";
	}
}
