package drguis.guis.types.decorators.general;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

import drguis.guis.GUI;
import drguis.guis.icons.Icon;
import drguis.guis.icons.actions.ClickAction;
import drguis.guis.types.decorators.GUIDecorator;

public class ArrayGUIDecorator<T extends Icon> extends GUIDecorator<T> {

	private List<T> icons;
	
	public ArrayGUIDecorator(GUI<T> gui) {
		super(gui);
		this.icons = new ArrayList<>();
	}
	
	@Override
	public T getIconInSlot(int slot) {
		T icon = this.icons.get(slot);
		if (icon == null) {
			icon = super.getIconInSlot(slot);
		}
		return icon;
	}
	
	public boolean addIcon(T icon) {
		if (this.icons.size() == getSize()) {
			return false;
		}
		this.icons.add(icon);
		return true;
	}
	
	public boolean removeIcon(T icon) {
		return this.icons.remove(icon);
	}
	
	public void removeAllIcons() {
		this.icons.clear();
	}

	@Override
	public boolean onClickOnSlot(Player player, int slot, InventoryClickEvent event) {
		Icon icon = this.icons.get(slot);
		if (icon == null) {
			return super.onClickOnSlot(player, slot, event);
		}
		for (ClickAction action : icon.getClickActions()) {
			action.execute(player);
		}
		player.closeInventory();
		event.setCancelled(true);
		return true;
	}
	
	@Override
	public Inventory getInventory(Player player) {
		Inventory inventory = super.getInventory(player);
		for (Icon icon : this.icons) {
			System.out.println(icon.getItemStack());
		}
		int iconIndex = 0;
		for (Icon icon : this.icons) {
			if (icon != null) {
				inventory.setItem(iconIndex, icon.getItemStack());
			}
			iconIndex++;
		}
		System.out.println(inventory.getItem(0));
		return inventory;
	}
	
	@Override
	public Inventory getInventory() {
		Inventory inventory = super.getInventory();
		int index = 0;
		for (Icon icon : this.icons) {
			inventory.setItem(index, icon.getItemStack());
			index++;
		}
		return inventory;
	}

}
