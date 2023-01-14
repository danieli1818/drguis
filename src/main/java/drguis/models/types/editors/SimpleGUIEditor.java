package drguis.models.types.editors;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import drguis.api.GUIsAPI;
import drguis.common.CloseReason;
import drguis.common.Icon;
import drguis.common.events.GUIRelation;
import drguis.common.events.IconClickEvent;
import drguis.common.events.PlayerInventoryClickEvent;
import drguis.common.icons.SerializableIcon;
import drguis.common.icons.properties.SimpleIconProperties;
import drguis.common.icons.types.ActionsIcon;
import drguis.common.icons.types.SimpleIcon;
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
import drguis.views.types.SparseGUIView;
import drlibs.events.inventory.DragAndDropInventoryEvent;
import drlibs.events.inventory.ItemSlotSwapEvent;
import drlibs.events.inventory.moveitemtootherinventory.MoveItemToOtherInventoryEvent;
import drlibs.items.ItemStackBuilder;

public class SimpleGUIEditor implements GUIEditor {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3792701767797637700L;

	private static final String shouldSerializeIconPropertyID = "should_serialize";

	private transient GUIView editorGuiView;

	private transient ModeActionsIcon modeIcon;

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
		initializeModeIcon(editorPageSize);
		SimpleIconProperties iconProperties = new SimpleIconProperties();
		iconProperties.setBoolean(shouldSerializeIconPropertyID, false);
		Icon nullIcon = new SimpleIcon(new ItemStack(Material.STAINED_GLASS), iconProperties);
		for (int i = pageSize; i < editorPageSize; i++) {
			if (editorGuiView.getIcon(i) == null) {
				editorGuiView.setIcon(i, nullIcon);
			}
		}
	}

	private void initializeModeIcon(int editorPageSize) {
		modeIcon = new ModeActionsIcon();
		SimpleIconProperties iconProperties = new SimpleIconProperties();
		iconProperties.setBoolean(shouldSerializeIconPropertyID, false);
		SimpleIcon normalModeIcon = new SimpleIcon(
				new ItemStackBuilder(Material.DIAMOND_PICKAXE).setDisplayName("Normal Editing Mode")
						.setLoreString("Drag and drop items on the GUI to edit it").build(),
				iconProperties);
		SimpleIcon innerEditModeIcon = new SimpleIcon(new ItemStackBuilder(Material.DIAMOND_SWORD)
				.setDisplayName("Inner Editing Mode").setLoreString("Click on an icon to edit it").build(),
				iconProperties);
		modeIcon.setIcon(GUIMode.NORMAL, normalModeIcon);
		modeIcon.setIcon(GUIMode.EDIT, innerEditModeIcon);
		editorGuiView.setIcon(editorPageSize - 9, modeIcon);
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
					&& icon.getProperties().getBoolean(shouldSerializeIconPropertyID) == false)) {
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

}
