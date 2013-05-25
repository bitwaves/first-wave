package ch.bitwave.mim.m2.core;

import org.apache.commons.lang.StringUtils;

public class Profile extends MimPackage implements NamingStrategy {

	@Override
	public boolean isSameName(final String firstName, final String secondName) {
		return StringUtils.equals(firstName, secondName);
	}

}
