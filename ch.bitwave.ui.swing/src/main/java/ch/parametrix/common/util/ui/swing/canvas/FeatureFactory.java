package ch.parametrix.common.util.ui.swing.canvas;

import java.util.HashMap;
import java.util.Map;

import ch.bitwave.platform.codestyle.DBC;
import ch.parametrix.common.util.ui.swing.contracts.Feature;

/**
 * A factory capable of returning a feature instance for a particular model
 * element class, which means that the factory determines how a model element
 * will be represented on the canvas. The factory must be populated before use.
 * 
 */
public class FeatureFactory {
	private Map<Class<? extends Object>, Class<? extends Feature>> featureClassByComponentClass = new HashMap<Class<? extends Object>, Class<? extends Feature>>();

	/**
	 * Creates a new, empty feature factory.
	 */
	public FeatureFactory() {
	}

	public Feature createFeatureForElement(final Object component) {
		DBC.PRE.assertNotNull(component, "component");
		final Class<? extends Object> cc = component.getClass();
		final Class<? extends Feature> fc = this.featureClassByComponentClass.get(cc);
		if (fc == null) {
			throw new RuntimeException(String.format(
					"Object type %s is not mapped to a corresponding feature.", cc.getSimpleName()));
		}
		Feature feature;
		try {
			feature = fc.getConstructor(Object.class).newInstance(component);
			return feature;
		} catch (final Exception e) {
			throw new RuntimeException(
					String.format("Failed to create feature for element %s due to: %s", component,
							e.getMessage()), e);
		}
	}

	/**
	 * Registers the given feature class for the given element class. The
	 * factory will thus represent all elements of the given class through
	 * instances of the given feature class. through this factory.
	 * 
	 * @param elementClass
	 *            the model element to represent.
	 * @param featureClass
	 *            the feature class to represent the element with.
	 */
	public void put(final Class<? extends Object> elementClass,
			final Class<? extends Feature> featureClass) {
		this.featureClassByComponentClass.put(elementClass, featureClass);
	}
}
