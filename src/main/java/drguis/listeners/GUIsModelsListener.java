package drguis.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.InventoryHolder;

import drguis.common.CloseReason;
import drguis.common.events.GUIRelation;
import drguis.common.events.IconClickEvent;
import drguis.common.events.PlayerInventoryClickEvent;
import drguis.management.PlayersGUIsCloseReasonsManager;
import drguis.models.GUIModel;
import drguis.views.GUIView;
import drlibs.events.inventory.DragAndDropInventoryEvent;
import drlibs.events.inventory.ItemSlotSwapEvent;
import drlibs.events.inventory.moveitemtootherinventory.MoveItemToOtherInventoryEvent;

public class GUIsModelsListener implements Listener {

	@EventHandler(priority = EventPriority.LOWEST)
	public void onIconClickEvent(IconClickEvent event) {
		event.getGuiView().getGUIHolder().onIconClickEvent(event);
	}
	
	@EventHandler(priority = EventPriority.LOWEST)
	public void onPlayerInventoryClickEvent(PlayerInventoryClickEvent event) {
		event.getGuiView().getGUIHolder().onPlayerInventoryClickEvent(event);
	}
	
	@EventHandler
	public void onDragAndDropEvent(DragAndDropInventoryEvent event) {
		InventoryClickEvent startDragEvent = event.getStartDragEvent();
		InventoryClickEvent dropEvent = event.getDropEvent();
		InventoryHolder startInventoryHolder = startDragEvent.getClickedInventory().getHolder();
		InventoryHolder dropInventoryHolder = dropEvent.getClickedInventory().getHolder();
		if (startInventoryHolder instanceof GUIView) {
			GUIModel startGUIModel = ((GUIView)startInventoryHolder).getGUIHolder();
			if (dropInventoryHolder instanceof GUIView) {
				GUIModel dropGUIModel = ((GUIView)dropInventoryHolder).getGUIHolder();
				if (startGUIModel == dropGUIModel) {
					startGUIModel.onDragAndDropEvent(event, GUIRelation.INNER);
				} else {
					startGUIModel.onDragAndDropEvent(event, GUIRelation.FROM);
					dropGUIModel.onDragAndDropEvent(event, GUIRelation.TO);
				}
			} else {
				startGUIModel.onDragAndDropEvent(event, GUIRelation.FROM);
			}
		} else {
			if (dropInventoryHolder instanceof GUIView) {
				GUIModel dropGUIModel = ((GUIView)dropInventoryHolder).getGUIHolder();
				dropGUIModel.onDragAndDropEvent(event, GUIRelation.TO);
			}
		}
		System.out.println("Yay drag and drop event!");
	}
	
	@EventHandler
	public void onSlotSwapEvent(ItemSlotSwapEvent event) {
		InventoryClickEvent swapClickEvent = event.getSwapClickEvent();
		InventoryHolder inventoryHolder = swapClickEvent.getInventory().getHolder();
		if (inventoryHolder instanceof GUIView) {
			((GUIView)inventoryHolder).getGUIHolder().onSlotSwapEvent(event);
		}
		System.out.println("Yay slot swap event!");
		System.out.println(event);
	}
	
	@EventHandler
	public void onMoveItemToOtherInventoryEvent(MoveItemToOtherInventoryEvent event) {
		InventoryHolder fromInventoryHolder = event.getFromInventory().getHolder();
		if (fromInventoryHolder instanceof GUIView) {
			((GUIView)fromInventoryHolder).getGUIHolder().onMoveItemToOtherInventoryEvent(event, GUIRelation.FROM);
		} else {
			InventoryHolder toInventoryHolder = event.getToInventory().getHolder();
			if (toInventoryHolder instanceof GUIView) {
				((GUIView)toInventoryHolder).getGUIHolder().onMoveItemToOtherInventoryEvent(event, GUIRelation.TO);
			}
		}
		System.out.println("Yay move item to other inventory event!");
		System.out.println(event);
	}
	
	@EventHandler
	public void onInventoryCloseEvent(InventoryCloseEvent event) {
		InventoryHolder inventoryHolder = event.getInventory().getHolder();
		if (inventoryHolder instanceof GUIView) {
			GUIView guiView = (GUIView) inventoryHolder;
			Player player = (Player) event.getPlayer();
			CloseReason closeReason = PlayersGUIsCloseReasonsManager.getInstance().getCloseReason(player.getUniqueId());
			PlayersGUIsCloseReasonsManager.getInstance().resetCloseReason(player.getUniqueId());
			guiView.getGUIHolder().onGUICloseEvent(guiView, closeReason, player);
		}
	}
	
}
