package ch.bitwave.mim.canvas.builders;

import java.util.List;

import javax.annotation.Nullable;

import ch.bitwave.mim.canvas.features.HorizontalGroupFeature;
import ch.bitwave.mim.canvas.features.HorizontalRankFeature;
import ch.bitwave.mim.canvas.features.NodeFeature;
import ch.bitwave.mim.canvas.generators.TreeNode;
import ch.bitwave.mim.canvas.strategies.FitTextSizingStrategy;
import ch.bitwave.mim.canvas.strategies.HorizontalFannedConnectorLayoutStrategy;
import ch.parametrix.common.util.ui.swing.clap.DelegatedLayoutGoal;

/**
 * Renders a tree of nodes in a left-to-right orientation.
 */
public class HorizontalTreeRenderer extends TreeRenderer {
	public HorizontalTreeRenderer() {
		// In a horizontal tree we size the nodes by their labels by default and
		// fan connectors vertically.
		setSizingStrategy(new FitTextSizingStrategy());
		setConnectorLayoutStrategy(new HorizontalFannedConnectorLayoutStrategy());
	}

	@Override
	public HorizontalGroupFeature renderTree(final List<TreeNode> rootNodes) {
		HorizontalGroupFeature root = createNode(null);
		for (TreeNode node : rootNodes) {
			renderNode(root, node);
		}
		getLayoutProcessor().addGoal(new DelegatedLayoutGoal(root));
		return root;
	}

	private void renderNode(final HorizontalGroupFeature parentGroup, final TreeNode node) {
		NodeFeature nodeFeature = (NodeFeature) getFeatureFactory().createFeatureForElement(
				node.getElement());
		configureNode(nodeFeature);
		addFeature(nodeFeature);
		HorizontalGroupFeature detailFeature = createNode(nodeFeature);
		parentGroup.addDetailGroup(detailFeature);
		renderConnection(parentGroup.getNode(), detailFeature.getNode());
		for (TreeNode detailNode : node.getDetails()) {
			renderNode(detailFeature, detailNode);
		}
	}

	protected HorizontalGroupFeature createNode(@Nullable final NodeFeature feature) {
		Object model = null;
		if (feature != null)
			model = feature.getElement();
		HorizontalGroupFeature group = new HorizontalGroupFeature(model);
		HorizontalRankFeature rank = new HorizontalRankFeature(model);
		group.setRank(rank);
		group.setNode(feature);
		addFeature(group);
		addFeature(rank);
		return group;
	}

}
