package ch.bitwave.mim.m2.core;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class ClassifierTest {
	@Test
	public void shouldGeneralize() {
		// Set up two classes with a specialization. A Car is a Vehicle.
		MimClass vehicleClass = new MimClass();
		vehicleClass.setName("Vehicle");
		MimClass carClass = new MimClass();
		carClass.setName("Car");
		Generalization gen = new Generalization();
		gen.setGeneral(vehicleClass);
		gen.setSpecific(carClass);
		// Now check the various methods related to generalizations.
		assertTrue(carClass.isSpecializationOf(vehicleClass));
		assertTrue(vehicleClass.isGeneralizationOf(carClass));
		assertFalse(carClass.isGeneralizationOf(vehicleClass));
		assertFalse(vehicleClass.isSpecializationOf(carClass));
		assertFalse(carClass.getSpecifics().contains(vehicleClass));
		assertFalse(vehicleClass.getGenerals().contains(carClass));
	}
}
