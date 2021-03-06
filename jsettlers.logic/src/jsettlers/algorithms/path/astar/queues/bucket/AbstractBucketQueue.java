package jsettlers.algorithms.path.astar.queues.bucket;

public abstract class AbstractBucketQueue {
	public abstract void insert(int elementId, float rank);

	public abstract int size();

	public abstract void clear();

	/**
	 * Deletes the element of the heap that has the minimal value.
	 * <p>
	 * If the heap is empty, no action is performed.
	 * 
	 * @return The deleted element, or -1 if the heap was empty.
	 */
	public abstract int deleteMin();

	/**
	 * This method must be called to update the position in the priority queue when the costs of the element had been reduced (the priority has
	 * increased!).
	 * 
	 * @param elementId
	 *            Id of the element.
	 * @param newRank
	 *            the old rank of the element.
	 */
	public abstract void increasedPriority(int elementId, float oldRank, float newRank);

	/**
	 * 
	 * @return Returns true if the priority queue is empty,<br>
	 *         false if it's not empty.
	 */
	public abstract boolean isEmpty();
}
