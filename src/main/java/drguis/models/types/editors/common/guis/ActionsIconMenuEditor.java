package drguis.models.types.editors.common.guis;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import drguis.common.CloseReason;
import drguis.common.events.GUIRelation;
import drguis.management.GUIsStack;
import drguis.models.GUIModel;
import drguis.models.types.editors.common.icons.PrevGUIIcon;
import drguis.models.utils.IconsFunctionsUtils;
import drguis.utils.GUIsUtils;
import drguis.views.GUIView;
import drguis.views.common.Icon;
import drguis.views.common.actions.ConsumerAction;
import drguis.views.common.events.IconClickEvent;
import drguis.views.common.events.PlayerInventoryClickEvent;
import drguis.views.common.icons.types.ActionIcon;
import drguis.views.common.icons.types.ActionsIcon;
import drguis.views.types.SparseGUIView;
import drlibs.events.inventory.DragAndDropInventoryEvent;
import drlibs.events.inventory.ItemSlotSwapEvent;
import drlibs.events.inventory.moveitemtootherinventory.MoveItemToOtherInventoryEvent;

public class ActionsIconMenuEditor implements GUIModel {

	private ActionsIcon icon;

	public ActionsIconMenuEditor(Icon icon) {
		if (icon instanceof ActionsIcon) {
			this.icon = (ActionsIcon)icon;
		} else {
			this.icon = new ActionsIcon(icon.getItemStack(), true);
		}
	}

	@Override
	public GUIView getGUI(Player player) {
		GUIView guiView = new SparseGUIView(this, 36, "Icon Editor"); // TODO Change string to be retrieved from
																		// MessagesStorage
		guiView.setIcon(4, icon);
		guiView.setIcon(12, new ActionIcon(new ItemStack(Material.DIAMOND_PICKAXE), true,
				new ConsumerAction((Player currentPlayer) -> GUIsUtils.openSubGUI(player, new ActionsIconEditor(icon, 4)::getGUI)))); // TODO
																													// Change
																													// guiView
																													// in
																													// the action function
																													// to the
																													// ActionsIconEditor
		guiView.setIcon(14, new ActionIcon(new ItemStack(Material.SHEARS), true,
				new ConsumerAction((Player currentPlayer) -> GUIsUtils.openSubGUI(player, guiView)))); // TODO Change
																											// guiView
																											// in the
																											// action function to the
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
	public void onDragAndDropEvent(DragAndDropInventoryEvent event, GUIRelation relation) {}

	@Override
	public void onSlotSwapEvent(ItemSlotSwapEvent event) {}

	@Override
	public void onMoveItemToOtherInventoryEvent(MoveItemToOtherInventoryEvent event, GUIRelation relation) {}

	@Override
	public void onPlayerInventoryClickEvent(PlayerInventoryClickEvent event) {
		event.setCancelled(true);
	}
	
	@Override
	public void onGUICloseEvent(GUIView guiView, CloseReason closeReason, Player player) {
		GUIsUtils.defaultOnGUICloseEvent(guiView, closeReason, player);
	}

}
