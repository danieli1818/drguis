package drguis.guis.types.list;

import java.util.List;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import drguis.guis.GUI;
import drguis.guis.common.SlotData;
import drguis.guis.icons.BaseIcon;
import drguis.guis.icons.Icon;
import drguis.guis.types.decorators.general.MapGUIDecorator;

public class GUIsList implements GUI<Icon> {

	private List<GUI<Icon>> guis;
	private SlotData prevGUIIconSlot;
	private SlotData nextGUIIconSlot;
	
	public GUIsList(List<GUI<Icon>> guis, ItemStack prevGUIIconItemStack, ItemStack nextGUIIconItemStack) {
		if (guis == null || guis.isEmpty()) {
			throw new IllegalArgumentException("GUIs list can't be null or empty!");
		}
		int guisSize = guis.get(0).getSize();
		int lastRowIndex = guisSize / 9;
		if (guisSize % 9 == 0) {
			lastRowIndex -= 1;
		}
		SlotData prevGUIIconSlot = new SlotData(lastRowIndex * 9, prevGUIIconItemStack);
		SlotData nextGUIIconSlot = new SlotData(guisSize - 1, nextGUIIconItemStack);
		initialize(guis, prevGUIIconSlot, nextGUIIconSlot);
	}
	
	public GUIsList(List<GUI<Icon>> guis, SlotData prevGUIIconSlot, SlotData nextGUIIconSlot) {
		initialize(guis, prevGUIIconSlot, nextGUIIconSlot);
	}
	
	private void initialize(List<GUI<Icon>> guis, SlotData prevGUIIconSlot, SlotData nextGUIIconSlot) {
		if (guis == null || guis.isEmpty()) {
			throw new IllegalArgumentException("GUIs list can't be null or empty!");
		}
		int guisSize = guis.get(0).getSize();
		for (GUI<Icon> gui : guis) {
			if (gui.getSize() != guisSize) {
				throw new IllegalArgumentException("All GUIs must have the same size!");
			}
		}
		if (this.prevGUIIconSlot.getSlot() < 0 || this.prevGUIIconSlot.getSlot() >= guisSize) {
			throw new IllegalArgumentException("Previous GUI icon slot must be inside the guis!");
		}
		if (this.nextGUIIconSlot.getSlot() < 0 || this.nextGUIIconSlot.getSlot() >= guisSize) {
			throw new IllegalArgumentException("Next GUI icon slot must be inside the guis!");
		}
		this.guis = guis;
		this.prevGUIIconSlot = prevGUIIconSlot;
		this.nextGUIIconSlot = nextGUIIconSlot;
	}

	@Override
	public Inventory getInventory() {
		GUI<Icon> gui = addRelativeGUIs(guis.get(0), 0);
		return gui.getInventory();
	}

	@Override
	public Icon getIconInSlot(int slot) {
		GUI<Icon> gui = addRelativeGUIs(guis.get(0), 0);
		return gui.getIconInSlot(slot);
	}

	@Override
	public boolean onClickOnSlot(Player player, int slot, InventoryClickEvent event) {
		GUI<Icon> gui = addRelativeGUIs(guis.get(0), 0);
		return gui.onClickOnSlot(player, slot, event);
	}

	@Override
	public Inventory getInventory(Player player) {
		GUI<Icon> gui = addRelativeGUIs(guis.get(0), 0);
		return gui.getInventory(player);
	}
	
	private GUI<Icon> addRelativeGUIs(GUI<Icon> gui, int guiIndex) {
		MapGUIDecorator<Icon> newGUI = new MapGUIDecorator<>(gui);
		if (guiIndex != 0) {
			int prevGUIIndex = guiIndex - 1;
			GUI<Icon> prevGUI = guis.get(prevGUIIndex);
			if (prevGUI != null) {
				setPrevGUIIcon(newGUI, prevGUI);
			}
		}
		if (guiIndex != guis.size() - 1) {
			int nextGUIIndex = guiIndex - 1;
			GUI<Icon> nextGUI = guis.get(nextGUIIndex);
			if (nextGUI != null) {
				setNextGUIIcon(newGUI, nextGUI);
			}
		}
		return gui;
	}
	
	private MapGUIDecorator<Icon> setPrevGUIIcon(MapGUIDecorator<Icon> currentGUI, GUI<Icon> prevGUI) {
		Icon icon = new BaseIcon(this.prevGUIIconSlot.getItemStack()).addClickAction((Player player) -> 
			player.openInventory(prevGUI.getInventory(player)));
		currentGUI.setIconInSlot(icon, this.prevGUIIconSlot.getSlot());
		return currentGUI;
	}
	
	private MapGUIDecorator<Icon> setNextGUIIcon(MapGUIDecorator<Icon> currentGUI, GUI<Icon> nextGUI) {
		Icon icon = new BaseIcon(this.nextGUIIconSlot.getItemStack()).addClickAction((Player player) -> 
			player.openInventory(nextGUI.getInventory(player)));
		currentGUI.setIconInSlot(icon, this.nextGUIIconSlot.getSlot());
		return currentGUI;
	}

	@Override
	public int getSize() {
		return this.guis.stream().map((GUI<Icon> gui) -> gui.getSize()).reduce(0, (Integer sum1, Integer sum2) -> sum1 + sum2);
	}
	
}
