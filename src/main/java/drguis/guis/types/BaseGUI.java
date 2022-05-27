package drguis.guis.types;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import drguis.guis.GUI;
import drguis.guis.icons.Icon;

public abstract class BaseGUI<T extends Icon> implements GUI<T> {

	private int size;
	private String title;
	
	public BaseGUI(int size, String title) {
		this.size = 36;
		this.title = title;
	}
	
	public int getSize() {
		return this.size;
	}
	
	@Override
	public Inventory getInventory() {
		return Bukkit.createInventory(this, this.size, this.title);
	}
	
	@Override
	public Inventory getInventory(Player player) {
		return getInventory();
	}

}
