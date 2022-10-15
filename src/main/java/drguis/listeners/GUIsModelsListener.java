package drguis.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

import drguis.views.common.events.IconClickEvent;

public class GUIsModelsListener implements Listener {

	@EventHandler(priority = EventPriority.LOWEST)
	public void onIconClickEvent(IconClickEvent event) {
		event.getGuiView().getGUIHolder().onIconClickEvent(event);
	}

}
