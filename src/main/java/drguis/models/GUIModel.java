package drguis.models;

import org.bukkit.entity.Player;

import drguis.views.common.events.IconClickEvent;
import drguis.views.GUIView;

public interface GUIModel {

	public GUIView getGUI(Player player);
	
	public void onIconClickEvent(IconClickEvent event);
	
}
