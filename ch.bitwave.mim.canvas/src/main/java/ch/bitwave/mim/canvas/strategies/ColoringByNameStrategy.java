package ch.bitwave.mim.canvas.strategies;

import java.awt.Color;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import ch.bitwave.mim.m2.core.MimClass;

/**
 * Colors a node in a different background color if its label matches the name
 * pattern.
 */
public class ColoringByNameStrategy extends ColoringByPatternStrategy {

	public ColoringByNameStrategy(final Color matchColor, final Pattern namePattern) {
		super(matchColor, namePattern);
	}

	@Override
	protected boolean isMatchingElement(final Matcher matcher, final Object element) {
		if (element instanceof MimClass) {
			MimClass mc = (MimClass) element;
			matcher.reset(mc.getName());
			if (matcher.matches()) {
				return true;
			}
		}
		return false;
	}

}
