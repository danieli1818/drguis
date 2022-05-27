package drguis.guis.types.decorators.selection_gui;

import drguis.guis.GUI;
import drguis.guis.icons.Icon;

public interface SelectionGUII<T extends Icon> extends GUI<T> {

	public void clearSelections();
	
}
