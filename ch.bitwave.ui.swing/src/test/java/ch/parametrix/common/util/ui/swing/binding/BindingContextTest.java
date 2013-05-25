package ch.parametrix.common.util.ui.swing.binding;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.List;

import javax.swing.JComponent;
import javax.swing.JPanel;

import org.junit.Test;

/**
 * 
 */
public class BindingContextTest {
	@Test
	public void shouldCreate() {
		BindingContext context = new BindingContext();
		assertEquals(0, context.getSize());
		assertTrue(context.getIssues().isEmpty());
	}

	@Test
	public void shouldAddBinding() {
		BindingContext context = new BindingContext();
		context.add(new SampleBinding());
		assertEquals(1, context.getSize());
	}

	@Test
	public void shouldGetIssues() {
		BindingContext context = new BindingContext();
		SampleBinding binding = new SampleBinding();
		context.add(binding);
		List<String> initialIssues = context.getIssues();
		assertFalse(context.hasIssues());
		assertEquals(0, initialIssues.size());
		binding.setIssue("Problemo!");
		List<String> laterIssues = context.getIssues();
		assertTrue(context.hasIssues());
		assertEquals(0, initialIssues.size());
		assertEquals(1, laterIssues.size());
		assertEquals("Problemo!", laterIssues.get(0));
	}

	@Test
	public void shouldGetIssueMessageWithoutPreamble() {
		BindingContext context = new BindingContext();
		SampleBinding binding = new SampleBinding();
		context.add(binding);
		binding.setIssue("Problemo!");
		assertEquals("Problemo!\n", context.getIssueMessage(null));
	}

	@Test
	public void shouldGetIssueMessageWithPreamble() {
		BindingContext context = new BindingContext();
		SampleBinding binding = new SampleBinding();
		context.add(binding);
		binding.setIssue("Problemo!");
		assertEquals("There are issues:\nProblemo!\n", context.getIssueMessage("There are issues:"));
	}

	@Test
	public void shouldClear() {
		BindingContext context = new BindingContext();
		SampleBinding binding = new SampleBinding();
		context.add(binding);
		assertFalse(binding.isUnbound());
		context.clear();
		assertEquals(0, context.getSize());
		assertTrue("The binding context must unbind its bindings on clear.", binding.isUnbound());
	}

	@Test
	public void shouldUnbind() {
		BindingContext context = new BindingContext();
		JComponent parent = new JPanel();
		JComponent detailParent = new JPanel();
		parent.add(detailParent);
		SampleBinding binding1 = new SampleBinding(parent);
		context.add(binding1);
		SampleBinding binding2 = new SampleBinding();
		context.add(binding2);
		SampleBinding binding3 = new SampleBinding(detailParent);
		context.add(binding3);
		assertEquals(3, context.getSize());
		context.unbind(parent);
		assertEquals(2, context.getSize());
		assertTrue("The binding context must unbind the binding residing in the parent.",
				binding1.isUnbound());
		assertFalse(
				"The binding context must NOT unbind those bindings not belonging to the parent.",
				binding2.isUnbound());
		assertFalse(
				"The binding context must NOT unbind bindings indirectly residing in the parent, because it does not have an understanding of UI hierarchies.",
				binding3.isUnbound());
	}
}
