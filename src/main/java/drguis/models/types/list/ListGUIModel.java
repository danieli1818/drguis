package drguis.models.types.list;

import org.bukkit.entity.Player;

import drguis.models.GUIModel;
import drguis.views.GUIView;

public interface ListGUIModel extends GUIModel {

	public GUIView getGUIPage(Player player, int pageIndex);
	
	public int getNumOfPages(Player player);
	
}
