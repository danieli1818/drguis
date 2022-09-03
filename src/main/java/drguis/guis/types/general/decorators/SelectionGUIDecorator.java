package drguis.guis.types.general.decorators;

import drguis.guis.GUI;
import drguis.guis.common.Icon;

public class SelectionGUIDecorator extends BaseGUIDecorator implements GUI {

	private Icon selectedIcon;
	
	public SelectionGUIDecorator(GUI gui) {
		super(gui);
	}

}
