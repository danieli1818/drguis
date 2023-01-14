package drguis.models.types;

import org.bukkit.entity.Player;

import drlibs.events.inventory.DragAndDropInventoryEvent;
import drlibs.events.inventory.ItemSlotSwapEvent;
import drlibs.events.inventory.moveitemtootherinventory.MoveItemToOtherInventoryEvent;
import drguis.common.CloseReason;
import drguis.common.events.GUIRelation;
import drguis.common.events.IconClickEvent;
import drguis.common.events.PlayerInventoryClickEvent;
import drguis.models.GUIModel;
import drguis.models.utils.IconsFunctionsUtils;
import drguis.utils.GUIsUtils;
import drguis.views.GUIView;

public class SimpleGUIModel implements GUIModel {

	private GUIView guiView;
	
	public SimpleGUIModel(GUIView guiView) {
		this.guiView = guiView;
		this.guiView.setGUIHolder(this);
	}
	
	@Override
	public GUIView getGUI(Player player) {
		return guiView;
	}

	@Override
	public void onIconClickEvent(IconClickEvent event) {
		IconsFunctionsUtils.defaultOnIconClickEvent(event);
	}

	@Override
	public void onDragAndDropEvent(DragAndDropInventoryEvent event, GUIRelation relation) {}

	@Override
	public void onSlotSwapEvent(ItemSlotSwapEvent event) {}

	@Override
	public void onMoveItemToOtherInventoryEvent(MoveItemToOtherInventoryEvent event, GUIRelation relation) {}

	@Override
	public void onPlayerInventoryClickEvent(PlayerInventoryClickEvent event) {}

	@Override
	public void onGUICloseEvent(GUIView guiView, CloseReason closeReason, Player player) {
		GUIsUtils.defaultOnGUICloseEvent(guiView, closeReason, player);
	}
	
	
}
