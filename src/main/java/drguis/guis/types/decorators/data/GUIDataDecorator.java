package drguis.guis.types.decorators.data;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

import drguis.guis.icons.Icon;
import drguis.guis.icons.spaces.Space;
import drguis.guis.types.DataGUI;

public abstract class GUIDataDecorator<T extends Icon> implements DataGUI<T> {

	private DataGUI<T> gui;
	
	public GUIDataDecorator(DataGUI<T> gui) {
		this.gui = gui;
	}
	
	@Override
	public Inventory getInventory() {
		return gui.getInventory();
	}

	@Override
	public T getDataIconInSlot(int slot) {
		return gui.getDataIconInSlot(slot);
	}

	@Override
	public boolean onClickOnSlot(Player player, int slot, InventoryClickEvent event) {
		return gui.onClickOnSlot(player, slot, event);
	}

	@Override
	public Inventory getInventory(Player player) {
		Inventory inventory = gui.getInventory();
		Inventory decoratedInventory = Bukkit.createInventory(this, inventory.getSize(), inventory.getTitle());
		decoratedInventory.setContents(inventory.getContents());
		return decoratedInventory;
	}

	public final DataGUI<T> getGUI() {
		return this.gui;
	}
	
	@Override
	public int getSize() {
		return this.gui.getSize();
	}
	
	@Override
	public Space getDataIconsSpace() {
		return gui.getDataIconsSpace();
	}
	
	@Override
	public Icon getIconInSlot(int slot) {
		return gui.getIconInSlot(slot);
	}
	
}
