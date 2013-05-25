package ch.bitwave.mim.canvas.builders;

import java.util.List;

import javax.annotation.Nullable;

import ch.bitwave.mim.canvas.features.GroupFeature;
import ch.bitwave.mim.canvas.features.NodeFeature;
import ch.bitwave.mim.canvas.features.VerticalGroupFeature;
import ch.bitwave.mim.canvas.features.VerticalRankFeature;
import ch.bitwave.mim.canvas.generators.TreeNode;
import ch.bitwave.mim.canvas.strategies.VerticalFannedConnectorLayoutStrategy;
import ch.parametrix.common.util.ui.swing.clap.DelegatedLayoutGoal;

/**
 * Renders a tree of nodes in a top-to-bottom orientation.
 */
public class VerticalTreeRenderer extends TreeRenderer {

	public VerticalTreeRenderer() {
		setConnectorLayoutStrategy(new VerticalFannedConnectorLayoutStrategy());
	}

	@Override
	public GroupFeature renderTree(final List<TreeNode> rootNodes) {
		VerticalGroupFeature root = createNode(null);
		for (TreeNode node : rootNodes) {
			renderNode(root, node);
		}
		getLayoutProcessor().addGoal(new DelegatedLayoutGoal(root));
		return root;
	}

	private void renderNode(final VerticalGroupFeature parentGroup, final TreeNode node) {
		NodeFeature nodeFeature = (NodeFeature) getFeatureFactory().createFeatureForElement(
				node.getElement());
		configureNode(nodeFeature);
		addFeature(nodeFeature);
		VerticalGroupFeature detailFeature = createNode(nodeFeature);
		parentGroup.addDetailGroup(detailFeature);
		renderConnection(parentGroup.getNode(), detailFeature.getNode());
		for (TreeNode detailNode : node.getDetails()) {
			renderNode(detailFeature, detailNode);
		}
	}

	protected VerticalGroupFeature createNode(@Nullable final NodeFeature feature) {
		Object model = null;
		if (feature != null)
			model = feature.getElement();
		VerticalGroupFeature group = new VerticalGroupFeature(model);
		VerticalRankFeature rank = new VerticalRankFeature(model);
		group.setRank(rank);
		group.setNode(feature);
		addFeature(group);
		addFeature(rank);
		return group;
	}

}
