package drguis.models.types.editors.common.factories;

import org.bukkit.inventory.ItemStack;

import drguis.common.Icon;
import drguis.common.icons.IconProperties;
import drguis.common.icons.types.SimpleIcon;

public class SimpleIconsFactory implements IconsFactory {

	private IconProperties iconProperties;
	
	public SimpleIconsFactory(IconProperties iconProperties) {
		this.iconProperties = iconProperties;
	}
	
	@Override
	public Icon apply(ItemStack itemStack) {
		return new SimpleIcon(itemStack, iconProperties);
	}
	
	protected IconProperties getIconProperties() {
		return iconProperties;
	}

}
