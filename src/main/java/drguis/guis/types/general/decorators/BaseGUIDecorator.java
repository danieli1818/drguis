package drguis.guis.types.general.decorators;

import org.bukkit.entity.Player;

import drguis.guis.GUI;
import drguis.guis.GUIPage;

public abstract class BaseGUIDecorator implements GUI {

	private GUI gui;
	
	public BaseGUIDecorator(GUI gui) {
		this.gui = gui;
	}

	@Override
	public GUIPage getPage(int index) {
		return gui.getPage(index);
	}

	@Override
	public GUIPage getPage(int index, Player player) {
		return gui.getPage(index, player);
	}
	
	
}
