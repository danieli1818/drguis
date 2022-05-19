package drguis.guis.types.general;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import drguis.guis.icons.Icon;
import drguis.guis.icons.actions.ClickAction;
import drguis.guis.types.BaseGUI;

public class ArrayGUI extends BaseGUI {
	
	private List<Icon> icons;
	
	public ArrayGUI(int size, String title) {
		super(size, title);
		this.icons = new ArrayList<>();
	}

	@Override
	public Icon getIconInSlot(int slot) {
		return this.icons.get(slot);
	}
	
	public boolean addIcon(Icon icon) {
		if (this.icons.size() == getSize()) {
			return false;
		}
		this.icons.add(icon);
		return true;
	}
	
	public boolean removeIcon(Icon icon) {
		return this.icons.remove(icon);
	}
	
	public void removeAllIcon() {
		this.icons.clear();
	}

	@Override
	public boolean onClickOnSlot(Player player, int slot, InventoryClickEvent event) {
		Icon icon = this.icons.get(slot);
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
		for (Icon icon : this.icons) {
			System.out.println(icon.getItemStack());
		}
		inventory.setContents(this.icons.stream().
				map((Icon icon)->icon.getItemStack()).
				collect(Collectors.toList()).
				toArray(new ItemStack[this.icons.size()]));
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
