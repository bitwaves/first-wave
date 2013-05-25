package ch.bitwave.mim.canvas.strategies;

import java.awt.Color;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import ch.bitwave.mim.m2.core.MimClass;

/**
 * Draws classes introducing features matching a given pattern in a different
 * color.
 */
public class ColoringByFeatureIntroductionStrategy extends ColoringByPatternStrategy {

	public ColoringByFeatureIntroductionStrategy(final Color matchColor, final Pattern namePattern) {
		super(matchColor, namePattern);
	}

	@Override
	protected boolean isMatchingElement(final Matcher matcher, final Object element) {
		if (element instanceof MimClass) {
			MimClass mc = (MimClass) element;
			if (mc.hasFeatureMatching(matcher)) {
				return true;
			}
		}
		return false;
	}

}
