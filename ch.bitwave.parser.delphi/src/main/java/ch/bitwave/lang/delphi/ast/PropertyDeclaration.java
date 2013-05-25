package ch.bitwave.lang.delphi.ast;

import java.util.ArrayList;
import java.util.List;

public class PropertyDeclaration extends MemberDeclaration {

	private PropertyAccessDeclaration accessDeclaration;
	private Type type;
	private ReadAccessorDeclaration readAccessor;
	private WriteAccessorDeclaration writeAccessor;
	private List<PropertyModifier> modifiers;

	public PropertyDeclaration(final AccessModifierKind access) {
		super(access);
		this.modifiers = new ArrayList<PropertyModifier>();
	}

	public List<PropertyModifier> getModifiers() {
		return this.modifiers;
	}

	public PropertyAccessDeclaration getAccessDeclaration() {
		return this.accessDeclaration;
	}

	public void setAccessDeclaration(final PropertyAccessDeclaration accessDeclaration) {
		this.accessDeclaration = accessDeclaration;
	}

	public Type getType() {
		return this.type;
	}

	public void setType(final Type type) {
		this.type = type;
	}

	public ReadAccessorDeclaration getReadAccessor() {
		return this.readAccessor;
	}

	public void setReadAccessor(final ReadAccessorDeclaration readAccessor) {
		this.readAccessor = readAccessor;
	}

	public WriteAccessorDeclaration getWriteAccessor() {
		return this.writeAccessor;
	}

	public void setWriteAccessor(final WriteAccessorDeclaration writeAccessor) {
		this.writeAccessor = writeAccessor;
	}

	@Override
	public void remove(final Node node) {
		super.remove(node);
		if (this.accessDeclaration == node) {
			this.accessDeclaration = null;
		} else if (this.type == node) {
			this.type = null;
		} else if (this.readAccessor == node) {
			this.readAccessor = null;
		} else if (this.writeAccessor == node) {
			this.writeAccessor = null;
		} else {
			this.modifiers.remove(node);
		}
	}

	@Override
	protected boolean internalAdd(final Node node) throws InvalidNodeException {
		if (super.internalAdd(node))
			return true;
		if (node instanceof PropertyModifier) {
			this.modifiers.add((PropertyModifier) node);
			return true;
		}
		if (node instanceof PropertyAccessDeclaration) {
			this.accessDeclaration = (PropertyAccessDeclaration) node;
			return true;
		}
		if (node instanceof Type) {
			this.type = (Type) node;
			return true;
		}
		if (node instanceof ReadAccessorDeclaration) {
			this.readAccessor = (ReadAccessorDeclaration) node;
			return true;
		}
		if (node instanceof WriteAccessorDeclaration) {
			this.writeAccessor = (WriteAccessorDeclaration) node;
			return true;
		}
		return false;
	}

	@Override
	protected void contributeDetails(final List<Node> details) {
		super.contributeDetails(details);
		contribute(details, this.accessDeclaration, this.type, this.readAccessor,
				this.writeAccessor);
	}

	@Override
	public void accept(final DelphiSyntaxVisitor visitor) {
		visitor.visitPropertyDeclaration(this);
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("PropertyDeclaration [identifier=").append(this.getIdentifier()).append("]");
		return builder.toString();
	}

}
