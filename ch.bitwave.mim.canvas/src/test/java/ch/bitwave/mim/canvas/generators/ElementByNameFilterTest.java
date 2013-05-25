package ch.bitwave.mim.canvas.generators;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import ch.bitwave.mim.m2.core.MimClass;
import ch.bitwave.mim.m2.core.MimElement;
import ch.bitwave.mim.m2.core.NamedElement;

public class ElementByNameFilterTest {

	@Test
	public void shouldAcceptMatchingCaseInsensitive() {
		ElementByNameFilter filter = new ElementByNameFilter("tpomcontroller", false);
		assertTrue(filter.accepts(createElement("TPOMController")));
		assertFalse(filter.accepts(createElement("TFooController")));
	}

	@Test
	public void shouldAcceptMatchingCaseSensitive() {
		ElementByNameFilter filter = new ElementByNameFilter("TPOMController", true);
		assertTrue(filter.accepts(createElement("TPOMController")));
		assertFalse(filter.accepts(createElement("TpomController")));
	}

	private MimElement createElement(final String name) {
		NamedElement result = new MimClass();
		result.setName(name);
		return result;
	}
}
