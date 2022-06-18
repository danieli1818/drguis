package drguis.guis.types.general;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

import drguis.guis.icons.Icon;
import drguis.guis.icons.actions.ClickAction;
import drguis.guis.icons.spaces.RangeSpace;
import drguis.guis.icons.spaces.Space;
import drguis.guis.types.BaseDataGUI;

public class MapGUI<T extends Icon> extends BaseDataGUI<T> {

	private Map<Integer, T> icons;
	
	public MapGUI(int size, String title, Space dataIconsSpace) {
		super(size, title, dataIconsSpace);
		this.icons = new HashMap<>();
	}
	
	public MapGUI(int size, String title) {
		this(size, title, new RangeSpace(0, size));
	}

	@Override
	public T getDataIconInSlot(int slot) {
		Space dataIconsSpace = getDataIconsSpace();
		if (dataIconsSpace.isAbsoluteSlotInSpace(slot)) {
			return this.icons.get(dataIconsSpace.getRelativeSlot(slot));
		}
		return null;
	}
	
	public T setIconInSlot(T icon, int slot) {
		return this.icons.replace(slot, icon);
	}
	
	@Override
	public Icon getIconInSlot(int slot) {
		return getDataIconInSlot(slot);
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
		return getInventory();
	}
	
	@Override
	public Inventory getInventory() {
		Inventory inventory = super.getInventory();
		Space dataIconsSpace = getDataIconsSpace();
		for (Entry<Integer, T> iconEntry : this.icons.entrySet()) {
			inventory.setItem(dataIconsSpace.getAbsoluteSlot(iconEntry.getKey()), iconEntry.getValue().getItemStack());
		}
		return inventory;
	}

}
