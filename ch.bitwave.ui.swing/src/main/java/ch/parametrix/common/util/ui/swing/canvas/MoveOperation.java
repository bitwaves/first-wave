package ch.parametrix.common.util.ui.swing.canvas;

import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import ch.parametrix.common.util.ui.swing.WorldCoordinate;
import ch.parametrix.common.util.ui.swing.contracts.DeviceDistance;
import ch.parametrix.common.util.ui.swing.contracts.ICanvasFeature;
import ch.parametrix.common.util.ui.swing.contracts.ICanvasFeatureProvider;
import ch.parametrix.common.util.ui.swing.contracts.WorldDistance;

/**
 * An operation for moving features on a canvas.
 * 
 */
public class MoveOperation extends DragOperation {

	Map<ICanvasFeature, WorldCoordinate> originalPlacement;

	/**
	 * Creates a new MoveOperation for moving features on the given canvas.
	 * 
	 * @param canvas
	 *            the canvas to move features on.
	 * @param provider
	 *            the provider responsible for providing the Features.
	 */
	public MoveOperation(final CanvasPanel canvas, final ICanvasFeatureProvider provider) {
		super(canvas, provider);
		backupOriginalPlacement(provider.getSelectedFeatureSet());
	}

	private void backupOriginalPlacement(final Set<? extends ICanvasFeature> selectedFeatureSet) {
		this.originalPlacement = new HashMap<ICanvasFeature, WorldCoordinate>();
		for (ICanvasFeature feature : selectedFeatureSet) {
			this.originalPlacement.put(feature, feature.getPlacement());
			for (ICanvasFeature detail : feature.getDetailFeatures()) {
				if (detail.isCompositeFeature() && !selectedFeatureSet.contains(detail)) {
					this.originalPlacement.put(detail, detail.getPlacement());
				}
			}
		}
	}

	@Override
	public void update(final MouseEvent me, final DeviceDistance deltaScreen,
			final WorldDistance deltaWorld) {
		double dx = this.worldPos.getX() - this.worldOrigin.getX();
		double dy = this.worldPos.getY() - this.worldOrigin.getY();
		boolean snap = !me.isControlDown();
		int gridSize = this.provider.getGridSize();
		for (Entry<ICanvasFeature, WorldCoordinate> entry : this.originalPlacement.entrySet()) {
			ICanvasFeature feature = entry.getKey();
			WorldCoordinate orig = entry.getValue();
			double nx = orig.getX() + dx;
			double ny = orig.getY() + dy;
			if (snap) {
				nx = (int) Math.round(nx / gridSize) * gridSize;
				ny = (int) Math.round(ny / gridSize) * gridSize;
			}
			feature.moveTo(this.provider, new WorldCoordinate(nx, ny));
		}
	}

	@Override
	public void cancel() {
		for (Entry<ICanvasFeature, WorldCoordinate> entry : this.originalPlacement.entrySet()) {
			ICanvasFeature feature = entry.getKey();
			WorldCoordinate orig = entry.getValue();
			feature.moveTo(this.provider, new WorldCoordinate(orig));
		}
	}

	@Override
	public void apply() throws OperationException {

	}

}
