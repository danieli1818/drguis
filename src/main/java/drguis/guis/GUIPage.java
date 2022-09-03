package drguis.guis;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

import drguis.guis.common.Icon;

public interface GUIPage extends InventoryHolder {

	public Inventory getInventory(Player player);
	
	public void onInventoryClickEvent(InventoryClickEvent event);
	
	public Icon getIconInIndex(int index);
	
	public Icon[] getIcons();
	
	public Icon getIconInIndex(Player player, int index);
	
	public Icon[] getIcons(Player player);
	
	public int getPageSize();
	
	public String getTitle();
	
}
