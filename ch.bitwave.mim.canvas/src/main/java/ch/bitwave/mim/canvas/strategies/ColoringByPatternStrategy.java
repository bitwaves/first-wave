package ch.bitwave.mim.canvas.strategies;

import java.awt.Color;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class ColoringByPatternStrategy extends ColoringStrategy {

	private Color matchColor;
	private Matcher memberNameMatcher;

	public ColoringByPatternStrategy(final Color matchColor, final Pattern namePattern) {
		this.matchColor = matchColor;
		this.memberNameMatcher = namePattern.matcher("");
	}

	public Color getMatchColor() {
		return this.matchColor;
	}

	public void setMatchColor(final Color matchColor) {
		this.matchColor = matchColor;
	}

	public Matcher getMemberNameMatcher() {
		return this.memberNameMatcher;
	}

	public void setMemberNameMatcher(final Matcher memberNameMatcher) {
		this.memberNameMatcher = memberNameMatcher;
	}

	@Override
	public final Color getColor(final Object element) {
		if (isMatchingElement(this.memberNameMatcher, element)) {
			return this.matchColor;
		}
		return Color.WHITE;
	}

	protected abstract boolean isMatchingElement(final Matcher matcher, final Object element);
}
