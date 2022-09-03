package drguis.guis.common.icons;

import java.util.Collection;
import java.util.List;

import org.bukkit.inventory.ItemStack;

import drguis.guis.common.Action;

public class ListActionsIcon extends SimpleIcon {

	private List<Action> actions;
	
	public ListActionsIcon(ItemStack icon) {
		super(icon);
	}
	
	public ListActionsIcon addAction(Action action) {
		actions.add(action);
		return this;
	}
	
	public ListActionsIcon removeAction(Action action) {
		actions.remove(action);
		return this;
	}

	@Override
	public Collection<Action> getActions() {
		return actions;
	}
	
}
