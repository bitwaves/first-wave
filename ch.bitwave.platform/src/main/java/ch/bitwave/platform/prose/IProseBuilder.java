package ch.bitwave.platform.prose;

import javax.annotation.Nonnull;

/**
 * An interface for creating structured text while separating content from
 * representation.
 */
public interface IProseBuilder {

	/**
	 * Appends the given text.
	 * 
	 * @param text
	 *            the text to append.
	 * @return this builder.
	 */
	IProseBuilder append(@Nonnull final String text);

	/**
	 * Appends a formatted string, using the arguments provided. This is simply
	 * a shorthand for <code>append(String.format(...))</code>.
	 * 
	 * @param format
	 * @param args
	 * @return this builder.
	 */
	IProseBuilder append(@Nonnull final String format, final Object... args);

	/**
	 * Appends a name/value pair followed by a line break. Name and value are
	 * separated by ": ".
	 * 
	 * @param name
	 *            the attribute name.
	 * @param value
	 *            the attribute value.
	 * @return this builder.
	 */
	public IProseBuilder define(@Nonnull final String name, @Nonnull final Object value);

	/**
	 * Appends the given text, followed by a newline. Shorthand for
	 * <code>append(...).newLine()</code>.
	 * 
	 * @param text
	 *            the line of text to append.
	 * @return this builder.
	 */
	public IProseBuilder appendLine(final String text);

	/**
	 * Breaks the line.
	 * 
	 * @return this builder.
	 */
	public IProseBuilder newLine();

	/**
	 * Breaks the paragraph (two line breaks).
	 * 
	 * @return this builder.
	 */
	public IProseBuilder newPara();

	/**
	 * Tells whether the builder already has accumulated some text.
	 * 
	 * @return true if the builder contains text, false if not.
	 */
	public boolean hasText();

	/**
	 * Returns the text that has so far been accumulated in the builder.
	 * 
	 * @return the prose text.
	 */
	public String getText();

}
