package drguis.guis.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

import drguis.guis.GUI;

public class GUIsListener implements Listener {

	@EventHandler
	public boolean onInventoryClickEvent(InventoryClickEvent event) {
		if (event.getInventory().getHolder() instanceof GUI) {
			onGUIClickEvent((GUI)event.getInventory().getHolder(), (Player)event.getWhoClicked(), event.getSlot(), event);
			return true;
		}
		return false;
	}
	
	public boolean onGUIClickEvent(GUI gui, Player player, int slot, InventoryClickEvent event) {
		return gui.onClickOnSlot(player, slot, event);
	}
	
}
