package drguis.api;

import org.bukkit.entity.Player;

import drguis.views.GUIView;

public class GUIsAPI {

	public static void showGUIToPlayer(Player player, GUIView gui) {
		player.openInventory(gui.getInventory());
	}
	
}
