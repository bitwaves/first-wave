package ch.bitwave.mim.m2.core;

import org.apache.commons.lang.StringUtils;

/**
 * A naming strategy which compares names exactly.
 */
public class ExactNamingStrategy implements NamingStrategy {

	private static final ExactNamingStrategy INSTANCE = new ExactNamingStrategy();

	@Override
	public boolean isSameName(final String firstName, final String secondName) {
		return StringUtils.equals(firstName, secondName);
	}

	public static NamingStrategy getInstance() {
		return INSTANCE;
	}

}
