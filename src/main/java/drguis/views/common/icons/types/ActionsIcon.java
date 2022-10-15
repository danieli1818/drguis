package drguis.views.common.icons.types;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.inventory.ItemStack;

import drguis.views.common.Action;
import drguis.views.common.Icon;
import drguis.views.common.icons.IconProperties;

public class ActionsIcon extends SimpleIcon implements Icon {

	private List<Action> actions;
	
	public ActionsIcon(ItemStack itemStack, IconProperties properties) {
		super(itemStack, properties);
		actions = new ArrayList<>();
	}
	
	public ActionsIcon(ItemStack itemStack, boolean cancelClickEvent) {
		super(itemStack, cancelClickEvent);
		actions = new ArrayList<>();
	}
	
	public ActionsIcon addAction(Action action) {
		actions.add(action);
		return this;
	}
	
	public ActionsIcon removeAction(Action action) {
		actions.remove(action);
		return this;
	}
	
	@Override
	public List<Action> getActions() {
		return actions;
	}

}
