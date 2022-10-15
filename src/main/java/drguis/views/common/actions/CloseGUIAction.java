package drguis.views.common.actions;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import drguis.views.common.Action;
import drguis.views.common.Icon;
import drguis.views.common.icons.types.SimpleIcon;

public class CloseGUIAction implements Action {

	@Override
	public void accept(Player player) {
		player.closeInventory();
	}

	@Override
	public Icon getActionIcon() {
		ItemStack iconItemStack = new ItemStack(Material.BOOK_AND_QUILL);
		ItemMeta iconItemMeta = iconItemStack.getItemMeta();
		iconItemMeta.setDisplayName("Close GUI Action");
		iconItemStack.setItemMeta(iconItemMeta);
		return new SimpleIcon(iconItemStack, true);
	}

}
