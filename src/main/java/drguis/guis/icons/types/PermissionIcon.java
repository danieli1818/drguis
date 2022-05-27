package drguis.guis.icons.types;

import org.bukkit.inventory.ItemStack;

import drguis.guis.icons.BaseIcon;

public class PermissionIcon extends BaseIcon {

	private String permission;
	
	public PermissionIcon(ItemStack itemStack, String permission) {
		super(itemStack);
		this.permission = permission;
	}
	
	public String getPermission() {
		return this.permission;
	}

}
