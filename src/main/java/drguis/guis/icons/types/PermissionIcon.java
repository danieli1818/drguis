package drguis.guis.icons.types;

import org.bukkit.inventory.ItemStack;

import drguis.guis.icons.BaseIcon;
import drguis.guis.icons.actions.ClickAction;

public class PermissionIcon extends BaseIcon {

	private String permission;
	
	public PermissionIcon(ItemStack itemStack, String permission) {
		super(itemStack);
		this.permission = permission;
	}
	
	public String getPermission() {
		return this.permission;
	}
	
	@Override
	public PermissionIcon addClickAction(ClickAction clickAction) {
		return (PermissionIcon)super.addClickAction(clickAction);
	}

}
