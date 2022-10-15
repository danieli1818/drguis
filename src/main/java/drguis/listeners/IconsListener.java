package drguis.listeners;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.InventoryHolder;

import drguis.views.common.Icon;
import drguis.views.common.events.IconClickEvent;
import drguis.views.GUIView;

public class IconsListener implements Listener {

	@EventHandler
	public void onInventoryClickEvent(InventoryClickEvent event) {
		InventoryHolder inventoryHolder = event.getInventory().getHolder();
		if (inventoryHolder instanceof GUIView) {
			Player player = (Player) event.getWhoClicked();
			GUIView guiView = (GUIView) inventoryHolder;
			Icon icon = guiView.getIcon(event.getRawSlot());
			Bukkit.getPluginManager().callEvent(new IconClickEvent(event, player, guiView, icon, event.getRawSlot()));
		}
	}
	
//	@EventHandler
//	public void onIconClickEvent(IconClickEvent event) {
//		Icon icon = event.getIcon();
//		if (icon != null) {
//			for (Action action : icon.getActions()) {
//				action.accept(event.getPlayer());
//			}
//		}
//	}
	
}
