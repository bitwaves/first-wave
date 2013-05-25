package ch.bitwave.lang.delphi.ast;

import java.util.ArrayList;
import java.util.List;

public abstract class InvokableType extends Type implements Parameterized {
	private List<ParameterDeclaration> parameters;
	private boolean ofObject;
	private List<FunctionDirective> modifiers;

	public InvokableType() {
		this.parameters = new ArrayList<ParameterDeclaration>();
		this.modifiers = new ArrayList<FunctionDirective>();
	}

	public boolean isOfObject() {
		return this.ofObject;
	}

	public void setOfObject(final boolean ofObject) {
		this.ofObject = ofObject;
	}

	@Override
	public List<ParameterDeclaration> getParameters() {
		return this.parameters;
	}

	public List<FunctionDirective> getModifiers() {
		return this.modifiers;
	}

	@Override
	public void remove(final Node node) {
		this.parameters.remove(node);
		this.modifiers.remove(node);
	}

	@Override
	protected boolean internalAdd(final Node node) throws InvalidNodeException {
		if (node instanceof FunctionDirective) {
			this.modifiers.add((FunctionDirective) node);
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
		details.addAll(this.modifiers);
	}

	@Override
	public String toString() {
		return "ProcedureType";
	}

	@Override
	public boolean hasParameters() {
		return !this.parameters.isEmpty();
	}

	@Override
	public void addParameter(final ParameterDeclaration param) {
		this.parameters.add(param);
	}

}
