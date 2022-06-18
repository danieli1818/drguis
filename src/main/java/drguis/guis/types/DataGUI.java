package drguis.guis.types;

import drguis.guis.GUI;
import drguis.guis.icons.Icon;
import drguis.guis.icons.spaces.Space;

public interface DataGUI<T extends Icon> extends GUI {
	
	public T getDataIconInSlot(int slot);
	
	public Space getDataIconsSpace();
	
}
