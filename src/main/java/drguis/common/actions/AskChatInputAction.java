package drguis.common.actions;

import java.util.function.BiConsumer;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import drguis.common.Action;
import drguis.common.CloseReason;
import drguis.common.Icon;
import drguis.common.icons.types.SimpleIcon;
import drguis.management.PlayersGUIsCloseReasonsManager;
import drlibs.input.ChatPlayerInput;

public class AskChatInputAction implements Action {
	
	private String inputQuery;
	private BiConsumer<Player, String> inputConsumer;
	private boolean shouldCloseGUIBefore;
	
	public AskChatInputAction(String inputQuery, BiConsumer<Player, String> inputConsumer) {
		this(inputQuery, inputConsumer, true);
	}
	
	public AskChatInputAction(String inputQuery, BiConsumer<Player, String> inputConsumer, boolean shouldCloseGUIBefore) {
		this.inputQuery = inputQuery;
		this.inputConsumer = inputConsumer;
		this.shouldCloseGUIBefore = shouldCloseGUIBefore;
	}

	@Override
	public void accept(Player player) {
		if (shouldCloseGUIBefore) {
			PlayersGUIsCloseReasonsManager.getInstance().setCloseReason(player.getUniqueId(), CloseReason.INPUT);
			player.closeInventory();
		}
		new ChatPlayerInput(inputQuery).askPlayerInput(player, inputConsumer);
	}

	@Override
	public Icon getActionIcon() {
		ItemStack iconItemStack = new ItemStack(Material.BOOK_AND_QUILL);
		ItemMeta iconItemMeta = iconItemStack.getItemMeta();
		iconItemMeta.setDisplayName("Ask Chat Input Action");
		iconItemStack.setItemMeta(iconItemMeta);
		return new SimpleIcon(iconItemStack, true);
	}

}
