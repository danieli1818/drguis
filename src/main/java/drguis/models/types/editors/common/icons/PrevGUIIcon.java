package drguis.models.types.editors.common.icons;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import drguis.utils.GUIsUtils;
import drguis.views.common.actions.ConsumerAction;
import drguis.views.common.icons.types.ActionIcon;

public class PrevGUIIcon extends ActionIcon {

	public PrevGUIIcon() {
		this(getDefaultItemStack());
	}
	
	public PrevGUIIcon(ItemStack itemStack) {
		super(itemStack, true, new ConsumerAction(GUIsUtils::openPrevGUI));
	}
	
	private static ItemStack getDefaultItemStack() {
		ItemStack itemStack = new ItemStack(Material.ARROW);
		ItemMeta itemMeta = itemStack.getItemMeta();
		itemMeta.setDisplayName("Go back");
		List<String> itemLore = new ArrayList<>();
		itemLore.add("Click on me to go back to the previous GUI");
		itemMeta.setLore(itemLore);
		itemStack.setItemMeta(itemMeta);
		return itemStack;
	}

}
