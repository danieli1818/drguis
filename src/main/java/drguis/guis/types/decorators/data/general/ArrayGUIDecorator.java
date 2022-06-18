package drguis.guis.types.decorators.data.general;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

import drguis.guis.icons.Icon;
import drguis.guis.icons.actions.ClickAction;
import drguis.guis.types.DataGUI;
import drguis.guis.types.decorators.data.GUIDataDecorator;

public class ArrayGUIDecorator<T extends Icon> extends GUIDataDecorator<T> {

	private List<T> icons;
	
	public ArrayGUIDecorator(DataGUI<T> gui) {
		super(gui);
		this.icons = new ArrayList<>();
	}
	
	@Override
	public T getDataIconInSlot(int slot) {
		T icon = this.icons.get(slot);
		if (icon == null) {
			icon = super.getDataIconInSlot(slot);
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
		int iconIndex = 0;
		for (Icon icon : this.icons) {
			if (icon != null) {
				inventory.setItem(iconIndex, icon.getItemStack());
			}
			iconIndex++;
		}
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
