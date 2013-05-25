package ch.bitwave.lang.delphi.ast;

import java.util.ArrayList;
import java.util.List;

public abstract class ProceduralDeclaration extends IdentifiedNode implements Parameterized {

	private List<ParameterDeclaration> parameters;
	private List<FunctionDirective> modifiers;

	public ProceduralDeclaration() {
		this.modifiers = new ArrayList<FunctionDirective>();
		this.parameters = new ArrayList<ParameterDeclaration>();
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
		super.remove(node);
		this.modifiers.remove(node);
		this.parameters.remove(node);
	}

	@Override
	protected boolean internalAdd(final Node node) throws InvalidNodeException {
		if (super.internalAdd(node))
			return true;
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
		super.contributeDetails(details);
		details.addAll(this.parameters);
		details.addAll(this.modifiers);
	}

	@Override
	public boolean hasParameters() {
		return !this.parameters.isEmpty();
	}

	@Override
	public void addParameter(final ParameterDeclaration decl) {
		this.parameters.add(decl);
	}

	@Override
	public boolean isProcedural() {
		return true;
	}

}
