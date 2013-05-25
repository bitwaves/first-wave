package ch.bitwave.lang.delphi.ast;

import java.util.ArrayList;
import java.util.List;

public class RecordType extends Type {

	private List<RecordFieldDeclaration> declaredFields;

	public RecordType() {
		this.declaredFields = new ArrayList<RecordFieldDeclaration>();
	}

	@Override
	public void remove(final Node node) {
		this.declaredFields.remove(node);
	}

	@Override
	protected boolean internalAdd(final Node node) throws InvalidNodeException {
		if (node instanceof RecordFieldDeclaration) {
			this.declaredFields.add((RecordFieldDeclaration) node);
			return true;
		}
		return false;
	}

	@Override
	protected void contributeDetails(final List<Node> details) {
		details.addAll(this.declaredFields);
	}

	@Override
	public void accept(final DelphiSyntaxVisitor visitor) {
		visitor.visitRecordType(this);
	}

	@Override
	public String toString() {
		return "RecordType";
	}

}
