package ch.parametrix.common.util.ui.swing.utility;

import java.awt.Image;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.ClipboardOwner;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;

/**
 * Copies an image to the clipboard. Taken from the interwebz.
 */
public class CopyImageToClipboard implements ClipboardOwner {

	public void copyToClipboard(final Image image) {
		TransferableImage trans = new TransferableImage(image);
		Clipboard c = Toolkit.getDefaultToolkit().getSystemClipboard();
		c.setContents(trans, this);
	}

	@Override
	public void lostOwnership(final Clipboard clip, final Transferable trans) {
		throw new RuntimeException("Lost Clipboard Ownership");
	}

	private class TransferableImage implements Transferable {

		private Image image;

		public TransferableImage(final Image i) {
			this.image = i;
		}

		@Override
		public Object getTransferData(final DataFlavor flavor) throws UnsupportedFlavorException,
				IOException {
			if (flavor.equals(DataFlavor.imageFlavor) && this.image != null) {
				return this.image;
			} else {
				throw new UnsupportedFlavorException(flavor);
			}
		}

		@Override
		public DataFlavor[] getTransferDataFlavors() {
			DataFlavor[] flavors = new DataFlavor[1];
			flavors[0] = DataFlavor.imageFlavor;
			return flavors;
		}

		@Override
		public boolean isDataFlavorSupported(final DataFlavor flavor) {
			DataFlavor[] flavors = getTransferDataFlavors();
			for (int i = 0; i < flavors.length; i++) {
				if (flavor.equals(flavors[i])) {
					return true;
				}
			}

			return false;
		}
	}
}
