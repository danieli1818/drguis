package drguis.views.common.icons.types;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.inventory.ItemStack;

import drguis.views.common.Action;
import drguis.views.common.Icon;
import drguis.views.common.icons.IconProperties;

public class ActionIcon extends SimpleIcon implements Icon {
	
	private Action action;

	public ActionIcon(ItemStack itemStack, IconProperties properties, Action action) {
		super(itemStack, properties);
		this.action = action;
	}
	
	public ActionIcon(ItemStack itemStack, boolean cancelClickEvent, Action action) {
		super(itemStack, cancelClickEvent);
		this.action = action;
	}
	
	@Override
	public List<Action> getActions() {
		List<Action> actions = new ArrayList<>();
		actions.add(action);
		return actions;
	}

}
