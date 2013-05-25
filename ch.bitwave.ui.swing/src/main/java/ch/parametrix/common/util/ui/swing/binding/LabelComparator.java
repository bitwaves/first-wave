package ch.parametrix.common.util.ui.swing.binding;

import java.text.Collator;
import java.util.Comparator;

/**
 * Compares elements of an enumeration binding by the label they provide. Tries
 * to obtain an ISelfDescribing from the elements to compare but defaults to the
 * Object.toString() if not available.
 */
public class LabelComparator implements Comparator<Object> {

	private static final Collator COLLATOR = Collator.getInstance();

	@Override
	public int compare(final Object arg0, final Object arg1) {
		String l0 = getLabel(arg0);
		String l1 = getLabel(arg1);
		return COLLATOR.compare(l0, l1);
	}

	private String getLabel(final Object arg0) {
		if (arg0 == null) {
			return "";
		}
		if (arg0 instanceof ILabelProvider) {
			return ((ILabelProvider) arg0).getLabel();
		}
		return toString();
	}
}
