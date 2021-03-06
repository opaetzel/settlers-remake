package jsettlers.mapcreator.tools.shapes;

import jsettlers.common.position.ShortPoint2D;
import jsettlers.mapcreator.localization.EditorLabels;

/**
 * This shape lets space between its points.
 * 
 * @author michael
 *
 */
public class GridShape extends LineShape {
	@Override
	protected boolean shouldDrawAt(ShortPoint2D current) {
		return current.x % 2 == 0 && current.y % 2 == 1;
	}

	@Override
	public String getName() {
		return EditorLabels.getLabel("grid_line");
	}
}
