package drguis.guis.pages.decorators;

import java.util.function.Function;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

import drguis.guis.GUIPage;
import drguis.guis.common.Icon;
import drguis.guis.pages.GUIPageDecorator;

public class ReplaceGUIPageDecorator extends GUIPageDecorator {

	private Function<Icon, Icon> replaceFunction;
	
	public ReplaceGUIPageDecorator(GUIPage guiPage, Function<Icon, Icon> replaceFunction) {
		super(guiPage);
		this.replaceFunction = replaceFunction;
	}
	
	@Override
	public Inventory getInventory(Player player) {
		Inventory inventory = super.getInventory(player);
		Icon[] icons = getIcons(player).clone();
		for (int i = 0; i < icons.length; i++) {
			icons[i] = replaceFunction.apply(icons[i]);
			inventory.setItem(i, icons[i].getIcon());
		}
		return inventory;
	}
	
	@Override
	public Inventory getInventory() {
		Inventory inventory = super.getInventory();
		Icon[] icons = getIcons().clone();
		for (int i = 0; i < icons.length; i++) {
			icons[i] = replaceFunction.apply(icons[i]);
			inventory.setItem(i, icons[i].getIcon());
		}
		return inventory;
	}
	
	@Override
	public void onInventoryClickEvent(InventoryClickEvent event) {
		// TODO Auto-generated method stub
		super.onInventoryClickEvent(event);
	}
	
}
