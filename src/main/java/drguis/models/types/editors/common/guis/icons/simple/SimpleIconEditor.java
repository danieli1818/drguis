package drguis.models.types.editors.common.guis.icons.simple;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import drguis.api.GUIsAPI;
import drguis.common.CloseReason;
import drguis.common.events.GUIRelation;
import drguis.common.events.IconClickEvent;
import drguis.common.events.PlayerInventoryClickEvent;
import drguis.common.icons.types.SimpleIcon;
import drguis.common.items.UsefulItemStacks;
import drguis.common.items.UsefulItemStacksIDs;
import drguis.management.GUIsStack;
import drguis.models.GUIModel;
import drguis.models.types.editors.common.icons.PrevGUIIcon;
import drguis.models.utils.IconsFunctionsUtils;
import drguis.utils.GUIsUtils;
import drguis.views.GUIView;
import drguis.views.types.SparseGUIView;
import drlibs.events.inventory.DragInventoryDragAndDropInventoryEvent;
import drlibs.events.inventory.ItemSlotSwapEvent;
import drlibs.events.inventory.NormalDragAndDropInventoryEvent;
import drlibs.events.inventory.moveitemtootherinventory.MoveItemToOtherInventoryEvent;
import drlibs.items.ItemStackBuilder;

public class SimpleIconEditor implements GUIModel {

	private SimpleIcon icon;

	public SimpleIconEditor(SimpleIcon icon) {
		this.icon = icon;
	}

	@Override
	public GUIView getGUI(Player player) {
		GUIView guiView = new SparseGUIView(this, 36, "Icon Editor"); // TODO Change string to be retrieved from
		// MessagesStorage
		guiView.setIcon(4, icon);
		guiView.setIcon(13, new SimpleIcon(
				new ItemStackBuilder(UsefulItemStacks.getInstance().getItemStack(UsefulItemStacksIDs.helpItemStack))
						.setLoreString("Click on an item from your inventory to set it as the icon").build(),
				false));
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
	public void onPlayerInventoryClickEvent(PlayerInventoryClickEvent event) {
		event.setCancelled(true);
		InventoryClickEvent inventoryClickEvent = event.getInventoryClickEvent();
		ItemStack itemStack = inventoryClickEvent.getCurrentItem();
		if (itemStack != null) {
			icon.setItemStack(new ItemStack(itemStack));
		}
		GUIsAPI.updateGUIToPlayer(event.getPlayer());
	}

	@Override
	public void onNormalDragAndDropEvent(NormalDragAndDropInventoryEvent event, GUIRelation relation) {
	}

	@Override
	public void onDragInventoryDragAndDropEvent(DragInventoryDragAndDropInventoryEvent event, boolean isFromGUI) {
	}

	@Override
	public void onSlotSwapEvent(ItemSlotSwapEvent event) {
		event.setCancelled(true);
	}

	@Override
	public void onMoveItemToOtherInventoryEvent(MoveItemToOtherInventoryEvent event, GUIRelation relation) {
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
