package drguis.views.common.actions;

import java.util.function.Consumer;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import drguis.views.common.Action;
import drguis.views.common.Icon;
import drguis.views.common.icons.types.SimpleIcon;
import drlibs.input.BookPlayerInput;

public class GetBookInputAction implements Action {
	
	private Consumer<String> inputConsumer;
	
	public GetBookInputAction(Consumer<String> inputConsumer) {
		this.inputConsumer = inputConsumer;
	}

	@Override
	public void accept(Player player) {
		new BookPlayerInput().getPlayerInput(player).thenAccept(inputConsumer);
	}

	@Override
	public Icon getActionIcon() {
		ItemStack iconItemStack = new ItemStack(Material.BOOK_AND_QUILL);
		ItemMeta iconItemMeta = iconItemStack.getItemMeta();
		iconItemMeta.setDisplayName("Book Input Action");
		iconItemStack.setItemMeta(iconItemMeta);
		return new SimpleIcon(iconItemStack, true);
	}

}
