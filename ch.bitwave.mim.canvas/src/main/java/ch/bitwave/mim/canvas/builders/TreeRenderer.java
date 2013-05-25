package ch.bitwave.mim.canvas.builders;

import java.util.List;

import ch.bitwave.mim.canvas.features.ClassFeature;
import ch.bitwave.mim.canvas.features.ConnectionFeature;
import ch.bitwave.mim.canvas.features.GroupFeature;
import ch.bitwave.mim.canvas.features.NodeFeature;
import ch.bitwave.mim.canvas.features.PackageFeature;
import ch.bitwave.mim.canvas.generators.TreeNode;
import ch.bitwave.mim.canvas.strategies.ColoringStrategy;
import ch.bitwave.mim.canvas.strategies.ConnectorLayoutStrategy;
import ch.bitwave.mim.canvas.strategies.SizingStrategy;
import ch.bitwave.mim.m2.core.Generalization;
import ch.bitwave.mim.m2.core.MimClass;
import ch.bitwave.mim.m2.core.MimPackage;
import ch.bitwave.mim.m2.core.StubClass;
import ch.bitwave.mim.m2.core.StubPackage;
import ch.parametrix.common.util.ui.swing.canvas.FeatureFactory;
import ch.parametrix.common.util.ui.swing.clap.LayoutProcessor;
import ch.parametrix.common.util.ui.swing.contracts.Feature;
import ch.parametrix.common.util.ui.swing.contracts.ICanvasFeatureManager;

public abstract class TreeRenderer {

	private ICanvasFeatureManager featureManager;
	private FeatureFactory featureFactory;
	private ColoringStrategy coloringStrategy;
	private SizingStrategy sizingStrategy;
	private ConnectorLayoutStrategy connectorLayoutStrategy;
	private LayoutProcessor clap;

	public TreeRenderer() {
		this.featureFactory = new FeatureFactory();
		configureFeatureFactory(this.featureFactory);
	}

	public LayoutProcessor getLayoutProcessor() {
		return this.clap;
	}

	public ConnectorLayoutStrategy getConnectorLayoutStrategy() {
		return this.connectorLayoutStrategy;
	}

	public void setConnectorLayoutStrategy(final ConnectorLayoutStrategy connectorLayoutStrategy) {
		this.connectorLayoutStrategy = connectorLayoutStrategy;
	}

	public ColoringStrategy getColoringStrategy() {
		return this.coloringStrategy;
	}

	public void setColoringStrategy(final ColoringStrategy coloringStrategy) {
		this.coloringStrategy = coloringStrategy;
	}

	public SizingStrategy getSizingStrategy() {
		return this.sizingStrategy;
	}

	public void setSizingStrategy(final SizingStrategy sizingStrategy) {
		this.sizingStrategy = sizingStrategy;
	}

	public FeatureFactory getFeatureFactory() {
		return this.featureFactory;
	}

	protected void configureFeatureFactory(final FeatureFactory factory) {
		factory.put(StubPackage.class, PackageFeature.class);
		factory.put(MimPackage.class, PackageFeature.class);
		factory.put(Generalization.class, ConnectionFeature.class);
		factory.put(StubClass.class, ClassFeature.class);
		factory.put(MimClass.class, ClassFeature.class);
	}

	public void configure(final ICanvasFeatureManager featureManager, final LayoutProcessor clap) {
		this.featureManager = featureManager;
		this.clap = clap;
	}

	protected void addFeature(final Feature feature) {
		this.featureManager.addFeature(feature);
	}

	public abstract GroupFeature renderTree(final List<TreeNode> rootNodes);

	protected void configureNode(final NodeFeature nodeFeature) {
		nodeFeature.setConnectorLayoutStrategy(this.getConnectorLayoutStrategy());
		nodeFeature.setColoringStrategy(this.getColoringStrategy());
		nodeFeature.setSizingStrategy(this.getSizingStrategy());
	}

	protected void renderConnection(final NodeFeature master, final NodeFeature detail) {
		if (master != null) {
			ConnectionFeature con = new ConnectionFeature(master.getElement());
			master.addOutboundConnector(con.getSourceFeature());
			detail.addInboundConnector(con.getTargetFeature());
			addFeature(con.getSourceFeature());
			addFeature(con.getTargetFeature());
			addFeature(con);
		}
	}
}
