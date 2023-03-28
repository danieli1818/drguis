package drguis.models.types.editors.common.guis.icons.actions;

import java.util.Collections;
import java.util.Map;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.ItemStack;

import drguis.api.GUIsAPI;
import drguis.common.Action;
import drguis.common.CloseReason;
import drguis.common.Icon;
import drguis.common.Region;
import drguis.common.actions.OpenGUIAction;
import drguis.common.events.GUIRelation;
import drguis.common.events.IconClickEvent;
import drguis.common.events.PlayerInventoryClickEvent;
import drguis.common.icons.types.ActionsIcon;
import drguis.common.icons.types.SimpleIcon;
import drguis.common.icons.utils.IconMetadata;
import drguis.common.regions.SeriesRegion;
import drguis.management.GUIsStack;
import drguis.models.GUIModel;
import drguis.models.types.editors.common.icons.ModeActionsIcon;
import drguis.models.types.editors.common.icons.PrevGUIIcon;
import drguis.models.types.list.IconsListGUIModel;
import drguis.models.utils.IconsFunctionsUtils;
import drguis.utils.GUIsUtils;
import drguis.views.GUIView;
import drlibs.events.inventory.DragAndDropInventoryEvent;
import drlibs.events.inventory.DragInventoryDragAndDropInventoryEvent;
import drlibs.events.inventory.NormalDragAndDropInventoryEvent;

public class ActionsEditor extends IconsListGUIModel implements GUIModel {

	private ActionsIcon icon;
	private ModeActionsIcon modeIcon;
	private ActionsIcon addActionIcon;

	public ActionsEditor(ActionsIcon icon, int numOfRows) {
		super(new SeriesRegion((numOfRows - 1) * 9), "Actions Editor Page {PAGE_NUMBER}", numOfRows * 9,
				new IconMetadata(new ItemStack(Material.ARROW), (numOfRows - 1) * 9 + 2),
				new IconMetadata(new ItemStack(Material.ARROW), (numOfRows * 9) - 1));
		this.icon = icon;
		modeIcon = new ModeActionsIcon("swap");
		modeIcon.setIcon("swap", new SimpleIcon(new ItemStack(Material.DIAMOND_PICKAXE), true));
		modeIcon.setIcon("move", new SimpleIcon(new ItemStack(Material.DIAMOND_SWORD), true));
		addActionIcon = new ActionsIcon(new ItemStack(Material.APPLE), true);
		addActionIcon.addAction(new OpenGUIAction((Player player) -> new AddActionEditor(this.icon).getGUI(player),
				CloseReason.OPENING_GUI));
		for (Action action : icon.getActions()) {
			addIcon(action.getActionIcon());
		}
	}

	@Override
	public GUIView getGUIPage(Player player, int pageIndex) {
		GUIView guiPage = super.getGUIPage(player, pageIndex);
		guiPage.setIcon(getGuiPageSize() - 9, modeIcon);
		if (GUIsStack.getInstance().hasGUIView(player.getUniqueId())) {
			guiPage.setIcon(getGuiPageSize() - 8, new PrevGUIIcon());
		}
		guiPage.setIcon(getGuiPageSize() - 5, addActionIcon);
		return guiPage;
	}

	@Override
	public GUIView getGUI(Player player) {
		return getGUIPage(player, 0); // TODO Check if it is removable and still work since the parent class's
										// function
	}

	@Override
	public void onIconClickEvent(IconClickEvent event) {
		int iconSlot = event.getIconIndex();
		if (!getRegion().isInRegion(iconSlot)) {
			ItemStack cursorItem = event.getPlayer().getItemOnCursor();
			if (cursorItem == null || cursorItem.getType() == Material.AIR) {
				IconsFunctionsUtils.defaultOnIconClickEvent(event);
				GUIsAPI.updateGUIToPlayer(event.getPlayer());
			}
		}
		// TODO Add support to changing places of actions icons in order to change the
		// order
//		if (event.getIconIndex() >= getGuiPageSize() - 9) {
//			IconsFunctionsUtils.defaultOnIconClickEvent(event);
//			GUIsAPI.updateGUIToPlayer(event.getPlayer());
//			return;
//		}
//		switch (modeIcon.getMode()) {
//		case EDIT:
//			event.setCancelled(true);
//			Icon icon = event.getIcon();
//			if (icon != null) {
//				Player player = event.getPlayer();
//				GUIsUtils.openSubGUI(player, new ActionsIconEditor(icon)::getGUI);
//			}
//			break;
//		default: // NORMAL
//			break;
//		}
	}

