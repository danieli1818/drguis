package drguis.guis.types.decorators.data.selection_gui;

import drguis.guis.icons.Icon;
import drguis.guis.types.DataGUI;

public interface SelectionGUII<T extends Icon> extends DataGUI<T> {

	public void clearSelections();
	
}
