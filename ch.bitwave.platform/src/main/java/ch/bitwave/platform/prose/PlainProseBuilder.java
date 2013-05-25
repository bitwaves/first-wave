package ch.bitwave.platform.prose;

import javax.annotation.Nonnull;

import org.apache.commons.lang.StringUtils;

/**
 * A utility class for building plain text prose. Use this if a string builder
 * is not comfy enough.
 * 
 */
public class PlainProseBuilder extends AbstractProseBuilder {
	private static final long serialVersionUID = 1L;
	private StringBuilder sb;

	/**
	 * Creates a new plain-text prose builder instance.
	 */
	public PlainProseBuilder() {
		this.sb = new StringBuilder();
	}

	@Override
	public IProseBuilder append(@Nonnull final String text) {
		this.sb.append(text);
		return this;
	}

	/**
	 * <p>
	 * {@inheritDoc}
	 * </p>
	 * <p>
	 * In plain-text prose, the name and value are separated by ": "
	 * </p>
	 */
	@Override
	public IProseBuilder define(@Nonnull final String name, @Nonnull final Object value) {
		String text = value == null ? StringUtils.EMPTY : value.toString();
		return append("%s: %s\n", name, text); //$NON-NLS-1$
	}

	/**
	 * Breaks the line.
	 * 
	 * @return this builder.
	 */
	@Override
	public IProseBuilder newLine() {
		this.sb.append('\n');
		return this;
	}

	/**
	 * Breaks the paragraph (two line breaks).
	 * 
	 * @return this builder.
	 */
	@Override
	public IProseBuilder newPara() {
		this.sb.append("\n\n"); //$NON-NLS-1$
		return this;
	}

	/**
	 * Tells whether the builder already has accumulated some text.
	 * 
	 * @return true if the builder contains text, false if not.
	 */
	@Override
	public boolean hasText() {
		return this.sb.length() > 0;
	}

	/**
	 * Returns the text that has so far been accumulated in the builder.
	 * 
	 * @return the prose text.
	 */
	@Override
	public String getText() {
		return this.sb.toString();
	}

}
