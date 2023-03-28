package drguis.models.types.editors;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import drguis.api.GUIsAPI;
import drguis.common.CloseReason;
import drguis.common.Icon;
import drguis.common.events.GUIRelation;
import drguis.common.events.IconClickEvent;
import drguis.common.events.PlayerInventoryClickEvent;
import drguis.common.icons.IconProperties;
import drguis.common.icons.IconPropertiesFields;
import drguis.common.icons.SerializableIcon;
import drguis.common.icons.properties.SimpleIconProperties;
import drguis.common.icons.types.ActionsIcon;
import drguis.common.icons.types.SimpleIcon;
import drguis.common.items.UsefulItemStacks;
import drguis.common.items.UsefulItemStacksIDs;
import drguis.management.GUIsEditorsManager;
import drguis.management.GUIsStack;
import drguis.models.GUIModel;
import drguis.models.types.SimpleGUIModel;
import drguis.models.types.editors.common.CommonGUIMode;
import drguis.models.types.editors.common.factories.ActionsIconsFactory;
import drguis.models.types.editors.common.factories.IconsFactory;
import drguis.models.types.editors.common.factories.SimpleIconsFactory;
import drguis.models.types.editors.common.icons.ModeActionsIcon;
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

public class SimpleGUIEditor implements GUIEditor {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3792701767797637700L;

	private static final String shouldSerializeIconPropertyID = "should_serialize";

	private transient GUIView editorGuiView;

	private transient ModeActionsIcon editModeIcon;

	private transient ModeActionsIcon addIconTypeModeIcon;

	private transient Map<String, IconsFactory> iconsFactories;

	private int pageSize;

	public SimpleGUIEditor(int pageSize, String title) {
		this.pageSize = pageSize;
		int mod = pageSize % 9;
		int editorPageSize = pageSize + (9 - mod);
		if (mod != 0) {
			editorPageSize += 9;
		}
		editorGuiView = new SparseGUIView(this, editorPageSize, title);
		initializeGUI(editorPageSize);
	}

	private void initializeGUI(int editorPageSize) {
		initializeEditModeIcon(editorPageSize);
		initializeAddIconTypeModeIcon(editorPageSize);
		initializeIconsFactories();
		SimpleIconProperties iconProperties = new SimpleIconProperties();
		iconProperties.setBoolean(shouldSerializeIconPropertyID, false);
		Icon nullIcon = new SimpleIcon(new ItemStack(Material.STAINED_GLASS), iconProperties);
		for (int i = pageSize; i < editorPageSize; i++) {
			if (editorGuiView.getIcon(i) == null) {
				editorGuiView.setIcon(i, nullIcon);
			}
		}
	}

	private void initializeEditModeIcon(int editorPageSize) {
		editModeIcon = new ModeActionsIcon(CommonGUIMode.NORMAL);
		SimpleIconProperties iconProperties = new SimpleIconProperties();
		iconProperties.setBoolean(shouldSerializeIconPropertyID, false);
		SimpleIcon normalModeIcon = new SimpleIcon(
				UsefulItemStacks.getInstance().getItemStack(UsefulItemStacksIDs.normalEditingModeItemStack),
				iconProperties);
		SimpleIcon innerEditModeIcon = new SimpleIcon(
				UsefulItemStacks.getInstance().getItemStack(UsefulItemStacksIDs.innerEditingModeItemStack),
				iconProperties);
		editModeIcon.setIcon(CommonGUIMode.NORMAL, normalModeIcon);
		editModeIcon.setIcon(CommonGUIMode.EDIT, innerEditModeIcon);
		editorGuiView.setIcon(editorPageSize - 9, editModeIcon);
	}

	private void initializeAddIconTypeModeIcon(int editorPageSize) {
		addIconTypeModeIcon = new ModeActionsIcon("simple");
		SimpleIconProperties iconProperties = new SimpleIconProperties();
		iconProperties.setBoolean(shouldSerializeIconPropertyID, false);
		addIconTypeModeIcon.setIcon("simple",
				new SimpleIcon(new ItemStackBuilder(Material.DIAMOND_PICKAXE).setDisplayName("Simple Icon")
						.setLoreString("Move items from your inventory to the GUI to create simple icons").build(),
						iconProperties));
//		addIconTypeModeIcon.setIcon("action",
//				new SimpleIcon(new ItemStackBuilder(Material.DIAMOND_PICKAXE).setDisplayName("Action Icon")
//						.setLoreString("Move items from your inventory to the GUI to create action icons").build(),
//						iconProperties));
		addIconTypeModeIcon.setIcon("actions",
				new SimpleIcon(new ItemStackBuilder(Material.DIAMOND_PICKAXE).setDisplayName("Actions Icon")
						.setLoreString("Move items from your inventory to the GUI to create actions icons").build(),
						iconProperties));
	}

