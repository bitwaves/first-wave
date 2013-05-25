package ch.bitwave.lang.delphi.ast;

import java.util.ArrayList;
import java.util.List;

public abstract class InvokableImplementation extends Node implements Parameterized {

	private List<ParameterDeclaration> parameters;
	private CodeBlock codeBlock;

	public InvokableImplementation() {
		this.parameters = new ArrayList<ParameterDeclaration>();
	}

	public CodeBlock getCodeBlock() {
		return this.codeBlock;
	}

	public void setCodeBlock(final CodeBlock codeBlock) {
		this.codeBlock = codeBlock;
	}

	public void setParameters(final List<ParameterDeclaration> parameters) {
		this.parameters = parameters;
	}

	@Override
	public void addParameter(final ParameterDeclaration param) {
		this.parameters.add(param);
	}

	@Override
	public List<ParameterDeclaration> getParameters() {
		return getDetailsOfType(ParameterDeclaration.class);
	}

	@Override
	public void remove(final Node node) {
		if (this.codeBlock == node) {
			this.codeBlock = null;
		} else {
			this.parameters.remove(node);
		}
	}

	@Override
	protected boolean internalAdd(final Node node) throws InvalidNodeException {
		if (node instanceof CodeBlock) {
			this.codeBlock = (CodeBlock) node;
			return true;
		}
		if (node instanceof ParameterDeclaration) {
			this.parameters.add((ParameterDeclaration) node);
			return true;
		}
		return false;
	}

	@Override
	protected void contributeDetails(final List<Node> details) {
		details.addAll(this.parameters);
		contribute(details, this.codeBlock);
	}

	@Override
	public boolean hasParameters() {
		return !this.parameters.isEmpty();
	}

}
