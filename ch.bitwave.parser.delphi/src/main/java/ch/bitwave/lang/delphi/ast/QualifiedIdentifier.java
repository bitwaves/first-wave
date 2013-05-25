package ch.bitwave.lang.delphi.ast;

import java.util.List;

public class QualifiedIdentifier extends Node {

	private Identifier namespaceIdentifier;
	private Identifier objectIdentifier;

	public Identifier getNamespaceIdentifier() {
		return this.namespaceIdentifier;
	}

	public void setNamespaceIdentifier(final Identifier namespaceIdentifier) {
		this.namespaceIdentifier = namespaceIdentifier;
	}

	public Identifier getObjectIdentifier() {
		return this.objectIdentifier;
	}

	public void setObjectIdentifier(final Identifier objectIdentifier) {
		this.objectIdentifier = objectIdentifier;
	}

	public QualifiedIdentifier() {
	}

	public List<Identifier> getIdentifiers() {
		return getDetailsOfType(Identifier.class);
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("QualifiedIdentifier [name: ").append(getName()).append(']');
		return builder.toString();
	}

	public String getName() {
		StringBuilder sb = new StringBuilder();
		sb.append(this.namespaceIdentifier.getName());
		sb.append(".");
		sb.append(this.objectIdentifier.getName());
		return sb.toString();
	}

	@Override
	public void remove(final Node node) {
		if (this.namespaceIdentifier == node) {
			this.namespaceIdentifier = null;
		} else if (this.objectIdentifier == node) {
			this.objectIdentifier = null;
		}
	}

	@Override
	protected boolean internalAdd(final Node node) throws InvalidNodeException {
		if (node instanceof Identifier) {
			if (this.namespaceIdentifier == null) {
				this.namespaceIdentifier = (Identifier) node;
				return true;
			}
			if (this.objectIdentifier == null) {
				this.objectIdentifier = (Identifier) node;
				return true;
			}
		}
		return false;
	}

	@Override
	protected void contributeDetails(final List<Node> details) {
		contribute(details, this.namespaceIdentifier, this.objectIdentifier);
	}

	@Override
	public void accept(final DelphiSyntaxVisitor visitor) {
		visitor.visitQualifiedIdentifier(this);
	}

	public String getNamespaceName() {
		return this.namespaceIdentifier.getName();
	}

	public String getObjectName() {
		return this.objectIdentifier.getName();
	}
}
