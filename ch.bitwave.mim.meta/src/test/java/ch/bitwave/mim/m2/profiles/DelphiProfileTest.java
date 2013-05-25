package ch.bitwave.mim.m2.profiles;

import static org.junit.Assert.assertSame;

import org.junit.Test;

import ch.bitwave.mim.m2.core.MimClass;
import ch.bitwave.mim.m2.core.MimPackage;

public class DelphiProfileTest {
	@Test
	public void shouldThreadIdentifiersCaseInsensitively() {
		DelphiProfile profile = new DelphiProfile();
		MimPackage toolbox = profile.createPackage("toolbox");
		MimClass utils = profile.createClass("utils");
		toolbox.addOwnedMember(utils);
		assertSame(utils, toolbox.findMember("Utils"));
	}
}
