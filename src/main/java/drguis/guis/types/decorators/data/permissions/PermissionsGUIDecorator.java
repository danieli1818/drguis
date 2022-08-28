package drguis.guis.types.decorators.data.permissions;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

import drguis.guis.icons.types.PermissionIcon;
import drguis.guis.types.DataGUI;
import drguis.guis.types.decorators.data.GUIDataDecorator;

public class PermissionsGUIDecorator<T extends PermissionIcon> extends GUIDataDecorator<T> {

	private String noPermissionMessage;
	
	public PermissionsGUIDecorator(DataGUI<T> gui, String noPermissionMessage) {
		super(gui);
		this.noPermissionMessage = noPermissionMessage;
	}
	
	@Override
	public boolean onClickOnSlot(Player player, int slot, InventoryClickEvent event) {
		T icon = getDataIconInSlot(slot);
		if (icon == null) {
			return super.onClickOnSlot(player, slot, event);
		}
		String permission = icon.getPermission();
		if (permission == null) {
			return super.onClickOnSlot(player, slot, event);
		}
		if (!player.hasPermission(permission)) {
			if (noPermissionMessage != null) {
				player.sendMessage(noPermissionMessage);
			}
			return false;
		}
		return super.onClickOnSlot(player, slot, event);
	}
	
}
