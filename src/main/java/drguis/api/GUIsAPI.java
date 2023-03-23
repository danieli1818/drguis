package drguis.api;

import java.util.function.Function;

import org.bukkit.entity.Player;

import drguis.common.CloseReason;
import drguis.management.PlayersGUIsCloseReasonsManager;
import drguis.utils.GUIsUtils;
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
		GUIView guiView = GUIsUtils.getOpenGUIView(player);
		if (guiView != null) {
			PlayersGUIsCloseReasonsManager.getInstance().setCloseReason(player.getUniqueId(), CloseReason.UPDATING_GUI);
			player.openInventory(guiView.getGUIHolder().getUpdatedGUI(player, guiView).getInventory());
		}
	}
	
	public static void showUpdatedGUIToPlayer(Player player, GUIView gui) {
		player.openInventory(gui.getGUIHolder().getUpdatedGUI(player, gui).getInventory());
	}
	
}
