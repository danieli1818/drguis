package drguis.models.utils;

import org.bukkit.entity.Player;

import drguis.common.Action;
import drguis.common.Icon;
import drguis.common.events.IconClickEvent;

public class IconsFunctionsUtils {

	public static void defaultOnIconClickEvent(IconClickEvent event) {
		Icon icon = event.getIcon();
		if (icon != null) {
			if (icon.cancelClickEvent()) {
				event.setCancelled(true);
			}
			for (Action action : icon.getActions()) {
				action.accept(event.getPlayer());
			}
		}
	}
	
	public static void defaultOnIconClickEvent(Icon icon, Player player) {
		if (icon != null) {
			for (Action action : icon.getActions()) {
				action.accept(player);
			}
		}
	}
	
}
