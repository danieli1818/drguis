package drguis.guis.common.actions;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import drguis.guis.common.Action;

public class CommandAction implements Action {

	private String command;
	
	public CommandAction(String command) {
		this.command = command;
	}

	@Override
	public void accept(Player player) {
		Bukkit.dispatchCommand(player, command);
	}
	
}
