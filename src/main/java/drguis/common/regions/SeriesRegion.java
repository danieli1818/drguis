package drguis.common.regions;

import java.util.Iterator;

import drguis.common.Region;

public class SeriesRegion implements Region {

	private int startIndex;
	private int incrementor;
	private int length;
	
	public SeriesRegion(int length) {
		this(0, 1, length);
	}
	
	public SeriesRegion(int startIndex, int incrementor, int length) {
		this.startIndex = startIndex;
		this.incrementor = incrementor;
		this.length = length;
	}
	
	@Override
	public Iterator<Integer> iterator() {
		return new SeriesRegionIterator(this);
	}
	
	public int getStartIndex() {
		return startIndex;
	}
	
	public int getIncrementor() {
		return incrementor;
	}
	
	public int getLength() {
		return length;
	}

	private class SeriesRegionIterator implements Iterator<Integer> {

		private SeriesRegion seriesRegion;
		private int currentIndex;
		private int currentValue;
		
		public SeriesRegionIterator(SeriesRegion seriesRegion) {
			this.seriesRegion = seriesRegion;
			currentIndex = 0;
			currentValue = seriesRegion.getStartIndex();
		}
		
		@Override
		public boolean hasNext() {
			return currentIndex + 1 <= seriesRegion.getLength();
		}

		@Override
		public Integer next() {
			if (hasNext()) {
				int returnValue = currentValue;
				currentIndex++;
				currentValue += seriesRegion.getIncrementor();
				return returnValue;
			}
			return null;
		}
		
	}

	@Override
	public int getSize() {
		return length;
	}
	
}
