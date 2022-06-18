package drguis.guis.types.decorators.data.general;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

import drguis.guis.icons.Icon;
import drguis.guis.icons.actions.ClickAction;
import drguis.guis.types.DataGUI;
import drguis.guis.types.decorators.data.GUIDataDecorator;

public class MapGUIDecorator<T extends Icon> extends GUIDataDecorator<T> {

	private Map<Integer, T> icons;
	
	public MapGUIDecorator(DataGUI<T> gui) {
		super(gui);
		this.icons = new HashMap<>();
	}
	
	@Override
	public T getDataIconInSlot(int slot) {
		T icon = this.icons.get(slot);
		if (icon == null) {
			icon = super.getDataIconInSlot(slot);
		}
		return icon;
	}
	
	public T setIconInSlot(T icon, int slot) {
		T replacedIcon = this.icons.replace(slot, icon);
		if (replacedIcon == null) {
			replacedIcon = super.getDataIconInSlot(slot);
		}
		return replacedIcon;
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
