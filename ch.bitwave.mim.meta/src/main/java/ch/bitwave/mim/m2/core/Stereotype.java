package ch.bitwave.mim.m2.core;

public class Stereotype extends MimClass {

	public Profile getProfile() {
		return getOwnerAs(Profile.class);
	}

	public void setProfile(final Profile profile) {
		setOwner(profile);
	}

}
