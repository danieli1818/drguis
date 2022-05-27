package drguis.guis.types.general;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

import drguis.guis.icons.Icon;
import drguis.guis.icons.actions.ClickAction;
import drguis.guis.types.BaseGUI;

public class MapGUI<T extends Icon> extends BaseGUI<T> {

	private Map<Integer, T> icons;
	
	public MapGUI(int size, String title) {
		super(size, title);
		this.icons = new HashMap<>();
	}

	@Override
	public T getIconInSlot(int slot) {
		return this.icons.get(slot);
	}
	
	public T setIconInSlot(T icon, int slot) {
		return this.icons.replace(slot, icon);
	}

	@Override
	public boolean onClickOnSlot(Player player, int slot, InventoryClickEvent event) {
		Icon icon = getIconInSlot(slot);
		if (icon == null) {
			return false;
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
		for (Entry<Integer, T> iconEntry : this.icons.entrySet()) {
			inventory.setItem(iconEntry.getKey(), iconEntry.getValue().getItemStack());
		}
		return inventory;
	}
	
	@Override
	public Inventory getInventory() {
		Inventory inventory = super.getInventory();
		for (Entry<Integer, T> iconEntry : this.icons.entrySet()) {
			inventory.setItem(iconEntry.getKey(), iconEntry.getValue().getItemStack());
		}
		return inventory;
	}

}
