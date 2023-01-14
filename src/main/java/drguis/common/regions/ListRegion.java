package drguis.common.regions;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import drguis.common.Region;

public class ListRegion implements Region {
	
	private List<Integer> regionList;
	
	public ListRegion(List<Integer> regionList) {
		this.regionList = regionList;
	}
	
	public ListRegion(int... regionList) {
		this.regionList = new ArrayList<>();
		for (int num : regionList) {
			this.regionList.add(num);
		}
	}
	
	public ListRegion addSlot(int slotIndex) {
		regionList.add(slotIndex);
		return this;
	}
	
	public ListRegion removeSlot(int slotIndex) {
		regionList.remove(slotIndex);
		return this;
	}
	
	public ListRegion clear() {
		regionList.clear();
		return this;
	}

	@Override
	public Iterator<Integer> iterator() {
		return regionList.iterator();
	}

	@Override
	public int getSize() {
		return regionList.size();
	}

}
