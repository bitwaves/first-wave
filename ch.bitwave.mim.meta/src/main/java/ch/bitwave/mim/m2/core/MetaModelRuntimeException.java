package ch.bitwave.mim.m2.core;

public class MetaModelRuntimeException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public MetaModelRuntimeException() {
		super();
	}

	public MetaModelRuntimeException(final String message, final Throwable cause) {
		super(message, cause);
	}

	public MetaModelRuntimeException(final String message) {
		super(message);
	}

	public MetaModelRuntimeException(final Throwable cause) {
		super(cause);
	}

}
