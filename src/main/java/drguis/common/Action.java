package drguis.common;

import java.util.function.Consumer;

import org.bukkit.entity.Player;

public interface Action extends Consumer<Player> {

	/**
	 * Returns the icon which represents the action in editor GUIs
	 * @return The icon which represents the action in editor GUIs
	 */
	public Icon getActionIcon();
	
}