	private void initializeIconsFactories() {
		iconsFactories = new HashMap<>();
		IconProperties iconProperties = new SimpleIconProperties()
				.setBoolean(IconPropertiesFields.CANCEL_CLICK_EVENT_FIELD, true);
		iconsFactories.put("simple", new SimpleIconsFactory(iconProperties));
//		iconsFactories.put("action", new ActionIconsFactory(iconProperties));
		iconsFactories.put("actions", new ActionsIconsFactory(iconProperties));
	}

	@Override
	public GUIView getGUI(Player player) {
		GUIView guiView = new SparseGUIView(this, editorGuiView.getSize(), editorGuiView.getTitle());
		for (int i = 0; i < editorGuiView.getSize(); i++) {
			guiView.setIcon(i, editorGuiView.getIcon(i));
		}
		if (GUIsStack.getInstance().hasGUIView(player.getUniqueId())) {
			guiView.setIcon(editorGuiView.getSize() - 8, new PrevGUIIcon());
		}
		System.out.println("Edit Mode Icon: " + editModeIcon.getMode());
		System.out.println("Edit Mode Icon Equals " + CommonGUIMode.NORMAL + " : "
				+ editModeIcon.getMode().equals(CommonGUIMode.NORMAL));
		if (editModeIcon.getMode().equals(CommonGUIMode.NORMAL)) {
			System.out.println("Yay entered if and added addIconTypeModeIcon Icon!");
			guiView.setIcon(editorGuiView.getSize() - 4, addIconTypeModeIcon);
		}
		return guiView;
	}

