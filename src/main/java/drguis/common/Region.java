package drguis.common;

public interface Region extends Iterable<Integer> {

	/**
	 * Returns the size of the region
	 * @return the size of the region
	 */
	public int getSize();
	
	/**
	 * Checks if the slotIndex is in the region and returns the result
	 * @param slotIndex The index of the slot to check
	 * @return Whether the slotIndex is in the region or not
	 */
	public boolean isInRegion(int slotIndex);
	
	/**
	 * Calculates the region index of the slotIndex and returns it
	 * if isn't in the region returns -1
	 * @param slotIndex The slot index to check
	 * @return The region index of the slotIndex if in region else -1
	 */
	public int getRegionIndex(int slotIndex);
	
}