	@Override
	public void onPlayerInventoryClickEvent(PlayerInventoryClickEvent event) {
		System.out.println("Yay in onPlayerInventoryClickEvent!");
		if (event.getPlayer().getItemOnCursor() == null
				|| event.getPlayer().getItemOnCursor().getType() == Material.AIR) {
			event.setCancelled(true);
		}
	}

	@Override
	public void onNormalDragAndDropEvent(NormalDragAndDropInventoryEvent event, GUIRelation relation) {
		int fromSlot = event.getStartDragEvent().getSlot();
		int toSlot = event.getDropEvent().getSlot();
		handleDragAndDropEvent(event, fromSlot, toSlot, relation, true);
	}

	@Override
	public void onDragInventoryDragAndDropEvent(DragInventoryDragAndDropInventoryEvent event, boolean isFromGUI) {
		System.out.println("Yay in drag inventory drag and drop event!");
		InventoryDragEvent dropEvent = event.getDropEvent();
		Map.Entry<Map<Integer, ItemStack>, Map<Integer, ItemStack>> topAndBottomInventoriesNewItems = GUIsUtils
				.getTopAndBottomInventoriesNewItems(dropEvent.getNewItems(), dropEvent.getView());
		Map<Integer, ItemStack> topInventoryNewItems = topAndBottomInventoriesNewItems.getKey();
		if (topInventoryNewItems.isEmpty()) {
			return;
		}
		System.out.println("Player's cursor:" + event.getPlayer().getItemOnCursor());
		System.out.println("Drop event's cursor:" + event.getDropEvent().getCursor());
		handleDragAndDropEvent(event, event.getStartDragEvent().getSlot(),
				Collections.min(topInventoryNewItems.keySet()), isFromGUI ? GUIRelation.INNER : GUIRelation.TO, false);
	}

	private void handleDragAndDropEvent(DragAndDropInventoryEvent event, int fromSlot, int toSlot, GUIRelation relation,
			boolean cancelIfNeeded) {
		System.out.println("Yay inside on normal drag and drop event!");
		System.out.println("GUI relation: " + relation);
		System.out.println("From Slot: " + fromSlot);
		System.out.println("To Slot: " + toSlot);
		switch (relation) {
		case INNER:
			if (fromSlot != toSlot) {
				System.out.println("Yay in normal drag and drop event!");
				Region region = getRegion();
				if (!region.isInRegion(fromSlot)) {
					System.out.println("Canceling drop event since from slot isn't in the region!");
					if (cancelIfNeeded) {
						cancelDropEvent(event);
					}
				} else {
					if (region.isInRegion(toSlot)) {
						System.out.println("From Slot: " + fromSlot + " is in region: " + region.isInRegion(fromSlot));
						handleDragAndDropEventInRegion(event, fromSlot, toSlot, cancelIfNeeded);
					} else {
						if (toSlot == getPrevPageIconIndex() || toSlot == getNextPageIconIndex()) {
							System.out.println("Yay, In prev/next icon drag and drop!");
							ItemStack cursorItemStack = event.getPlayer().getItemOnCursor();
							event.setCancelled(true);
							System.out.println("Yay default on icon click event: "
									+ GUIsUtils.getOpenGUIView(event.getPlayer()).getIcon(toSlot));
							IconsFunctionsUtils.defaultOnIconClickEvent(
									GUIsUtils.getOpenGUIView(event.getPlayer()).getIcon(toSlot), event.getPlayer());
							GUIsAPI.updateGUIToPlayer(event.getPlayer());
							if (cursorItemStack != null) {
								event.getPlayer().setItemOnCursor(new ItemStack(cursorItemStack));
							}
							break;
						} else {
							System.out.println("Canceling drop event since to slot isn't in the region!");
							if (cancelIfNeeded) {
								cancelDropEvent(event);
							}
						}
					}
				}
			}
			GUIsAPI.updateGUIToPlayer(event.getPlayer());
			break;
		case FROM:
			System.out.println("Yay inside FROM!");
			Region region = getRegion();
			if (region.isInRegion(fromSlot)) {
				System.out.println("Yay inside the region!");
				GUIView guiView = GUIsUtils.getOpenGUIView(event.getPlayer());
				int fromIconIndex = getIconIndex(guiView, fromSlot);
				icon.removeAction(fromIconIndex);
				GUIsAPI.updateGUIToPlayer(event.getPlayer());
				break;
			}
//			if (toSlot < 0) { // Dropping item outside the inventory
//				System.out.println("Yay inside dropping item outside the inventory!");
//				Region region = getRegion();
//				if (region.isInRegion(fromSlot)) {
//					System.out.println("Yay inside the region!");
//					int fromRegionSlot = region.getRegionIndex(fromSlot);
//					icon.setAction(fromRegionSlot, null);
//					break;
//				}
//			}
		default:
			event.setCancelled(true); // TODO Check if works else change to cancelDropEvent
		}
	}

