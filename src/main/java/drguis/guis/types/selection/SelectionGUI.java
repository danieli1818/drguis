package drguis.guis.types.selection;

import org.bukkit.entity.Player;

import drguis.guis.GUI;
import drguis.guis.common.Icon;

public interface SelectionGUI extends GUI {

	public Icon getSelectionIcon(Player player);
	
}
