package drguis.guis.types.decorators;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

import drguis.guis.GUI;
import drguis.guis.icons.Icon;

public abstract class GUIDecorator<T extends Icon> implements GUI<T> {

	private GUI<T> gui;
	
	public GUIDecorator(GUI<T> gui) {
		this.gui = gui;
	}
	
	@Override
	public Inventory getInventory() {
		return gui.getInventory();
	}

	@Override
	public T getIconInSlot(int slot) {
		return gui.getIconInSlot(slot);
	}

	@Override
	public boolean onClickOnSlot(Player player, int slot, InventoryClickEvent event) {
		return gui.onClickOnSlot(player, slot, event);
	}

	@Override
	public Inventory getInventory(Player player) {
		return gui.getInventory(player);
	}

	public final GUI<T> getGUI() {
		return this.gui;
	}
	
	@Override
	public int getSize() {
		return this.gui.getSize();
	}
	
}
