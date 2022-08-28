package drguis.guis.types.decorators.data.list;

import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

import drguis.guis.icons.Icon;
import drguis.guis.icons.spaces.Space;
import drguis.guis.types.DataGUI;
import drguis.guis.types.list.GUIsList;

public abstract class GUIsListDataDecorator<T extends Icon> implements GUIsList<T> {

	private GUIsList<T> gui;

	public GUIsListDataDecorator(GUIsList<T> gui) {
		this.gui = gui;
	}

	@Override
	public T getDataIconInSlot(int slot) {
		return gui.getDataIconInSlot(slot);
	}

	@Override
	public Space getDataIconsSpace() {
		return gui.getDataIconsSpace();
	}

	@Override
	public Icon getIconInSlot(int slot) {
		return gui.getIconInSlot(slot);
	}

	@Override
	public boolean onClickOnSlot(Player player, int slot, InventoryClickEvent event) {
		return gui.onClickOnSlot(player, slot, event);
	}

	@Override
	public Inventory getInventory(Player player) {
		Inventory inventory = gui.getInventory(player);
		Inventory decoratedInventory = Bukkit.createInventory(this, inventory.getSize(), inventory.getTitle());
		decoratedInventory.setContents(inventory.getContents());
		return decoratedInventory;
	}

	@Override
	public int getSize() {
		return gui.getSize();
	}

	@Override
	public Inventory getInventory() {
		Inventory inventory = gui.getInventory();
		Inventory decoratedInventory = Bukkit.createInventory(this, inventory.getSize(), inventory.getTitle());
		decoratedInventory.setContents(inventory.getContents());
		return decoratedInventory;
	}

	@Override
	public Map<Integer, T> getDataIcons() {
		return gui.getDataIcons();
	}

	@Override
	public Map<Integer, T> getDataIcons(Player player) {
		return gui.getDataIcons(player);
	}

	@Override
	public int getGUISize() {
		return gui.getGUISize();
	}

	@SuppressWarnings("unchecked")
	@Override
	public DataGUI<T> getGUIInIndex(int guiIndex) {
		return (DataGUI<T>) gui.getGUIInIndex(guiIndex);
	}

}
