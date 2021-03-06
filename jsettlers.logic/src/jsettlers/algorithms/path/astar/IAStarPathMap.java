package jsettlers.algorithms.path.astar;

import jsettlers.algorithms.path.IPathCalculatable;
import jsettlers.common.Color;

public interface IAStarPathMap {

	boolean isBlocked(IPathCalculatable requester, int x, int y);

	float getCost(int sx, int sy, int tx, int ty);

	/**
	 * only for debugging
	 * 
	 * @param x
	 *            x coordinate
	 * @param y
	 *            y coordinate
	 */
	void markAsOpen(int x, int y);

	/**
	 * only for debugging
	 * 
	 * @param x
	 *            x coordinate
	 * @param y
	 *            y coordinate
	 */
	void markAsClosed(int x, int y);

	void setDebugColor(int x, int y, Color color);

	/**
	 * Gets the id of of the blocked partition of the given coordinates.
	 * 
	 * @param x
	 * @param y
	 * @return
	 */
	short getBlockedPartition(int x, int y);

}
