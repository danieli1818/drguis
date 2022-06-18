package drguis.guis.types;

import org.apache.commons.lang.NullArgumentException;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import drguis.guis.icons.Icon;
import drguis.guis.icons.spaces.Space;

public abstract class BaseDataGUI<T extends Icon> implements DataGUI<T> {

	private int size;
	private String title;
	private Space dataIconsSpace;
	
	public BaseDataGUI(int size, String title, Space dataIconsSpace) {
		if (title == null) {
			title = "";
		}
		if (dataIconsSpace == null) {
			throw new NullArgumentException("dataIconsSpace");
		}
		if (!dataIconsSpace.isInRange(0, size)) {
			throw new IllegalArgumentException("The Space dataIconsSpace is out of bounds!");
		}
		this.size = 36;
		this.title = title;
		this.dataIconsSpace = dataIconsSpace;
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
	
	@Override
	public Space getDataIconsSpace() {
		return dataIconsSpace;
	}

}
