package drguis.guis.common;

import org.bukkit.inventory.ItemStack;

public class SlotData {

	private int slot;
	private ItemStack itemStack;
	
	public SlotData(int slot, ItemStack itemStack) {
		this.slot = slot;
		this.itemStack = itemStack;
	}
	
	public int getSlot() {
		return slot;
	}
	
	public ItemStack getItemStack() {
		return itemStack;
	}
		
}
