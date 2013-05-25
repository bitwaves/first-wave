package ch.bitwave.mim.canvas.generators;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import ch.bitwave.mim.m2.core.MimElement;
import ch.bitwave.mim.m2.core.NamedElement;

public class ElementByNameFilter extends ElementFilter {
	private Pattern pattern;
	private Matcher matcher;

	public ElementByNameFilter(final String selectionPattern, final boolean caseSensitive) {
		int flags = caseSensitive ? 0 : Pattern.CASE_INSENSITIVE;
		this.pattern = Pattern.compile(selectionPattern, flags);
		this.matcher = this.pattern.matcher("");
	}

	@Override
	public boolean accepts(final MimElement element) {
		if (!(element instanceof NamedElement)) {
			return false;
		}
		NamedElement ne = (NamedElement) element;
		this.matcher.reset(ne.getName());
		return this.matcher.matches();
	}
}
