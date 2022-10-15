package drguis.views.common.icons.types;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.inventory.ItemStack;

import drguis.views.common.Action;
import drguis.views.common.Icon;
import drguis.views.common.icons.IconProperties;

public class SimpleIcon implements Icon {

	private ItemStack itemStack;
	private boolean cancelClickEvent;
	private IconProperties properties;
	
	public SimpleIcon(ItemStack itemStack, IconProperties properties) {
		this(itemStack, true);
		this.properties = properties;
	}
	
	public SimpleIcon(ItemStack itemStack, boolean cancelClickEvent) {
		this.itemStack = itemStack;
		this.cancelClickEvent = cancelClickEvent;
	}
	
	@Override
	public ItemStack getItemStack() {
		return itemStack;
	}
	
	@Override
	public boolean cancelClickEvent() {
		return cancelClickEvent;
	}
	
	@Override
	public List<Action> getActions() {
		return new ArrayList<>();
	}

	@Override
	public IconProperties getProperties() {
		return properties;
	}

}
