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

public class MapGUI extends BaseGUI {

	private Map<Integer, Icon> icons;
	
	public MapGUI(int size, String title) {
		super(size, title);
		this.icons = new HashMap<>();
	}

	@Override
	public Icon getIconInSlot(int slot) {
		return this.icons.get(slot);
	}
	
	public Icon setIconInSlot(Icon icon, int slot) {
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
		Inventory inventory = getInventory(player);
		for (Entry<Integer,Icon> iconEntry : this.icons.entrySet()) {
			inventory.setItem(iconEntry.getKey(), iconEntry.getValue().getItemStack());
		}
		return inventory;
	}
	
	@Override
	public Inventory getInventory() {
		Inventory inventory = super.getInventory();
		for (Entry<Integer, Icon> iconEntry : this.icons.entrySet()) {
			inventory.setItem(iconEntry.getKey(), iconEntry.getValue().getItemStack());
		}
		return inventory;
	}

}
