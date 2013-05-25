package ch.parametrix.common.util.ui.swing.canvas;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;

import ch.parametrix.common.util.ui.swing.clap.SampleFeature;
import ch.parametrix.common.util.ui.swing.clap.SampleFeature2;
import ch.parametrix.common.util.ui.swing.contracts.ICanvasFeature;

/**
 * 
 */
public class FeatureProviderTest {
	@Test
	public void shouldCreate() {
		FeatureProvider provider = new FeatureProvider();
		assertTrue(provider.getCanvasFeatures().isEmpty());
		assertTrue(provider.getSelectedFeatures().isEmpty());
		assertNotNull(provider.getFeatureFactory());
		assertEquals(100, provider.getGridSize());
	}

	@Test
	public void shouldRegisterFeature() {
		FeatureProvider provider = new FeatureProvider();
		SampleFeature f1 = new SampleFeature("Hello");
		provider.registerFeature(f1);
		assertEquals(1, provider.getCanvasFeatures().size());
		assertSame(f1, provider.getCanvasFeatures().get(0));
	}

	@Test
	public void shouldSelectRegisteredFeatureOnEmptySelection() {
		FeatureProvider provider = new FeatureProvider();
		SampleFeature f1 = new SampleFeature("Hello");
		provider.registerFeature(f1);
		assertEquals(0, provider.getSelectedFeatures().size());
		provider.selectFeature(f1);
		assertEquals(1, provider.getCanvasFeatures().size());
		assertEquals(1, provider.getSelectedFeatures().size());
		assertSame(f1, provider.getSelectedFeatures().get(0));
	}

	@Test
	public void shouldSelectRegisteredFeatureOnExistingSingleSelection() {
		FeatureProvider provider = new FeatureProvider();
		SampleFeature f1 = new SampleFeature("Hello");
		SampleFeature f2 = new SampleFeature("Bye");
		provider.registerFeature(f1);
		provider.registerFeature(f2);
		provider.selectFeature(f1);
		provider.selectFeature(f2);
		assertEquals(2, provider.getCanvasFeatures().size());
		assertEquals(1, provider.getSelectedFeatures().size());
		assertSame(f2, provider.getSelectedFeatures().get(0));
	}

	@Test
	public void shouldSelectRegisteredFeatureOnExistingMultiSelection() {
		FeatureProvider provider = new FeatureProvider();
		SampleFeature f1 = new SampleFeature("Hello");
		SampleFeature f2 = new SampleFeature("olleh");
		SampleFeature f3 = new SampleFeature("Bye");
		provider.registerFeature(f1);
		provider.registerFeature(f2);
		provider.registerFeature(f3);
		provider.selectFeature(f1);
		provider.addFeatureToSelection(f2);
		assertEquals(2, provider.getSelectedFeatures().size());
		provider.selectFeature(f3);
		assertEquals(3, provider.getCanvasFeatures().size());
		assertEquals(1, provider.getSelectedFeatures().size());
		assertSame(f3, provider.getSelectedFeatures().get(0));
	}

	@Test
	public void shouldSelectFeatureByType() {
		FeatureProvider provider = new FeatureProvider();
		SampleFeature f1 = new SampleFeature("Hello");
		SampleFeature2 f2 = new SampleFeature2("Ehlo");
		provider.registerFeature(f1);
		provider.registerFeature(f2);
		provider.selectFeature(f1);
		provider.addFeatureToSelection(f2);
		{
			List<ICanvasFeature> typedSel = provider
					.getSelectedFeaturesOfType(SampleFeature2.class);
			assertEquals(
					"Only the second feature is of type SampleFeature2, so the result must only contain f2.",
					1, typedSel.size());
			assertSame(f2, typedSel.get(0));
		}
		{
			List<ICanvasFeature> typedSel = provider.getSelectedFeaturesOfType(SampleFeature.class);
			assertEquals(
					"Both features are of type SampleFeature, so result must contain both f1 and f2.",
					2, typedSel.size());
			assertSame(f1, typedSel.get(0));
			assertSame(f2, typedSel.get(1));
		}
	}

	@Test
	public void shouldNotSelectUnregisteredFeature() {
		FeatureProvider provider = new FeatureProvider();
		SampleFeature f1 = new SampleFeature("Hello");
		assertEquals(0, provider.getSelectedFeatures().size());
		try {
			provider.selectFeature(f1);
		} catch (RuntimeException e) {
			assertEquals(
					"Feature SampleFeature [element=Hello, anchor=WC(0.00, 0.00), size=WD(500.00, 100.00)] does not belong to this provider.",
					e.getMessage());
		}
		assertEquals(0, provider.getCanvasFeatures().size());
		assertEquals(0, provider.getSelectedFeatures().size());
	}

}
