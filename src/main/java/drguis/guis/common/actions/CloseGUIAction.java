package drguis.guis.common.actions;

import org.bukkit.entity.Player;

import drguis.guis.common.Action;

public class CloseGUIAction implements Action {

	@Override
	public void accept(Player player) {
		player.closeInventory();
	}

	
	
}
