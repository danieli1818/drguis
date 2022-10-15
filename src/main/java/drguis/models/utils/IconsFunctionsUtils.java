package drguis.models.utils;

import drguis.views.common.Action;
import drguis.views.common.Icon;
import drguis.views.common.events.IconClickEvent;

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
	
}
