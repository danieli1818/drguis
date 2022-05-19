package drguis.guis.icons;

import java.util.Collection;
import java.util.List;

import org.bukkit.inventory.ItemStack;

import drguis.guis.icons.actions.ClickAction;

public class BaseIcon implements Icon {

	private ItemStack itemStack;
	private List<ClickAction> clickActions;
	
	public BaseIcon(ItemStack itemStack) {
		this.itemStack = itemStack;
	}
	
	@Override
	public ItemStack getItemStack() {
		return this.itemStack;
	}

	@Override
	public Collection<ClickAction> getClickActions() {
		return clickActions;
	}
	
	public void addClickAction(ClickAction clickAction) {
		this.clickActions.add(clickAction);
	}
	
	public boolean removeClickAction(ClickAction clickAction) {
		return this.clickActions.remove(clickAction);
	}

}
