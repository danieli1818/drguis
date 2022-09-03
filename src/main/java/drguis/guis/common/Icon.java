package drguis.guis.common;

import java.util.Collection;

import org.bukkit.inventory.ItemStack;

public interface Icon {

	public ItemStack getIcon();
	
	public Collection<Action> getActions();
	
	public IconProperties getProperties();
	
}
