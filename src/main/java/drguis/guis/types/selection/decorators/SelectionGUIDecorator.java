package drguis.guis.types.selection.decorators;

import java.util.Map;

import org.bukkit.entity.Player;

import drguis.guis.GUI;
import drguis.guis.GUIPage;
import drguis.guis.common.Icon;
import drguis.guis.types.decorators.BaseGUIDecorator;
import drguis.guis.types.selection.SelectionGUI;

public class SelectionGUIDecorator extends BaseGUIDecorator implements SelectionGUI {

	private Map<Player, Icon> playersSelectedIcons;
	
	public SelectionGUIDecorator(GUI gui) {
		super(gui);
	}

	@Override
	public Icon getSelectionIcon(Player player) {
		return playersSelectedIcons.get(player);
	}
	
	@Override
	public GUIPage getPage(int index, Player player) {
		return super.getPage(index, player);
	}

}
