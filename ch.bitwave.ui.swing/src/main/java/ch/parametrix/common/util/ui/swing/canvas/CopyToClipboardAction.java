package ch.parametrix.common.util.ui.swing.canvas;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

public class CopyToClipboardAction extends AbstractAction {

	private static final long serialVersionUID = 1L;
	public static final Object ACTION_NAME = "CopyImageToClipboard";
	private CanvasPanel canvas;

	public CopyToClipboardAction(final CanvasPanel canvas) {
		this.canvas = canvas;
	}

	@Override
	public void actionPerformed(final ActionEvent arg0) {
		RenderSettings options = new RenderSettings();
		options.setResolution(72);
		this.canvas.copyImageToClipboard(options);
	}

}
