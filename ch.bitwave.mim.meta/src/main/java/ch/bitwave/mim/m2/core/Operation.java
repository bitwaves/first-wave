package ch.bitwave.mim.m2.core;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * <p>
 * An operation is owned by a class and may be invoked in the context of objects
 * that are instances of that class. It is a typed element and a multiplicity
 * element.
 * </p>
 * 
 * <p>
 * An operation belongs to a class. It is possible to invoke an operation on any
 * object that is directly or indirectly an instance of the class. Within such
 * an invocation the execution context includes this object and the values of
 * the parameters. The type of the operation, if any, is the type of the result
 * returned by the operation, and the multiplicity is the multiplicity of the
 * result. An operation can be associated with a set of types that represent
 * possible exceptions that the operation may raise.
 * </p>
 */
public class Operation extends Feature implements TypedElement, MultiplicityElement, Dependent {

	private MimClass owningClass;
	private Multiplicity multiplicity;
	private List<Parameter> ownedParameters;
	private List<MimType> raisedExceptions;
	private Dependencies dependencies;

	public Operation() {
	}

	public List<Parameter> getOwnedParameters() {
		return this.ownedParameters;
	}

	public List<MimType> getRaisedExceptions() {
		return this.raisedExceptions;
	}

	@Override
	public MimElement getOwner() {
		return this.owningClass;
	}

	@Override
	public MimType getType() {
		return null;
	}

	@Override
	public void setType(final MimType type) {

	}

	@Override
	public Multiplicity getMultiplicity() {
		return this.multiplicity;
	}

	@Override
	public boolean depdendsOnIdentifier(final String name) {
		if (this.dependencies == null) {
			return false;
		}
		return this.dependencies.depdendsOnIdentifier(name);
	}

	@Override
	public void addIdentifierDependency(final String name) {
		if (this.dependencies == null) {
			this.dependencies = new Dependencies();
		}
		this.dependencies.addIdentifierDependency(name);
	}

	public Set<String> getIdentifierDependencies() {
		if (this.dependencies == null) {
			return new HashSet<String>();
		}
		return this.dependencies.getIdentifierDependencies();
	}

}
