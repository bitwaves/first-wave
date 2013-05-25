package ch.bitwave.platform.prose;

import java.util.Stack;

import javax.annotation.Nonnull;

import org.apache.commons.lang.StringUtils;

import ch.bitwave.platform.codestyle.DBC;

/**
 * A prose builder for writing HTML text.
 */
@SuppressWarnings("nls")
public class HTMLProseBuilder extends AbstractProseBuilder {
	private static final long serialVersionUID = 1L;
	private Stack<String> elementStack;
	private StringBuilder sb;

	/**
	 * Creates a new prose builder for writing HTML text.
	 */
	public HTMLProseBuilder() {
		this.sb = new StringBuilder();
		this.elementStack = new Stack<String>();
	}

	/**
	 * 
	 */
	private void openParagraph() {
		if (!isInHTML()) {
			openHTML();
		}
		openElement("p");
	}

	/**
	 * 
	 */
	private void openHTML() {
		openElement("html");
	}

	/**
	 * @param elementName
	 */
	private void openElement(final String elementName) {
		this.elementStack.push(elementName);
		this.sb.append("<").append(elementName).append(">");
	}

	/**
	 * @return
	 */
	private boolean isInHTML() {
		return isInElement("html");
	}

	/**
	 * @return
	 */
	private boolean isInParagraph() {
		return isInElement("p");
	}

	/**
	 * @param string
	 * @return
	 */
	private boolean isInElement(final String elementName) {
		DBC.PRE.assertFalse("The element name must not be empty.", StringUtils.isEmpty(elementName));
		if (this.elementStack.isEmpty()) {
			return false;
		}
		for (int i = this.elementStack.size() - 1; i >= 0; i--) {
			String cur = this.elementStack.get(i);
			if (elementName.equals(cur)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public IProseBuilder append(final String text) {
		ensureParagraph();
		this.sb.append(text);
		return this;
	}

	/**
	 * Makes sure that the cursor is currently within an open paragraph.
	 */
	private void ensureParagraph() {
		if (!isInParagraph()) {
			openParagraph();
		}
	}

	@Override
	public IProseBuilder define(final String name, final Object value) {
		newPara();
		openElement("b");
		append(name);
		append(":");
		closeElement();
		append(" ");
		if (value != null) {
			append(value.toString());
		}
		return this;
	}

	@Override
	public IProseBuilder newLine() {
		ensureParagraph();
		append("<br/>");
		return this;
	}

	@Override
	public IProseBuilder newPara() {
		if (isInParagraph()) {
			closeParagraph();
		}
		openParagraph();
		return this;
	}

	/**
	 * 
	 */
	private void closeParagraph() {
		collapseElementStackUpTo("p");
	}

	@Override
	public boolean hasText() {
		return this.sb.length() > 0;
	}

	@Override
	public String getText() {
		collapseElementStack();
		return this.sb.toString();
	}

	/**
	 * 
	 */
	private void collapseElementStack() {
		while (!this.elementStack.isEmpty()) {
			closeElement();
		}
	}

	/**
	 * Collapses the element stack up to and including the element with the
	 * given name. If this element is not on the stack, the entire stack will be
	 * collapsed.
	 * 
	 * @param lastElementToClose
	 *            the element name.
	 */
	private void collapseElementStackUpTo(@Nonnull final String lastElementToClose) {
		DBC.PRE.assertNotNull("The element name must not be null.", lastElementToClose);
		while (!this.elementStack.isEmpty()) {
			String elementName = closeElement();
			if (lastElementToClose.equals(elementName)) {
				break;
			}
		}
	}

	/**
	 * @param pop
	 */
	private String closeElement() {
		String elementName = this.elementStack.pop();
		this.sb.append("</");
		this.sb.append(elementName);
		this.sb.append(">");
		return elementName;
	}
}