	private void handleDragAndDropEventInRegion(DragAndDropInventoryEvent event, int fromSlot, int toSlot,
			boolean cancelIfNeeded) {
		GUIView guiView = GUIsUtils.getOpenGUIView(event.getPlayer());
		GUIView fromSlotGUIView = guiView;
		Object fromSlotClickedInventoryHolder = event.getStartDragEvent().getClickedInventory().getHolder();
		if (fromSlotClickedInventoryHolder instanceof GUIView) {
			fromSlotGUIView = (GUIView) fromSlotClickedInventoryHolder;
		}
		int fromIconIndex = getIconIndex(fromSlotGUIView, fromSlot);
		int toIconIndex = getIconIndex(guiView, toSlot);
		Region region = getRegion();
		switch (modeIcon.getMode()) {
		case "swap":
			if (toIconIndex >= icon.getActions().size()) {
				if (cancelIfNeeded) {
					cancelDropEvent(event);
				}
			} else {
				int fromRegionSlot = region.getRegionIndex(fromSlot);
				int toRegionSlot = region.getRegionIndex(toSlot);
				System.out.println("Swapping " + fromRegionSlot + " and " + toRegionSlot);
				System.out.println("Class of start drag event: "
						+ event.getStartDragEvent().getClickedInventory().getHolder().getClass());
				Action fromAction = icon.getActions().get(fromIconIndex);
				icon.setAction(fromIconIndex, icon.getActions().get(toIconIndex));
				icon.setAction(toIconIndex, fromAction);
				Icon fromIcon = getIcon(fromIconIndex);
				if (fromSlotGUIView == guiView) {
					setIcon(fromRegionSlot, getIcon(toRegionSlot));
				}
				setIcon(toRegionSlot, fromIcon);
				event.getPlayer().setItemOnCursor(null);
			}
			break;
		case "move":
			icon.moveAction(fromIconIndex, toIconIndex);
			break;
		default:
			break;
		}
	}

	private void cancelDropEvent(DragAndDropInventoryEvent event) {
		Player player = event.getPlayer();
		event.setCancelled(true);
		player.setItemOnCursor(null);
	}

	@Override
	public GUIView getUpdatedGUI(Player player, GUIView prevGUIView) {
		clearIcons();
		for (Action action : icon.getActions()) {
			addIcon(action.getActionIcon());
		}
		int pageNumber = getPageNumberOfGUIView(prevGUIView);
		return getGUIPage(player, pageNumber);
//		int pageNumber = getPageNumberOfGUIView(prevGUIView);
//		if (pageNumber < 0) {
//			return null;
//		}
//		ActionsEditor actionsEditor = new ActionsEditor(icon, getGuiPageSize() / 9);
//		if (actionsEditor.getNumOfPages(player) < pageNumber) {
//			pageNumber = 0;
//		}
//		return actionsEditor.getGUIPage(player, pageNumber);
	}

	@Override
	public void onGUICloseEvent(GUIView guiView, CloseReason closeReason, Player player) {
		super.onGUICloseEvent(guiView, closeReason, player);
		player.setItemOnCursor(null);
	}

}
