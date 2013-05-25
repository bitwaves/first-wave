package ch.bitwave.lang.delphi.ast;

import java.io.File;
import java.util.List;

public class Unit extends IdentifiedNode {
	private InterfaceSection interfaceSection;
	private ImplementationSection implementationSection;
	private CodeBlock body;
	private File sourceLocation;

	public File getSourceLocation() {
		return this.sourceLocation;
	}

	public void setSourceLocation(final File sourceLocation) {
		this.sourceLocation = sourceLocation;
	}

	public InterfaceSection getInterfaceSection() {
		return this.interfaceSection;
	}

	public void setInterfaceSection(final InterfaceSection interfaceSection) {
		this.interfaceSection = interfaceSection;
	}

	public ImplementationSection getImplementationSection() {
		return this.implementationSection;
	}

	public void setImplementationSection(final ImplementationSection implementationSection) {
		this.implementationSection = implementationSection;
	}

	public CodeBlock getBody() {
		return this.body;
	}

	public void setBody(final CodeBlock body) {
		this.body = body;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Unit [name=").append(this.getIdentifier()).append("]");
		return builder.toString();
	}

	@Override
	public void remove(final Node node) {
		super.remove(node);
		if (this.interfaceSection == node) {
			this.interfaceSection = null;
		} else if (this.implementationSection == node) {
			this.implementationSection = null;
		} else if (this.body == node) {
			this.body = null;
		}
	}

	@Override
	protected boolean internalAdd(final Node node) throws InvalidNodeException {
		if (super.internalAdd(node))
			return true;
		if (this.interfaceSection == null) {
			assertNodeType(node, InterfaceSection.class);
			this.interfaceSection = (InterfaceSection) node;
			return true;
		}
		if (this.implementationSection == null) {
			assertNodeType(node, ImplementationSection.class);
			this.implementationSection = (ImplementationSection) node;
			return true;
		}
		if (this.body == null) {
			assertNodeType(node, CodeBlock.class);
			this.body = (CodeBlock) node;
			return true;
		}
		return false;

	}

	@Override
	protected void contributeDetails(final List<Node> details) {
		super.contributeDetails(details);
		contribute(details, this.interfaceSection, this.implementationSection, this.body);
	}

	@Override
	public void accept(final DelphiSyntaxVisitor visitor) {
		visitor.visitUnit(this);
	}

}
