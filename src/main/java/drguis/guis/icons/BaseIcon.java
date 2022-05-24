package drguis.guis.icons;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.bukkit.inventory.ItemStack;

import drguis.guis.icons.actions.ClickAction;

public class BaseIcon implements Icon {

	private ItemStack itemStack;
	private List<ClickAction> clickActions;
	
	public BaseIcon(ItemStack itemStack) {
		this.itemStack = itemStack;
		this.clickActions = new ArrayList<>();
	}
	
	@Override
	public ItemStack getItemStack() {
		return this.itemStack;
	}

	@Override
	public Collection<ClickAction> getClickActions() {
		return clickActions;
	}
	
	public BaseIcon addClickAction(ClickAction clickAction) {
		this.clickActions.add(clickAction);
		return this;
	}
	
	public boolean removeClickAction(ClickAction clickAction) {
		return this.clickActions.remove(clickAction);
	}
	
	public BaseIcon clearClickActions() {
		this.clickActions.clear();
		return this;
	}

}
