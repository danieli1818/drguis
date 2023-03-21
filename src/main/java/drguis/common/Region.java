package drguis.common;

public interface Region extends Iterable<Integer> {

	public int getSize();
	
	public boolean isInRegion(int index);
	
}
