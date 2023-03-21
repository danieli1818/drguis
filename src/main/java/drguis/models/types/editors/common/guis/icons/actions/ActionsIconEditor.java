package drguis.models.types.editors.common.guis.icons.actions;

import org.bukkit.entity.Player;

import drguis.common.CloseReason;
import drguis.common.actions.ConsumerAction;
import drguis.common.events.GUIRelation;
import drguis.common.events.IconClickEvent;
import drguis.common.events.PlayerInventoryClickEvent;
import drguis.common.icons.types.ActionIcon;
import drguis.common.icons.types.ActionsIcon;
import drguis.common.items.UsefulItemStacks;
import drguis.common.items.UsefulItemStacksIDs;
import drguis.management.GUIsStack;
import drguis.models.GUIModel;
import drguis.models.types.editors.common.guis.icons.simple.SimpleIconEditor;
import drguis.models.types.editors.common.icons.PrevGUIIcon;
import drguis.models.utils.IconsFunctionsUtils;
import drguis.utils.GUIsUtils;
import drguis.views.GUIView;
import drguis.views.types.SparseGUIView;
import drlibs.events.inventory.DragInventoryDragAndDropInventoryEvent;
import drlibs.events.inventory.ItemSlotSwapEvent;
import drlibs.events.inventory.NormalDragAndDropInventoryEvent;
import drlibs.events.inventory.moveitemtootherinventory.MoveItemToOtherInventoryEvent;

public class ActionsIconEditor implements GUIModel {

	private ActionsIcon icon;

	public ActionsIconEditor(ActionsIcon icon) {
		this.icon = (ActionsIcon) icon;
	}

	@Override
	public GUIView getGUI(Player player) {
		GUIView guiView = new SparseGUIView(this, 36, "Icon Editor"); // TODO Change string to be retrieved from
																		// MessagesStorage
		guiView.setIcon(4, icon);
		guiView.setIcon(12, new ActionIcon(
				UsefulItemStacks.getInstance().getItemStack(UsefulItemStacksIDs.editActionsItemStack), true,
				new ConsumerAction(
						(Player currentPlayer) -> GUIsUtils.openSubGUI(player, new ActionsEditor(icon, 4)::getGUI))));
		guiView.setIcon(14, new ActionIcon(UsefulItemStacks.getInstance().getItemStack(UsefulItemStacksIDs.editItemItemStack), true,
				new ConsumerAction((Player currentPlayer) -> GUIsUtils.openSubGUI(player, new SimpleIconEditor(icon)::getGUI)))); // TODO Change
																										// guiView
																										// in the
																										// action
																										// function to
																										// the
																										// IconPropertiesEditor
		System.out.println("Icon Editor Has GUIView: " + GUIsStack.getInstance().hasGUIView(player.getUniqueId()));
		if (GUIsStack.getInstance().hasGUIView(player.getUniqueId())) {
			guiView.setIcon(28, new PrevGUIIcon());
		}
		return guiView;
	}

	@Override
	public void onIconClickEvent(IconClickEvent event) {
		IconsFunctionsUtils.defaultOnIconClickEvent(event);
	}

	@Override
	public void onNormalDragAndDropEvent(NormalDragAndDropInventoryEvent event, GUIRelation relation) {
	}

	@Override
	public void onDragInventoryDragAndDropEvent(DragInventoryDragAndDropInventoryEvent event, boolean isFromGUI) {
	}

	@Override
	public void onSlotSwapEvent(ItemSlotSwapEvent event) {
	}

	@Override
	public void onMoveItemToOtherInventoryEvent(MoveItemToOtherInventoryEvent event, GUIRelation relation) {
	}

	@Override
	public void onPlayerInventoryClickEvent(PlayerInventoryClickEvent event) {
		event.setCancelled(true);
	}

	@Override
	public void onGUICloseEvent(GUIView guiView, CloseReason closeReason, Player player) {
		GUIsUtils.defaultOnGUICloseEvent(guiView, closeReason, player);
	}

	@Override
	public GUIView getUpdatedGUI(Player player, GUIView prevGUIView) {
		return getGUI(player);
	}

}
