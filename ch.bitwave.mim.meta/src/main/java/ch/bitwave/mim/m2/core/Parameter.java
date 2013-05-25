package ch.bitwave.mim.m2.core;

/**
 * <p>
 * A parameter is a typed element that represents a parameter of an operation.
 * </p>
 * 
 * <p>
 * When an operation is invoked, an argument may be passed to it for each
 * parameter. Each parameter has a type and a multiplicity. Every Parameter is
 * associated with an operation, although subclasses of Parameter elsewhere in
 * the UML model do not have to be associated with an operation, hence the 0..1
 * multiplicity.
 * </p>
 */
public class Parameter extends NamedElement implements TypedElement, MultiplicityElement {
	private Operation operation;
	private Multiplicity multiplicity;
	private ParameterDirectionKind direction;

	public ParameterDirectionKind getDirection() {
		return this.direction;
	}

	public void setDirection(final ParameterDirectionKind direction) {
		this.direction = direction;
	}

	public Operation getOperation() {
		return this.operation;
	}

	public void setOperation(final Operation operation) {
		this.operation = operation;
	}

	@Override
	public Multiplicity getMultiplicity() {
		return this.multiplicity;
	}

	@Override
	public MimType getType() {
		return null;
	}

	@Override
	public void setType(final MimType type) {

	}

	@Override
	public MimElement getOwner() {
		return this.operation;
	}

}
