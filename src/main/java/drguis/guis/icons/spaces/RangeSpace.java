package drguis.guis.icons.spaces;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class RangeSpace implements Space {

	private int fromIndex;
	private int toIndex;
	
	public RangeSpace(int fromIndex, int toIndex) {
		if (toIndex < fromIndex) {
			fromIndex += toIndex;
			toIndex = fromIndex - toIndex;
			fromIndex -= toIndex;
		}
		this.fromIndex = fromIndex;
		this.toIndex = toIndex;
	}

	@Override
	public Iterator<Integer> iterator() {
		return new RangeSpaceIterator(this);
	}

	@Override
	public List<Integer> getSpace() {
		List<Integer> spaceList = new ArrayList<>();
		for (int i = fromIndex; i < toIndex; i++) {
			spaceList.add(i);
		}
		return spaceList;
	}
	
	public int getFromIndex() {
		return this.fromIndex;
	}
	
	public int getToIndex() {
		return this.toIndex;
	}
	
	private class RangeSpaceIterator implements Iterator<Integer> {

		private int currentIndex;
		private int toIndex;
		
		public RangeSpaceIterator(RangeSpace rangeSpace) {
			this.currentIndex = rangeSpace.getFromIndex();
			this.toIndex = rangeSpace.getToIndex();
		}
		
		@Override
		public boolean hasNext() {
			return currentIndex != toIndex - 1;
		}

		@Override
		public Integer next() {
			if (hasNext()) {
				currentIndex++;
			}
			return currentIndex;
		}
		
	}

	@Override
	public boolean isInRange(int fromIndex, int toIndex) {
		return this.fromIndex >= fromIndex && this.toIndex <= toIndex;
	}
	
	@Override
	public int getRelativeSlot(int absoluteSlot) {
		return absoluteSlot - fromIndex;
	}
	
	@Override
	public int getAbsoluteSlot(int relativeSlot) {
		return relativeSlot + fromIndex;
	}
	
	@Override
	public boolean isAbsoluteSlotInSpace(int absoluteSlot) {
		return fromIndex <= absoluteSlot && absoluteSlot < toIndex;
	}

	@Override
	public int getSize() {
		return toIndex - fromIndex;
	}
	
}
