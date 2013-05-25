package ch.bitwave.mim.canvas.generators;

import java.text.Collator;
import java.util.Comparator;

public class TreeNodeAlphabetizer implements Comparator<TreeNode> {

	@Override
	public int compare(final TreeNode arg0, final TreeNode arg1) {
		return Collator.getInstance().compare(arg0.getLabel(), arg1.getLabel());
	}

}
