package ch.bitwave.mim.canvas.generators;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.annotation.Nonnull;

import ch.bitwave.mim.m2.core.Classifier;
import ch.bitwave.mim.m2.core.Generalization;
import ch.bitwave.mim.m2.core.MimClass;
import ch.bitwave.mim.m2.core.MimPackage;

public class ClassDependencyTreeGenerator extends TreeGenerator {

	private ElementFilter rootFilter;

	public ClassDependencyTreeGenerator(@Nonnull final ElementFilter rootFilter) {
		super();
		this.rootFilter = rootFilter;
	}

	@Override
	public List<TreeNode> build(final MimPackage root) {
		List<TreeNode> result = new ArrayList<TreeNode>();
		Set<MimClass> elementSet = root.getAllOwnedClasses();
		List<MimClass> treeRoot = getRootElements(elementSet, this.rootFilter);
		for (MimClass mimClass : treeRoot) {
			TreeNode rootNode = new TreeNode(mimClass);
			result.add(rootNode);
			buildNode(rootNode, mimClass);
		}
		return result;
	}

	private void buildNode(final TreeNode node, final Classifier generalClass) {
		List<Generalization> specializations = generalClass.getSpecializations();
		for (Generalization gen : specializations) {
			Classifier specific = gen.getSpecific();
			TreeNode detailNode = new TreeNode(gen, specific);
			node.addDetail(detailNode);
			buildNode(detailNode, specific);
		}
	}

	private List<MimClass> getRootElements(final Set<MimClass> elementSet,
			final ElementFilter rootFilter) {
		List<MimClass> result = new ArrayList<MimClass>();
		for (MimClass mimClass : elementSet) {
			if (rootFilter.accepts(mimClass)) {
				result.add(mimClass);
			}
		}
		return result;
	}
}
