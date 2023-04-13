package drguis.utils;

import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;

import drguis.api.GUIsAPI;
import drguis.common.CloseReason;
import drguis.management.GUIsStack;
import drguis.management.PlayersGUIsCloseReasonsManager;
import drguis.models.types.editors.GUIEditor;
import drguis.models.types.editors.common.icons.PrevGUIIcon;
import drguis.views.GUIView;
import drlibs.utils.functions.InventoriesUtils;
import drlibs.utils.functions.InventoriesUtils.InventorySlotData;

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
		case FORCE_CLOSE:
			GUIsStack.getInstance().clearGUIViewOfPlayer(player.getUniqueId());
			break;
		case UPDATING_GUI:
		case PREV_GUI:
		case INPUT:
		case PREV_PAGE:
		case NEXT_PAGE:
			break;
		default:
			GUIsStack.getInstance().addGUIViewToPlayer(player.getUniqueId(), guiView);
		}
	}

	public static void defaultOnGUIEditorCloseEvent(GUIEditor guiEditor, GUIView guiView, CloseReason closeReason,
			Player player) {
		System.out.println("Closed GUI for the reason: " + closeReason);
		switch (closeReason) {
		case NORMAL:
		case FORCE_CLOSE:
			GUIsStack.getInstance().clearGUIViewOfPlayer(player.getUniqueId());
			guiEditor.clearEditingPlayer();
			break;
		case UPDATING_GUI:
		case PREV_GUI:
		case INPUT:
		case PREV_PAGE:
		case NEXT_PAGE:
			break;
		default:
			GUIsStack.getInstance().addGUIViewToPlayer(player.getUniqueId(), guiView);
		}
	}

	public static boolean openPrevGUI(Player player) {
		GUIView guiView = GUIsStack.getInstance().removeGUIViewToPlayer(player.getUniqueId());
		if (guiView != null) {
			PlayersGUIsCloseReasonsManager.getInstance().setCloseReason(player.getUniqueId(), CloseReason.PREV_GUI);
//			GUIsAPI.showGUIToPlayer(player, guiView);
//			GUIsAPI.updateGUIToPlayer(player);
			GUIsAPI.showUpdatedGUIToPlayer(player, guiView);
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

	public static GUIView getOpenGUIView(Player player) {
		InventoryView inventoryView = player.getOpenInventory();
		if (inventoryView == null) {
			return null;
		}
		Inventory inventory = inventoryView.getTopInventory();
		if (inventory == null) {
			return null;
		}
		InventoryHolder inventoryHolder = inventory.getHolder();
		if (inventoryHolder instanceof GUIView) {
			return (GUIView) inventoryHolder;
		}
		return null;
	}

	public static Map.Entry<Map<Integer, ItemStack>, Map<Integer, ItemStack>> getTopAndBottomInventoriesNewItems(
			Map<Integer, ItemStack> newItems, InventoryView inventoryView) {
		Map<Integer, ItemStack> topInventoryNewItems = new HashMap<>();
		Map<Integer, ItemStack> bottomInventoryNewItems = new HashMap<>();
		for (Map.Entry<Integer, ItemStack> newItemEntry : newItems.entrySet()) {
			int rawSlot = newItemEntry.getKey();
			ItemStack itemStack = newItemEntry.getValue();
			InventorySlotData inventorySlotData = InventoriesUtils.parseRawSlot(inventoryView, rawSlot);
			if (inventorySlotData.isTopInventory()) {
				topInventoryNewItems.put(inventorySlotData.getSlot(), itemStack);
			} else {
				bottomInventoryNewItems.put(inventorySlotData.getSlot(), itemStack);
			}
		}
		return new AbstractMap.SimpleEntry<Map<Integer, ItemStack>, Map<Integer, ItemStack>>(topInventoryNewItems,
				bottomInventoryNewItems);
	}

}
