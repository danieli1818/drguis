package drguis.guis.common.regions;

import java.util.Iterator;

import drguis.guis.common.Region;

public class SerialRegion implements Region {
	
	private int fromIndex;
	private int length;
	
	public SerialRegion(int fromIndex, int length) {
		this.fromIndex = fromIndex;
		this.length = length;
		if (fromIndex <= 0) {
			throw new IllegalArgumentException("FromIndex parameter must be bigger than 0!");
		}
		if (length <= 0) {
			throw new IllegalArgumentException("Length parameter must be bigger than 0!");
		}
	}

	@Override
	public Iterator<Integer> iterator() {
		return new SerialRegionIterator(this);
	}

	@Override
	public int getSize() {
		return 0;
	}
	
	private int getFromIndex() {
		return fromIndex;
	}
	
	private int getLength() {
		return length;
	}
	
	private class SerialRegionIterator implements Iterator<Integer> {

		private int currentIndex;
		private int finalIndex;
		
		public SerialRegionIterator(SerialRegion serialRegion) {
			currentIndex = getFromIndex();
			finalIndex = currentIndex + serialRegion.getLength() - 1;
		}
		
		@Override
		public boolean hasNext() {
			return currentIndex != finalIndex;
		}

		@Override
		public Integer next() {
			currentIndex++;
			return currentIndex - 1;
		}
		
	}

}
