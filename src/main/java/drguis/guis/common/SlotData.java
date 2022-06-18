package drguis.guis.common;

import org.bukkit.inventory.ItemStack;

public interface SlotData {

	public Integer getSlot(int guiSize);
	
	public ItemStack getItemStack();
	
}
