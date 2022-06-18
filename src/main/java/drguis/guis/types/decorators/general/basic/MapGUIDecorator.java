package drguis.guis.types.decorators.general.basic;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

import drguis.guis.GUI;
import drguis.guis.icons.Icon;
import drguis.guis.icons.actions.ClickAction;
import drguis.guis.types.decorators.general.GUIDecorator;

public class MapGUIDecorator<T extends GUI> extends GUIDecorator<T> {

	private Map<Integer, Icon> icons;
	
	public MapGUIDecorator(T gui) {
		super(gui);
		this.icons = new HashMap<>();
	}
	
	public Icon setIcon(int slot, Icon icon) {
		if (slot < 0) {
			slot = getSize() + slot;
		}
		if (slot < 0 || slot >= getSize()) {
			throw new IndexOutOfBoundsException("Slot: " + slot + " is out of bounds!");
		}
		return icons.put(slot, icon);
	}
	
	@Override
	public Icon getIconInSlot(int slot) {
		Icon icon = icons.get(slot);
		if (icon == null) {
			icon = super.getIconInSlot(slot);
		}
		return icon;
	}
	
	@Override
	public Inventory getInventory() {
		Inventory inventory = super.getInventory();
		for (Map.Entry<Integer, Icon> iconEntry : icons.entrySet()) {
			inventory.setItem(iconEntry.getKey(), iconEntry.getValue().getItemStack());
		}
		return inventory;
	}
	
	@Override
	public Inventory getInventory(Player player) {
		Inventory inventory = super.getInventory(player);
		for (Map.Entry<Integer, Icon> iconEntry : icons.entrySet()) {
			inventory.setItem(iconEntry.getKey(), iconEntry.getValue().getItemStack());
		}
		return inventory;
	}
	
	@Override
	public boolean onClickOnSlot(Player player, int slot, InventoryClickEvent event) {
		Icon icon = icons.get(slot);
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

}
