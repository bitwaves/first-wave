package ch.parametrix.common.util.ui.swing.contracts;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;

import ch.bitwave.platform.prose.IProseBuilder;
import ch.bitwave.platform.prose.PlainProseBuilder;

/**
 * 
 */
public class AbstractChangeManagerTest {
	private IProseBuilder model;

	@Before
	public void setUp() {
		this.model = new PlainProseBuilder();
	}

	@Test
	public void shouldCreate() {
		AbstractChangeManager<IProseBuilder> cham = new SampleChangeManager<IProseBuilder>(
				this.model);
		assertTrue(cham.isInvalidated());
		assertFalse(cham.canUndo());
		assertFalse(cham.canRedo());
	}

	@Test
	public void shouldExceptOnUndoOnEmptyStack() {
		AbstractChangeManager<IProseBuilder> cham = new SampleChangeManager<IProseBuilder>(
				this.model);
		try {
			cham.undo();
			fail("Exception excepted.");
		} catch (RuntimeException e) {
			assertEquals("There is nothing to undo.", e.getMessage());
		}
	}

	@Test
	public void shouldExceptOnRedoOnEmptyStack() {
		AbstractChangeManager<IProseBuilder> cham = new SampleChangeManager<IProseBuilder>(
				this.model);
		try {
			cham.redo();
			fail("Exception excepted.");
		} catch (RuntimeException e) {
			assertEquals("There is nothing to redo.", e.getMessage());
		}
	}

	@Test
	public void shouldAddChanges() {
		this.model.append("Prepare bowl\n");
		AbstractChangeManager<IProseBuilder> cham = new SampleChangeManager<IProseBuilder>(
				this.model);
		cham.addChange(new SampleChange("Mix butter and sugar"));
		assertTrue(cham.canUndo());
		assertFalse(cham.canRedo());
		cham.addChange(new SampleChange("Add eggs"));
		cham.addChange(new SampleChange("Add flour"));
		assertTrue(cham.canUndo());
		assertFalse(cham.canRedo());
		IProseBuilder cur = cham.getModel();
		assertEquals("Current model must reflect changes.",
				"Prepare bowl\nMix butter and sugar\nAdd eggs\nAdd flour\n", cur.getText());
		assertEquals("Baseline model must not be affected.", "Prepare bowl\n", this.model.getText());
	}

	@Test
	public void shouldUndoAndRedoChange() {
		AbstractChangeManager<IProseBuilder> cham = new SampleChangeManager<IProseBuilder>(
				this.model);
		cham.addChange(new SampleChange("Bake"));
		IProseBuilder cur = cham.getModel();
		assertEquals("Bake\n", cur.getText());
		cham.undo();
		cur = cham.getModel();
		assertEquals("", cur.getText());
		cham.redo();
		cur = cham.getModel();
		assertEquals("Bake\n", cur.getText());
	}

	@Test
	public void shouldCollapseOnAddAfterUndo() {
		AbstractChangeManager<IProseBuilder> cham = new SampleChangeManager<IProseBuilder>(
				this.model);
		cham.addChange(new SampleChange("Mix butter and sugar"));
		cham.addChange(new SampleChange("Add eggs"));
		cham.addChange(new SampleChange("Bake"));
		cham.undo();
		cham.undo();
		cham.addChange(new SampleChange("Add flour"));
		IProseBuilder cur = cham.getModel();
		assertEquals("Mix butter and sugar\nAdd flour\n", cur.getText());
		assertFalse(cham.canRedo());
	}
}
