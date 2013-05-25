package ch.bitwave.lang.delphi.ast;

import java.util.ArrayList;
import java.util.List;

public abstract class MethodDeclaration extends MemberDeclaration implements Parameterized {

	private List<ParameterDeclaration> parameters;
	private List<MethodDirective> directives;

	public MethodDeclaration(final AccessModifierKind accessKind) {
		super(accessKind);
		this.directives = new ArrayList<MethodDirective>();
		this.parameters = new ArrayList<ParameterDeclaration>();
	}

	@Override
	public List<ParameterDeclaration> getParameters() {
		return this.parameters;
	}

	public List<MethodDirective> getDirectives() {
		return this.directives;
	}

	@Override
	public void remove(final Node node) {
		super.remove(node);
		this.directives.remove(node);
		this.parameters.remove(node);
	}

	@Override
	protected boolean internalAdd(final Node node) throws InvalidNodeException {
		if (super.internalAdd(node))
			return true;
		if (node instanceof MethodDirective) {
			this.directives.add((MethodDirective) node);
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
		details.addAll(this.directives);
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
	public boolean isAbstract() {
		for (MethodDirective directive : this.directives) {
			if (MethodDirectiveKind.ABSTRACT.equals(directive.getKind()))
				return true;
		}
		return false;
	}

}
