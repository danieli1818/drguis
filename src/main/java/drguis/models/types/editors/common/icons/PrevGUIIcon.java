package drguis.models.types.editors.common.icons;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import drguis.common.Icon;
import drguis.common.actions.ConsumerAction;
import drguis.common.icons.types.ActionIcon;
import drguis.utils.GUIsUtils;

public class PrevGUIIcon extends ActionIcon {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6330932576497328445L;

	public PrevGUIIcon() {
		this(getDefaultItemStack());
	}
	
	public PrevGUIIcon(ItemStack itemStack) {
		super(itemStack, true, new ConsumerAction(GUIsUtils::openPrevGUI));
	}
	
	@Override
	public String getClassType() {
		return PrevGUIIcon.getType();
	}
	
	public static String getType() {
		return "prev_gui_icon";
	}
	
	@Override
	public Icon cloneIcon() {
		return new PrevGUIIcon(new ItemStack(getItemStack()));
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
