package ch.bitwave.platform.prose;

import java.io.Serializable;

import javax.annotation.Nonnull;

/**
 * Implements the shorthand methods to avoid unnecessary repetition in the
 * concrete builder implementations.
 * 
 */
public abstract class AbstractProseBuilder implements IProseBuilder, Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	public IProseBuilder appendLine(final String text) {
		return append(text).newLine();
	}

	@Override
	public IProseBuilder append(@Nonnull final String format, final Object... args) {
		append(String.format(format, args));
		return this;
	}

}
