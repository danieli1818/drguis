package drguis.api;

import java.util.function.Function;

import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.InventoryView;

import drguis.common.CloseReason;
import drguis.management.PlayersGUIsCloseReasonsManager;
import drguis.views.GUIView;

public class GUIsAPI {

	public static void showGUIToPlayer(Player player, GUIView gui) {
		player.openInventory(gui.getInventory());
	}
	
	public static void showGUIToPlayer(Player player, Function<Player, GUIView> guiFunction) {
		player.closeInventory();
		player.openInventory(guiFunction.apply(player).getInventory());
	}
	
	public static void updateGUIToPlayer(Player player) {
		InventoryView inventoryView = player.getOpenInventory();
		if (inventoryView == null) {
			return;
		}
		Inventory inventory = inventoryView.getTopInventory();
		if (inventory == null) {
			return;
		}
		InventoryHolder inventoryHolder = inventory.getHolder();
		if (inventoryHolder instanceof GUIView) {
			System.out.println("First Icon Of InventoryHolder: " + ((GUIView) inventoryHolder).getIcon(0));
			System.out.println("Icons Of InventoryHolder: " + ((GUIView) inventoryHolder).getIcons());
			System.out.println("GUIView: " + inventoryHolder);
			GUIView guiView = (GUIView) inventoryHolder;
			PlayersGUIsCloseReasonsManager.getInstance().setCloseReason(player.getUniqueId(), CloseReason.UPDATING_GUI);
			player.openInventory(guiView.getGUIHolder().getUpdatedGUI(player, guiView).getInventory());
		}
	}
	
}
