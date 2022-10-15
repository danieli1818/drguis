package drguis.views;

import java.util.List;
import java.util.Map;

import org.bukkit.inventory.InventoryHolder;

import drguis.views.common.Icon;
import drguis.common.Region;
import drguis.models.GUIModel;

public interface GUIView extends InventoryHolder {

	public int getSize();
	
	public String getTitle();
	
	public GUIModel getGUIHolder();
	
	public GUIView setGUIHolder(GUIModel guiModel);
	
	public Icon getIcon(int index);
	
	public Icon setIcon(int index, Icon icon);
	
	public GUIView setIcons(Map<Integer, Icon> icons);
	
	public GUIView setIcons(Region region, List<Icon> icons);
	
	public GUIView clearIcons();
	
}
