package drguis.guis;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

import drguis.guis.icons.Icon;

public interface GUI extends InventoryHolder {

	public Icon getIconInSlot(int slot);
	
	public boolean onClickOnSlot(Player player, int slot, InventoryClickEvent event);
	
	public Inventory getInventory(Player player);
	
	public int getSize();
	
}
