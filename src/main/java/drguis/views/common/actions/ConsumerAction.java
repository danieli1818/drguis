package drguis.views.common.actions;

import java.util.List;
import java.util.function.Consumer;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import drguis.views.common.Action;
import drguis.views.common.Icon;
import drguis.views.common.icons.types.SimpleIcon;

public class ConsumerAction implements Action {

	private Consumer<Player> consumer;
	private List<String> description;
	
	public ConsumerAction(Consumer<Player> consumer) {
		this(consumer, null);
	}
	
	public ConsumerAction(Consumer<Player> consumer, List<String> description) {
		if (consumer == null) {
			throw new IllegalArgumentException("Consumer must not be null!");
		}
		this.consumer = consumer;
		this.description = description;
	}

	@Override
	public void accept(Player t) {
		consumer.accept(t);
	}

	@Override
	public Icon getActionIcon() {
		ItemStack iconItemStack = new ItemStack(Material.BOOK_AND_QUILL);
		ItemMeta iconItemMeta = iconItemStack.getItemMeta();
		iconItemMeta.setDisplayName("Consumer Action");
		if (description != null) {
			iconItemMeta.setLore(description);
		}
		iconItemStack.setItemMeta(iconItemMeta);
		return new SimpleIcon(iconItemStack, true);
	}
	
}
