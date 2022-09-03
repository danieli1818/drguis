package drguis.guis.common.icons;

import java.util.ArrayList;
import java.util.Collection;

import org.bukkit.inventory.ItemStack;

import drguis.guis.common.Action;
import drguis.guis.common.Icon;
import drguis.guis.common.IconProperties;

public class SimpleIcon implements Icon {

	private ItemStack icon;
	private IconProperties properties;
	
	public SimpleIcon(ItemStack icon) {
		this.icon = icon;
		this.properties = null;
	}
	
	public SimpleIcon(ItemStack icon, IconProperties properties) {
		this.icon = icon;
		this.properties = properties;
	}
	
	@Override
	public ItemStack getIcon() {
		return icon;
	}

	@Override
	public Collection<Action> getActions() {
		return new ArrayList<>();
	}
	
	@Override
	public IconProperties getProperties() {
		return properties;
	}

}
