package drguis.guis.pages.types;

import java.util.Map;

import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import drguis.guis.common.Icon;
import drguis.guis.pages.BaseGUIPage;

public class SparseGUIPage extends BaseGUIPage {
	
	private Map<Integer, Icon> icons;
	private Inventory inventory;
	
	public SparseGUIPage(int pageSize, String title) {
		super(pageSize, title);
	}

	private Inventory createInventory() {
		Inventory inventory = super.getInventory();
		for (Map.Entry<Integer, Icon> iconEntry : icons.entrySet()) {
			inventory.setItem(iconEntry.getKey(), iconEntry.getValue().getIcon());
		}
		return inventory;
	}

	@Override
	public Inventory getInventory() {
		if (inventory != null) {
			inventory = createInventory();
		}
		return inventory;
	}

	@Override
	public Inventory getInventory(Player player) {
		return getInventory();
	}

	@Override
	public Icon getIconInIndex(int index) {
		return icons.get(index);
	}

	@Override
	public Icon[] getIcons() {
		Icon[] icons = new Icon[getPageSize()];
		for (Map.Entry<Integer, Icon> icon : this.icons.entrySet()) {
			icons[icon.getKey()] = icon.getValue();
		}
		return icons;
	}

	@Override
	public Icon getIconInIndex(Player player, int index) {
		return getIconInIndex(player, index);
	}

	@Override
	public Icon[] getIcons(Player player) {
		return getIcons();
	}

}
