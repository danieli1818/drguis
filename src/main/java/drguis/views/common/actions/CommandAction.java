package drguis.views.common.actions;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import drguis.views.common.Action;
import drguis.views.common.Icon;
import drguis.views.common.icons.types.SimpleIcon;

public class CommandAction implements Action {

	private String command;
	
	public CommandAction(String command) {
		this.command = command;
	}
	
	@Override
	public void accept(Player player) {
		Bukkit.dispatchCommand(player, command);
	}

	@Override
	public Icon getActionIcon() {
		ItemStack iconItemStack = new ItemStack(Material.BOOK_AND_QUILL);
		ItemMeta iconItemMeta = iconItemStack.getItemMeta();
		iconItemMeta.setDisplayName("Command Action");
		List<String> lore = new ArrayList<>();
		lore.add(command);
		iconItemMeta.setLore(lore);
		iconItemStack.setItemMeta(iconItemMeta);
		return new SimpleIcon(iconItemStack, true);
	}

}
