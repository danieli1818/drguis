package drguis.models.types.editors.common.factories;

import org.bukkit.inventory.ItemStack;

import drguis.common.Icon;
import drguis.common.icons.IconProperties;
import drguis.common.icons.types.ActionsIcon;

public class ActionsIconsFactory extends SimpleIconsFactory implements IconsFactory {

	public ActionsIconsFactory(IconProperties iconProperties) {
		super(iconProperties);
	}

	@Override
	public Icon apply(ItemStack itemStack) {
		return new ActionsIcon(itemStack, getIconProperties());
	}

}
