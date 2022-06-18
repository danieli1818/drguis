package drguis.guis.icons.spaces;

import java.util.List;

public interface Space extends Iterable<Integer> {

	public List<Integer> getSpace();
	
	public boolean isInRange(int fromIndex, int toIndex);
	
	public int getRelativeSlot(int absoluteSlot);
	
	public int getAbsoluteSlot(int relativeSlot);
	
	public boolean isAbsoluteSlotInSpace(int absoluteSlot);
	
	public int getSize();
	
}
