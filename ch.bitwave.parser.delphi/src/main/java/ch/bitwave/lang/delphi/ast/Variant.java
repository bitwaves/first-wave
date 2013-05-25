package ch.bitwave.lang.delphi.ast;

import java.util.ArrayList;
import java.util.List;

public class Variant extends Node {

	private List<Constant> constants;
	private List<RecordFieldDeclaration> fields;

	public Variant() {
		this.constants = new ArrayList<Constant>();
		this.fields = new ArrayList<RecordFieldDeclaration>();
	}

	public List<RecordFieldDeclaration> getFields() {
		return this.fields;
	}

	public List<Constant> getConstants() {
		return this.constants;
	}

	@Override
	public void remove(final Node node) {
		this.constants.remove(node);
	}

	@Override
	protected boolean internalAdd(final Node node) throws InvalidNodeException {
		if (node instanceof Constant) {
			this.constants.add((Constant) node);
			return true;
		}
		if (node instanceof RecordFieldDeclaration) {
			this.fields.add((RecordFieldDeclaration) node);
			return true;
		}
		return false;
	}

	@Override
	protected void contributeDetails(final List<Node> details) {
		details.addAll(this.constants);
		details.addAll(this.fields);
	}

	@Override
	public void accept(final DelphiSyntaxVisitor visitor) {
		visitor.visitVariant(this);
	}

	@Override
	public String toString() {
		return "Variant";
	}

}
