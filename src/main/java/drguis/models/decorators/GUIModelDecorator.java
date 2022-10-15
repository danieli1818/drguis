package drguis.models.decorators;

import org.bukkit.entity.Player;

import drguis.views.common.events.IconClickEvent;
import drguis.models.GUIModel;
import drguis.views.GUIView;

public class GUIModelDecorator implements GUIModel {

	private GUIModel guiModel;
	
	public GUIModelDecorator(GUIModel guiModel) {
		this.guiModel = guiModel;
	}
	
	@Override
	public GUIView getGUI(Player player) {
		return guiModel.getGUI(player);
	}

	@Override
	public void onIconClickEvent(IconClickEvent event) {}

	
	
}
