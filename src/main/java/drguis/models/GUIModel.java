package drguis.models;

import org.bukkit.entity.Player;

import drguis.views.common.events.IconClickEvent;
import drguis.views.common.events.PlayerInventoryClickEvent;
import drlibs.events.inventory.DragAndDropInventoryEvent;
import drlibs.events.inventory.ItemSlotSwapEvent;
import drlibs.events.inventory.moveitemtootherinventory.MoveItemToOtherInventoryEvent;
import drguis.common.CloseReason;
import drguis.common.events.GUIRelation;
import drguis.views.GUIView;

public interface GUIModel {

	public GUIView getGUI(Player player);
	
	public void onIconClickEvent(IconClickEvent event);
	
	public void onPlayerInventoryClickEvent(PlayerInventoryClickEvent event);
	
	public void onDragAndDropEvent(DragAndDropInventoryEvent event, GUIRelation relation);
	
	public void onSlotSwapEvent(ItemSlotSwapEvent event);
	
	public void onMoveItemToOtherInventoryEvent(MoveItemToOtherInventoryEvent event, GUIRelation relation);
	
	public void onGUICloseEvent(GUIView guiView, CloseReason closeReason, Player player);
	
}
