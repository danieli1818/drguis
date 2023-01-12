package drguis.utils;

import java.util.function.Function;

import org.bukkit.entity.Player;

import drguis.api.GUIsAPI;
import drguis.common.CloseReason;
import drguis.management.GUIsStack;
import drguis.management.PlayersGUIsCloseReasonsManager;
import drguis.models.types.editors.common.icons.PrevGUIIcon;
import drguis.views.GUIView;

public class GUIsUtils {

	public static void openSubGUI(Player player, GUIView guiView) {
		PlayersGUIsCloseReasonsManager.getInstance().setCloseReason(player.getUniqueId(), CloseReason.OPENING_GUI);
		GUIsAPI.showGUIToPlayer(player, guiView);
	}
	
	public static void openSubGUI(Player player, Function<Player, GUIView> guiViewFunction) {
		System.out.println("Yay function openSubGUI!");
		PlayersGUIsCloseReasonsManager.getInstance().setCloseReason(player.getUniqueId(), CloseReason.OPENING_GUI);
		GUIsAPI.showGUIToPlayer(player, guiViewFunction);
	}
	
	public static void defaultOnGUICloseEvent(GUIView guiView, CloseReason closeReason, Player player) {
		System.out.println("Closed GUI for the reason: " + closeReason);
		switch (closeReason) {
		case NORMAL:
			GUIsStack.getInstance().clearGUIViewOfPlayer(player.getUniqueId());
			break;
		case UPDATING_GUI:
		case PREV_GUI:
			break;
		default:
			GUIsStack.getInstance().addGUIViewToPlayer(player.getUniqueId(), guiView);
		}
	}
	
	public static boolean openPrevGUI(Player player) {
		GUIView guiView = GUIsStack.getInstance().removeGUIViewToPlayer(player.getUniqueId());
		if (guiView != null) {
			PlayersGUIsCloseReasonsManager.getInstance().setCloseReason(player.getUniqueId(), CloseReason.PREV_GUI);
			GUIsAPI.showGUIToPlayer(player, guiView);
			return true;
		}
		return false;
	}
	
	public static boolean addPrevGUIIcon(int slot, GUIView guiView, Player player) {
		if (GUIsStack.getInstance().hasGUIView(player.getUniqueId())) {
			guiView.setIcon(slot, new PrevGUIIcon());
			return true;
		}
		return false;
	}
	
}
