package drguis.models.types.editors;

import java.io.Serializable;

import org.bukkit.entity.Player;

import drguis.models.GUIModel;

public interface GUIEditor extends GUIModel, Serializable {

	public GUIModel getGUIModel();
	
	public Player getEditingPlayer();
	
	public boolean editGUI(Player player);
	
	public Player clearEditingPlayer();
	
}
