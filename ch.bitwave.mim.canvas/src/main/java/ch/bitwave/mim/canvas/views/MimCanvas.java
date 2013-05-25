package ch.bitwave.mim.canvas.views;

import java.awt.geom.AffineTransform;

import ch.parametrix.common.util.ui.swing.canvas.CanvasPanel;

public class MimCanvas extends CanvasPanel {

	private static final long serialVersionUID = 1L;

	@Override
	protected void initViewTransform() {
		super.initViewTransform();
		scaleCanvas(AffineTransform.getScaleInstance(0.05, 0.05));
	}

}
