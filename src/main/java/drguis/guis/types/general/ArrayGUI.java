package drguis.guis.types.general;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

import drguis.guis.icons.Icon;
import drguis.guis.icons.actions.ClickAction;
import drguis.guis.icons.spaces.RangeSpace;
import drguis.guis.icons.spaces.Space;
import drguis.guis.types.BaseDataGUI;

public class ArrayGUI<T extends Icon> extends BaseDataGUI<T> {
	
	private List<T> icons;
	
	public ArrayGUI(int size, String title) {
		this(size, title, new RangeSpace(0, size));
	}
	
	public ArrayGUI(int size, String title, Space dataIconsSpace) {
		super(size, title, dataIconsSpace);
		this.icons = new ArrayList<>();
	}
	
	public ArrayGUI(int size, String title, List<T> icons) {
		this(size, title);
		this.icons.addAll(icons);
	}
	
	public ArrayGUI(int size, String title, Space dataIconsSpace, List<T> icons) {
		this(size, title, dataIconsSpace);
		this.icons.addAll(icons);
	}

	@Override
	public T getDataIconInSlot(int slot) {
		Space dataIconsSpace = getDataIconsSpace();
		if (dataIconsSpace.isAbsoluteSlotInSpace(slot)) {
			int relativeSlot = dataIconsSpace.getRelativeSlot(slot);
			if (relativeSlot < icons.size()) {
				return icons.get(dataIconsSpace.getRelativeSlot(slot));
			}
		}
		return null;
	}
	
	@Override
	public Icon getIconInSlot(int slot) {
		return getDataIconInSlot(slot);
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
		int index = 0;
		for (Icon icon : this.icons) {
			inventory.setItem(dataIconsSpace.getAbsoluteSlot(index), icon.getItemStack());
			index++;
		}
		return inventory;
	}

}
