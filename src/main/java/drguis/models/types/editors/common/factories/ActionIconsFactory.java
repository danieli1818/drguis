package drguis.models.types.editors.common.factories;

import org.bukkit.inventory.ItemStack;

import drguis.common.Icon;
import drguis.common.icons.IconProperties;
import drguis.common.icons.types.ActionIcon;

public class ActionIconsFactory extends SimpleIconsFactory implements IconsFactory {

	public ActionIconsFactory(IconProperties iconProperties) {
		super(iconProperties);
	}

	@Override
	public Icon apply(ItemStack itemStack) {
		return new ActionIcon(itemStack, getIconProperties(), null);
	}

}
