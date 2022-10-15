package drguis.models.types;

import org.bukkit.entity.Player;

import drguis.views.common.events.IconClickEvent;
import drguis.models.GUIModel;
import drguis.models.utils.IconsFunctionsUtils;
import drguis.views.GUIView;

public class SimpleGUIModel implements GUIModel {

	private GUIView guiView;
	
	public SimpleGUIModel(GUIView guiView) {
		this.guiView = guiView;
		this.guiView.setGUIHolder(this);
	}
	
	@Override
	public GUIView getGUI(Player player) {
		return guiView;
	}

	@Override
	public void onIconClickEvent(IconClickEvent event) {
		IconsFunctionsUtils.defaultOnIconClickEvent(event);
	}

	
	
}
