package drguis.views.common.icons.utils;

import org.bukkit.inventory.ItemStack;

public class IconMetadata {

	private ItemStack itemStack;
	private int index;
	
	public IconMetadata(ItemStack itemStack, int index) {
		this.itemStack = itemStack;
		this.index = index;
	}
	
	public ItemStack getItemStack() {
		return itemStack;
	}
	
	public int getIndex() {
		return index;
	}
	
}
