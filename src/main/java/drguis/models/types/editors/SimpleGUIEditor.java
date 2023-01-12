package drguis.models.types.editors;

import java.util.Map;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import drguis.api.GUIsAPI;
import drguis.common.CloseReason;
import drguis.common.events.GUIRelation;
import drguis.management.GUIsStack;
import drguis.models.GUIModel;
import drguis.models.types.SimpleGUIModel;
import drguis.models.types.editors.common.GUIMode;
import drguis.models.types.editors.common.guis.ActionsIconMenuEditor;
import drguis.models.types.editors.common.icons.ModeActionsIcon;
import drguis.models.types.editors.common.icons.PrevGUIIcon;
import drguis.models.utils.IconsFunctionsUtils;
import drguis.utils.GUIsUtils;
import drguis.views.GUIView;
import drguis.views.common.Icon;
import drguis.views.common.events.IconClickEvent;
import drguis.views.common.events.PlayerInventoryClickEvent;
import drguis.views.common.icons.types.ActionsIcon;
import drguis.views.common.icons.types.SimpleIcon;
import drguis.views.types.SparseGUIView;
import drlibs.events.inventory.DragAndDropInventoryEvent;
import drlibs.events.inventory.ItemSlotSwapEvent;
import drlibs.events.inventory.moveitemtootherinventory.MoveItemToOtherInventoryEvent;

public class SimpleGUIEditor implements GUIEditor {

	private GUIView editorGuiView;

	private ModeActionsIcon modeIcon;
	
	private int pageSize;

	public SimpleGUIEditor(int pageSize, String title) {
		this.pageSize = pageSize;
		int mod = pageSize % 9;
		int editorPageSize = pageSize + (9 - mod);
		if (mod != 0) {
			editorPageSize += 9;
		}
		editorGuiView = new SparseGUIView(this, editorPageSize, title);
		modeIcon = new ModeActionsIcon();
		modeIcon.setIcon(GUIMode.NORMAL, new SimpleIcon(new ItemStack(Material.DIAMOND_PICKAXE), true));
		modeIcon.setIcon(GUIMode.EDIT, new SimpleIcon(new ItemStack(Material.DIAMOND_SWORD), true)); // REMOVE
		editorGuiView.setIcon(editorPageSize - 9, modeIcon);
		Icon nullIcon = new SimpleIcon(new ItemStack(Material.STAINED_GLASS), true);
		for (int i = pageSize; i < editorPageSize; i++) {
			if (editorGuiView.getIcon(i) == null) {
				editorGuiView.setIcon(i, nullIcon);
			}
		}
	}

	@Override
	public GUIView getGUI(Player player) {
		if (GUIsStack.getInstance().hasGUIView(player.getUniqueId())) {
			GUIView guiView = new SparseGUIView(this, editorGuiView.getSize(), editorGuiView.getTitle());
			for (int i = 0; i < editorGuiView.getSize(); i++) {
				guiView.setIcon(i, editorGuiView.getIcon(i));
			}
			guiView.setIcon(editorGuiView.getSize() - 8, new PrevGUIIcon());
			return guiView;
		}
		return editorGuiView;
	}

	@Override
	public void onIconClickEvent(IconClickEvent event) {
		if (event.getIconIndex() >= pageSize) {
			IconsFunctionsUtils.defaultOnIconClickEvent(event);
			GUIsAPI.updateGUIToPlayer(event.getPlayer());
			return;
		}
		switch (modeIcon.getMode()) {
		case EDIT:
			event.setCancelled(true);
			Icon icon = event.getIcon();
			if (icon != null) {
				Player player = event.getPlayer();
				GUIsUtils.openSubGUI(player, new ActionsIconMenuEditor(icon)::getGUI);
			}
			break;
		default: // NORMAL
			break;
		}

	}

	@Override
	public GUIModel getGUIModel() {
		GUIView guiView = new SparseGUIView(pageSize, editorGuiView.getTitle());
		for (int i = 0; i < pageSize; i++) {
			guiView.setIcon(i, editorGuiView.getIcon(i));
		}
		return new SimpleGUIModel(guiView);
	}

	@Override
	public void onDragAndDropEvent(DragAndDropInventoryEvent event, GUIRelation relation) {
		System.out.println("Yay entered SimpleGUIEditor's onDragAndDropEvent!");
		int fromSlot;
		int toSlot;
		Icon fromIcon;
		Icon toIcon;
		switch (relation) {
		case FROM:
			System.out.println("FROM");
			fromSlot = event.getStartDragEvent().getSlot();
			editorGuiView.setIcon(fromSlot, null);
			break;
		case INNER:
			System.out.println("INNER");
			fromSlot = event.getStartDragEvent().getSlot();
			toSlot = event.getDropEvent().getSlot();
			fromIcon = editorGuiView.getIcon(fromSlot);
			toIcon = editorGuiView.getIcon(toSlot);
			editorGuiView.setIcon(fromSlot, toIcon);
			editorGuiView.setIcon(toSlot, fromIcon);
			break;
		default: // TO
			System.out.println("TO");
			toSlot = event.getDropEvent().getSlot();
			toIcon = editorGuiView.getIcon(toSlot);
			if (toIcon == null) {
				editorGuiView.setIcon(toSlot, new ActionsIcon(new ItemStack(event.getDropEvent().getCursor()), true));
			} else { // TODO change to keep the actions
				editorGuiView.setIcon(toSlot, new ActionsIcon(new ItemStack(event.getDropEvent().getCursor()), true));
			}
			break;
		}
		event.getPlayer().setItemOnCursor(null);
		GUIsAPI.updateGUIToPlayer(event.getPlayer());
	}

	@Override
	public void onSlotSwapEvent(ItemSlotSwapEvent event) {
		int fromSlot = event.getClickedSlot();
		editorGuiView.setIcon(fromSlot, null);
		GUIsAPI.updateGUIToPlayer((Player) event.getSwapClickEvent().getWhoClicked());
	}

	@Override
	public void onMoveItemToOtherInventoryEvent(MoveItemToOtherInventoryEvent event, GUIRelation relation) {
		System.out.println("Yay entered SimpleGUIEditor's onDragAndDropEvent!");
		switch (relation) {
		case FROM:
			System.out.println("FROM");
			int fromSlot = event.getFromSlot();
			editorGuiView.setIcon(fromSlot, null);
			break;
		default: // TO
			System.out.println("TO");
			Map<Integer, Integer> toSlotsAmounts = event.getToInventorySlotsAmounts();
			for (Map.Entry<Integer, Integer> toSlotsAmountsEntry : toSlotsAmounts.entrySet()) {
				ItemStack iconItemStack = new ItemStack(event.getItemStack());
				iconItemStack.setAmount(toSlotsAmountsEntry.getValue());
				editorGuiView.setIcon(toSlotsAmountsEntry.getKey(), new ActionsIcon(iconItemStack, true));
			}
			break;
		}
		GUIsAPI.updateGUIToPlayer((Player) event.getSwapClickEvent().getWhoClicked());
	}

	@Override
	public void onPlayerInventoryClickEvent(PlayerInventoryClickEvent event) {}
	
	@Override
	public void onGUICloseEvent(GUIView guiView, CloseReason closeReason, Player player) {
		GUIsUtils.defaultOnGUICloseEvent(guiView, closeReason, player);
	}

}
