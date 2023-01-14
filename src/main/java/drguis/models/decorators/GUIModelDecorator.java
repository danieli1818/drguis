package drguis.models.decorators;

import org.bukkit.entity.Player;

import drlibs.events.inventory.DragAndDropInventoryEvent;
import drlibs.events.inventory.ItemSlotSwapEvent;
import drlibs.events.inventory.moveitemtootherinventory.MoveItemToOtherInventoryEvent;
import drguis.common.CloseReason;
import drguis.common.events.GUIRelation;
import drguis.common.events.IconClickEvent;
import drguis.common.events.PlayerInventoryClickEvent;
import drguis.models.GUIModel;
import drguis.views.GUIView;

public class GUIModelDecorator implements GUIModel {

	private GUIModel guiModel;
	
	public GUIModelDecorator(GUIModel guiModel) {
		this.guiModel = guiModel;
	}
	
	@Override
	public GUIView getGUI(Player player) {
		return guiModel.getGUI(player);
	}

	@Override
	public void onIconClickEvent(IconClickEvent event) {
		guiModel.onIconClickEvent(event);
	}

	@Override
	public void onDragAndDropEvent(DragAndDropInventoryEvent event, GUIRelation relation) {
		guiModel.onDragAndDropEvent(event, relation);
	}

	@Override
	public void onSlotSwapEvent(ItemSlotSwapEvent event) {
		guiModel.onSlotSwapEvent(event);
	}

	@Override
	public void onMoveItemToOtherInventoryEvent(MoveItemToOtherInventoryEvent event, GUIRelation relation) {
		guiModel.onMoveItemToOtherInventoryEvent(event, relation);
	}

	@Override
	public void onPlayerInventoryClickEvent(PlayerInventoryClickEvent event) {
		guiModel.onPlayerInventoryClickEvent(event);
	}
	
	@Override
	public void onGUICloseEvent(GUIView guiView, CloseReason closeReason, Player player) {
		guiModel.onGUICloseEvent(guiView, closeReason, player);
	}

	
	
}
