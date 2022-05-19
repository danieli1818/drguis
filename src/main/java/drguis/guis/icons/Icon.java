package drguis.guis.icons;

import java.util.Collection;

import org.bukkit.inventory.ItemStack;

import drguis.guis.icons.actions.ClickAction;

public interface Icon {

	public ItemStack getItemStack();
	
	public Collection<ClickAction> getClickActions();
	
}
