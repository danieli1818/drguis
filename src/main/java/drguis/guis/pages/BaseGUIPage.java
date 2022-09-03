package drguis.guis.pages;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

import drguis.guis.GUIPage;
import drguis.guis.common.Action;
import drguis.guis.common.Icon;

public abstract class BaseGUIPage implements GUIPage {

	private int pageSize;
	private String title;
	
	public BaseGUIPage(int pageSize, String title) {
		this.pageSize = pageSize;
		this.title = title;
	}
	
	public int getPageSize() {
		return pageSize;
	}
	
	public String getTitle() {
		return title;
	}
	
	@Override
	public Inventory getInventory() {
		return Bukkit.createInventory(this, pageSize, title);
	}
	
	@Override
	public Inventory getInventory(Player player) {
		return getInventory();
	}
	
	@Override
	public void onInventoryClickEvent(InventoryClickEvent event) {
		int index = event.getRawSlot();
		Player player = (Player) event.getWhoClicked();
		Icon icon = getIconInIndex(index);
		if (icon != null) {
			for (Action action : icon.getActions()) {
				if (player != null) {
					action.accept(player);
				}
			}
		}
	}
	
}
