package drguis.guis.pages;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

import drguis.guis.GUIPage;
import drguis.guis.common.Icon;

public class GUIPageDecorator implements GUIPage {

	private GUIPage guiPage;
	
	public GUIPageDecorator(GUIPage guiPage) {
		this.guiPage = guiPage;
	}

	@Override
	public Inventory getInventory() {
		return guiPage.getInventory();
	}

	@Override
	public Inventory getInventory(Player player) {
		return guiPage.getInventory(player);
	}

	@Override
	public void onInventoryClickEvent(InventoryClickEvent event) {
		guiPage.onInventoryClickEvent(event);
	}

	@Override
	public Icon getIconInIndex(int index) {
		return guiPage.getIconInIndex(index);
	}

	@Override
	public Icon[] getIcons() {
		return guiPage.getIcons();
	}

	@Override
	public Icon getIconInIndex(Player player, int index) {
		return guiPage.getIconInIndex(player, index);
	}

	@Override
	public Icon[] getIcons(Player player) {
		return getIcons();
	}
	
	@Override
	public int getPageSize() {
		return guiPage.getPageSize();
	}
	
	@Override
	public String getTitle() {
		return guiPage.getTitle();
	}
	
	
}
