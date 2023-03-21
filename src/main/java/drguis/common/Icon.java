package drguis.common;

import java.util.List;

import org.bukkit.inventory.ItemStack;

import drguis.common.icons.IconProperties;

public interface Icon {
	
	public ItemStack getItemStack();
	
	public ItemStack setItemStack(ItemStack itemStack);
	
	public boolean cancelClickEvent();
	
	public List<Action> getActions();
	
	public IconProperties getProperties();
	
	public String getClassType();
	
	public Icon cloneIcon();
	
}
