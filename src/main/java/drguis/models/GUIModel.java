package drguis.models;

import org.bukkit.entity.Player;

import drlibs.events.inventory.DragInventoryDragAndDropInventoryEvent;
import drlibs.events.inventory.ItemSlotSwapEvent;
import drlibs.events.inventory.NormalDragAndDropInventoryEvent;
import drlibs.events.inventory.moveitemtootherinventory.MoveItemToOtherInventoryEvent;
import drguis.common.CloseReason;
import drguis.common.events.GUIRelation;
import drguis.common.events.IconClickEvent;
import drguis.common.events.PlayerInventoryClickEvent;
import drguis.views.GUIView;

public interface GUIModel {

	public GUIView getGUI(Player player);
	
	public void onIconClickEvent(IconClickEvent event);
	
	public void onPlayerInventoryClickEvent(PlayerInventoryClickEvent event);
	
	public void onNormalDragAndDropEvent(NormalDragAndDropInventoryEvent event, GUIRelation relation);
	
	public void onDragInventoryDragAndDropEvent(DragInventoryDragAndDropInventoryEvent event, boolean isFromGUI);
	
	public void onSlotSwapEvent(ItemSlotSwapEvent event);
	
	public void onMoveItemToOtherInventoryEvent(MoveItemToOtherInventoryEvent event, GUIRelation relation);
	
	public void onGUICloseEvent(GUIView guiView, CloseReason closeReason, Player player);
	
	public GUIView getUpdatedGUI(Player player, GUIView prevGUIView);
	
}
