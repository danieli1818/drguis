package drguis.views.common;

import java.util.function.Consumer;

import org.bukkit.entity.Player;

public interface Action extends Consumer<Player> {

	public Icon getActionIcon();
	
}
