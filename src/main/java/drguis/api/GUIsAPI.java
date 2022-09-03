package drguis.api;

import org.bukkit.entity.Player;

import drguis.guis.GUIPage;

public class GUIsAPI {

	public static void showGUIToPlayer(Player player, GUIPage guiPage) {
		player.openInventory(guiPage.getInventory(player));
	}
	
}
