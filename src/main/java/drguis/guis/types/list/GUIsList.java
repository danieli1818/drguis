package drguis.guis.types.list;

import java.util.Map;

import org.bukkit.entity.Player;

import drguis.guis.GUI;
import drguis.guis.icons.Icon;
import drguis.guis.types.DataGUI;

public interface GUIsList<T extends Icon> extends DataGUI<T> {

	public Map<Integer, T> getDataIcons();
	
	public Map<Integer, T> getDataIcons(Player player);
	
	public int getGUISize();
	
	public GUI getGUIInIndex(int guiIndex);
	
}
