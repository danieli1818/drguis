package drguis.views.common;

import java.util.List;

import org.bukkit.inventory.ItemStack;

import drguis.views.common.icons.IconProperties;

public interface Icon {
	
	public ItemStack getItemStack();
	
	public boolean cancelClickEvent();
	
	public List<Action> getActions();
	
	public IconProperties getProperties();
	
}
