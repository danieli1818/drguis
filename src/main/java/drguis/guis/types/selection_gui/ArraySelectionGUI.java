package drguis.guis.types.selection_gui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import drenchantments.enchantments.DREnchantmentsManager;
import drguis.guis.effects.enchantments.Glow;
import drguis.guis.icons.Icon;
import drguis.guis.icons.actions.ClickAction;
import drguis.guis.types.BaseGUI;

public class ArraySelectionGUI extends BaseGUI implements SelectionGUII {

	private List<Icon> icons;
	private Map<Player, Integer> playerSelections;
	
	public ArraySelectionGUI(int size, String title) {
		super(size, title);
		this.icons = new ArrayList<>();
		this.playerSelections = new HashMap<>();
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
		this.playerSelections.put(player, slot);
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
		Integer selectedSlot = this.playerSelections.get(player);
		if (selectedSlot == null) {
			return inventory;
		}
		System.out.println("Player has a selection!");
		ItemStack selectedItemStack = getItemStackInSlot(selectedSlot);
		if (selectedItemStack == null) {
			return inventory;
		}
		inventory.setItem(selectedSlot, selectItemStack(selectedItemStack.clone()));
		return inventory;
	}
	
	private ItemStack selectItemStack(ItemStack itemStack) {
		DREnchantmentsManager.getInstance().addEnchantmentFirstLevel(Glow.class, itemStack);
		ItemMeta itemMeta = itemStack.getItemMeta();
		List<String> lore = new ArrayList<>();
		if (itemMeta.hasLore()) {
			lore = itemMeta.getLore();
		}
		lore.add("Selected!");
		itemMeta.setLore(lore);
		itemStack.setItemMeta(itemMeta);
		return itemStack;
	}
	
	private ItemStack getItemStackInSlot(int slot) {
		Icon icon = getIconInSlot(slot);
		if (icon != null) {
			return icon.getItemStack();
		}
		return null;
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

	@Override
	public void clearSelections() {
		System.out.println("Cleared Players Selection!");
		this.playerSelections.clear();
	}

}
