package drguis.guis.types.decorators.data.permissions;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

import drguis.guis.icons.types.PermissionIcon;
import drguis.guis.types.DataGUI;
import drguis.guis.types.decorators.data.GUIDataDecorator;

public class PermissionGUI<T extends PermissionIcon> extends GUIDataDecorator<T> {

	private String nonPermissionMessage;
	
	public PermissionGUI(DataGUI<T> gui, String nonPermissionMessage) {
		super(gui);
		this.nonPermissionMessage = nonPermissionMessage;
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
			if (nonPermissionMessage != null) {
				player.sendMessage(nonPermissionMessage);
			}
			return false;
		}
		return super.onClickOnSlot(player, slot, event);
	}
	
}
