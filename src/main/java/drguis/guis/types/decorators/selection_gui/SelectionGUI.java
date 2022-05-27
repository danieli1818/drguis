package drguis.guis.types.decorators.selection_gui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import drenchantments.enchantments.DREnchantmentsManager;
import drguis.guis.GUI;
import drguis.guis.effects.enchantments.Glow;
import drguis.guis.icons.Icon;
import drguis.guis.types.decorators.GUIDecorator;

public class SelectionGUI<T extends Icon> extends GUIDecorator<T> implements SelectionGUII<T> {

	private Map<Player, Integer> playerSelections;
	
	public SelectionGUI(GUI<T> gui) {
		super(gui);
		this.playerSelections = new HashMap<>();
	}

	@Override
	public boolean onClickOnSlot(Player player, int slot, InventoryClickEvent event) {
		if (!onClickOnSlot(player, slot, event)) {
			return false;
		}
		this.playerSelections.put(player, slot);
		return true;
	}
	
	@Override
	public Inventory getInventory(Player player) {
		Inventory inventory = getGUI().getInventory(player);
		Integer selectedSlot = this.playerSelections.get(player);
		if (selectedSlot == null) {
			return inventory;
		}
		ItemStack selectedItemStack = getItemStackInSlot(selectedSlot);
		if (selectedItemStack == null) {
			return inventory;
		}
		inventory.setItem(selectedSlot, selectItemStack(selectedItemStack));
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
	public void clearSelections() {
		this.playerSelections.clear();
		
	}

}
