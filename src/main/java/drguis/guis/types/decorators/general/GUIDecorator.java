package drguis.guis.types.decorators.general;

import org.apache.commons.lang.NullArgumentException;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

import drguis.guis.GUI;
import drguis.guis.icons.Icon;

public abstract class GUIDecorator<T extends GUI> implements GUI {
	
	private T gui;
	
	public GUIDecorator(T gui) {
		if (gui == null) {
			throw new NullArgumentException("gui");
		}
		this.gui = gui;
	}

	@Override
	public Inventory getInventory() {
		Inventory inventory = gui.getInventory();
		Inventory decoratedInventory = Bukkit.createInventory(this, inventory.getSize(), inventory.getTitle());
		decoratedInventory.setContents(inventory.getContents());
		return decoratedInventory;
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
		return gui.getInventory(player);
	}

	@Override
	public int getSize() {
		return gui.getSize();
	}
	
	public T getGUI() {
		return gui;
	}

}
