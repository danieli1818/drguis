package drguis.guis.common;

import org.bukkit.inventory.ItemStack;

public class IndexSlotData implements SlotData {

	private int slot;
	private ItemStack itemStack;
	
	public IndexSlotData(int slot, ItemStack itemStack) {
		this.slot = slot;
		this.itemStack = itemStack;
	}
	
	public Integer getSlot(int guiSize) {
		return slot;
	}
	
	public ItemStack getItemStack() {
		return itemStack;
	}
		
}
