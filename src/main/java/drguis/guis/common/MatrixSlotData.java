package drguis.guis.common;

import org.bukkit.inventory.ItemStack;

public class MatrixSlotData implements SlotData {
	
	private int rowIndex;
	private int columnIndex;
	private ItemStack itemStack;
	
	public MatrixSlotData(int rowIndex, int columnIndex, ItemStack itemStack) {
		if (Math.abs(columnIndex) >= 9) {
			throw new IndexOutOfBoundsException("Column index: " + columnIndex + " is out of bounds!");
		}
		this.rowIndex = rowIndex;
		this.columnIndex = columnIndex;
		this.itemStack = itemStack;
	}

	@Override
	public Integer getSlot(int guiSize) {
		int numOfRows = guiSize / 9;
		int numOfColumnsInLastRow = guiSize % 9;
		if (Math.abs(rowIndex) >= numOfRows) {
			return null;
		}
		int row = rowIndex % numOfRows;
		if (row == numOfRows - 1) {
			if (Math.abs(columnIndex) >= numOfColumnsInLastRow) {
				return null;
			}
		}
		return 0;
	}
	
	@Override
	public ItemStack getItemStack() {
		return itemStack;
	}

}
