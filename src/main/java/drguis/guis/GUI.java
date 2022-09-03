package drguis.guis;

import org.bukkit.entity.Player;

public interface GUI {

	public GUIPage getPage(int index);
	
	public GUIPage getPage(int index, Player player);
	
}