	@Override
	public void onIconClickEvent(IconClickEvent event) {
		if (event.getIconIndex() >= pageSize) {
			IconsFunctionsUtils.defaultOnIconClickEvent(event);
			System.out.println("Item on cursor: " + event.getPlayer().getItemOnCursor());
			if (event.isCancelled()) {
				event.getPlayer().setItemOnCursor(null);
			}
			GUIsAPI.updateGUIToPlayer(event.getPlayer());
			System.out.println("In onIconClickEvent update!");
			return;
		}
		switch (editModeIcon.getMode()) {
		case CommonGUIMode.EDIT:
			event.setCancelled(true);
			Icon icon = event.getIcon();
			if (icon != null && GUIsEditorsManager.getDefaultInstance().hasEditor(icon)) {
				Player player = event.getPlayer();
				GUIsUtils.openSubGUI(player, GUIsEditorsManager.getDefaultInstance().getEditor(icon)::getGUI);
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
	public void onNormalDragAndDropEvent(NormalDragAndDropInventoryEvent event, GUIRelation relation) {
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
			ItemStack cursorItem = event.getDropEvent().getCursor();
			int dropSlot = event.getDropEvent().getSlot();
			if (dropSlot < 0) { // Dropping item outside the inventory
				event.getPlayer().setItemOnCursor(null);
			} else if (cursorItem != null) {
				event.getPlayer().getInventory().setItem(dropSlot, new ItemStack(cursorItem));
			}
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
			System.out.println("Is Cancelled: " + event.getDropEvent().isCancelled());
//			event.getDropEvent().setCancelled(true);
			toSlot = event.getDropEvent().getSlot();
			toIcon = editorGuiView.getIcon(toSlot);
			if (toIcon == null) {
				editorGuiView.setIcon(toSlot, iconsFactories.get(addIconTypeModeIcon.getMode())
						.apply(new ItemStack(event.getDropEvent().getCursor())));
			} else { // TODO change to keep the actions
				toIcon.setItemStack(new ItemStack(event.getDropEvent().getCursor()));
			}
			break;
		}
		System.out.println("First Icon In Editor GUI View: " + editorGuiView.getIcon(0));
		System.out.println("Icons In Editor GUI View: " + editorGuiView.getIcons());
		System.out.println("Editor GUI View: " + editorGuiView);

		event.getDropEvent().setCancelled(true);
		event.getPlayer().setItemOnCursor(null);
		GUIsAPI.updateGUIToPlayer(event.getPlayer());
	}

	@Override
	public void onDragInventoryDragAndDropEvent(DragInventoryDragAndDropInventoryEvent event, boolean isFromGUI) {
		System.out.println("Drag Inventory Event: " + event);
		System.out.println("Inventory Slots: " + event.getDropEvent().getInventorySlots());
		System.out.println("New Items: " + event.getDropEvent().getNewItems());
		System.out.println("Old Cursor: " + event.getDropEvent().getOldCursor());
		System.out.println("Cursor: " + event.getDropEvent().getCursor());
		InventoryClickEvent startDragEvent = event.getStartDragEvent();
		int fromSlot = startDragEvent.getSlot();
		Map.Entry<Map<Integer, ItemStack>, Map<Integer, ItemStack>> topAndBottomInventoriesNewItems = GUIsUtils
				.getTopAndBottomInventoriesNewItems(event.getDropEvent().getNewItems(), event.getDropEvent().getView());
		Map<Integer, ItemStack> topInventoryNewItems = topAndBottomInventoriesNewItems.getKey();
		Map<Integer, ItemStack> bottomInventoryNewItems = topAndBottomInventoriesNewItems.getValue();
		if (isFromGUI) {
			Icon fromIcon = editorGuiView.getIcon(fromSlot);
			for (Map.Entry<Integer, ItemStack> entry : topInventoryNewItems.entrySet()) {
				Icon newIcon = fromIcon.cloneIcon();
				newIcon.setItemStack(new ItemStack(entry.getValue()));
				editorGuiView.setIcon(entry.getKey(), newIcon);
			}
			if (!topInventoryNewItems.containsKey(fromSlot)) {
				editorGuiView.setIcon(fromSlot, null);
			}
		} else {
			for (Map.Entry<Integer, ItemStack> entry : topInventoryNewItems.entrySet()) {
				Icon newIcon = iconsFactories.get(addIconTypeModeIcon.getMode()).apply(new ItemStack(entry.getValue()));
				editorGuiView.setIcon(entry.getKey(), newIcon);
			}
		}
		for (Map.Entry<Integer, ItemStack> entry : bottomInventoryNewItems.entrySet()) {
			event.getPlayer().getInventory().setItem(entry.getKey(), new ItemStack(entry.getValue()));
		}
//		if (isFromGUI) {
//			Icon fromIcon = editorGuiView.getIcon(fromSlot);
//			Map<Integer, ItemStack> newItems = dropEvent.getNewItems();
//			System.out.println("Drop Event Slots: " + dropEvent.getInventorySlots());
//			System.out.println("Drop Event Inventory Contents: " + dropEvent.getInventory().getContents());
//			if (!dropEvent.getInventorySlots().contains(fromSlot)) {
//				if (dropEvent.getInventorySlots().size() == 1) {
//					int dropSlot = dropEvent.getInventorySlots().iterator().next();
//					if (dropSlot < 0) { // dropping item
//						editorGuiView.setIcon(fromSlot, null);
//					} else if (dropSlot < pageSize) { // swapping icons
//						Icon dropIcon = editorGuiView.getIcon(dropSlot);
//						editorGuiView.setIcon(dropSlot, editorGuiView.getIcon(fromSlot));
//						editorGuiView.setIcon(fromSlot, dropIcon);
//					} else { // to player's inventory
//						editorGuiView.setIcon(fromSlot, null);
//						event.getPlayer().getInventory().setItem(dropSlot, new ItemStack(event.getPlayer().getItemOnCursor()));
//						// TODO Set item in player's inventory
//					}
//				} else {
//					editorGuiView.setIcon(fromSlot, null);
//					for (Integer slot : dropEvent.getInventorySlots()) { // TODO Change the way of copying icons to clone
//						// function of the Icon
//						System.out.println("Slot: " + slot + " has: " + newItems.get(slot));
//						if (newItems.get(slot) == null) {
//							editorGuiView.setIcon(slot, null);
//						} else {
//							ActionsIcon icon = new ActionsIcon(new ItemStack(newItems.get(slot)), true);
//							for (Action action : fromIcon.getActions()) {
//								icon.addAction(action);
//							}
//							editorGuiView.setIcon(slot, icon);
//						}
//					}
//				}
//			} else {
//				for (Integer slot : dropEvent.getInventorySlots()) { // TODO Change the way of copying icons to clone
//					// function of the Icon
//					System.out.println("Slot: " + slot + " has: " + newItems.get(slot));
//					if (newItems.get(slot) == null) {
//						editorGuiView.setIcon(slot, null);
//					} else {
//						ActionsIcon icon = new ActionsIcon(new ItemStack(newItems.get(slot)), true);
//						for (Action action : fromIcon.getActions()) {
//							icon.addAction(action);
//						}
//						editorGuiView.setIcon(slot, icon);
//					}
//				}
//			}
//		} else {
//			Map<Integer, ItemStack> newItems = dropEvent.getNewItems();
//			System.out.println("Drop Event Slots 2: " + dropEvent.getInventorySlots());
//			for (Integer slot : dropEvent.getInventorySlots()) { // TODO Change the way of copying icons to clone
//																	// function of the Icon
//				System.out.println("Slot 2: " + slot + " has: " + newItems.get(slot));
//				Icon icon = iconsFactories.get(addIconTypeModeIcon.getMode()).apply(new ItemStack(newItems.get(slot)));
//				editorGuiView.setIcon(slot, icon);
//			}
//		}

//		ItemStack fromItemStack = startDragEvent.getCurrentItem();
//		ActionsIcon icon = new ActionsIcon(new ItemStack(fromItemStack), true); // TODO Change the way of copying icons
//																				// to clone
//																				// function of the Icon
//		for (Action action : fromIcon.getActions()) {
//			icon.addAction(action);
//		}
//		editorGuiView.setIcon(fromSlot, icon);

		event.getDropEvent().setCursor(null);
//		event.getDropEvent().setCancelled(true);
		event.getPlayer().setItemOnCursor(null);
		GUIsAPI.updateGUIToPlayer(event.getPlayer());
		event.getPlayer().setItemOnCursor(null);
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
	public void onPlayerInventoryClickEvent(PlayerInventoryClickEvent event) {
	}

	@Override
	public void onGUICloseEvent(GUIView guiView, CloseReason closeReason, Player player) {
		GUIsUtils.defaultOnGUICloseEvent(guiView, closeReason, player);
	}

	private void writeObject(ObjectOutputStream oos) throws IOException {
		oos.defaultWriteObject();
		oos.writeInt(editorGuiView.getSize());
		oos.writeObject(editorGuiView.getTitle());
		oos.writeObject(getIconsToSerialize());
	}

	private Map<Integer, SerializableIcon> getIconsToSerialize() {
		Map<Integer, SerializableIcon> iconsToSerialize = new HashMap<>();
		Map<Integer, Icon> icons = editorGuiView.getIcons();
		for (Map.Entry<Integer, Icon> iconEntry : icons.entrySet()) {
			Icon icon = iconEntry.getValue();
			if (icon == null || !(icon instanceof SerializableIcon) || (icon.getProperties() != null
					&& Boolean.FALSE.equals(icon.getProperties().getBoolean(shouldSerializeIconPropertyID)))) {
				continue;
			}
			iconsToSerialize.put(iconEntry.getKey(), (SerializableIcon) icon);
		}
		return iconsToSerialize;
	}

	@SuppressWarnings("unchecked")
	private void readObject(ObjectInputStream ois) throws ClassNotFoundException, IOException {
		ois.defaultReadObject();
		int editorPageSize = ois.readInt();
		String title = (String) ois.readObject();
		Map<Integer, SerializableIcon> icons = (Map<Integer, SerializableIcon>) ois.readObject();
		editorGuiView = new SparseGUIView(this, editorPageSize, title);
		for (Map.Entry<Integer, SerializableIcon> iconEntry : icons.entrySet()) {
			editorGuiView.setIcon(iconEntry.getKey(), iconEntry.getValue());
		}
		initializeGUI(editorPageSize);
	}

	@Override
	public GUIView getUpdatedGUI(Player player, GUIView prevGUIView) {
		return getGUI(player);
	}

}
