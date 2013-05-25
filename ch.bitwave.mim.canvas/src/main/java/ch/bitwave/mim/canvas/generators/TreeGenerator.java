package ch.bitwave.mim.canvas.generators;

import java.util.List;

import ch.bitwave.mim.m2.core.MimPackage;

public abstract class TreeGenerator {

	public abstract List<TreeNode> build(MimPackage root);

}
