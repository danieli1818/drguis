package drguis.common.actions;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.google.common.base.Function;

import drguis.api.GUIsAPI;
import drguis.common.Action;
import drguis.common.CloseReason;
import drguis.common.Icon;
import drguis.common.icons.types.SimpleIcon;
import drguis.management.PlayersGUIsCloseReasonsManager;
import drguis.views.GUIView;

public class OpenGUIAction implements Action {

	private Function<Player, GUIView> guiViewFunction;
	private CloseReason closeReason;
	
	public OpenGUIAction(GUIView guiView, CloseReason closeReason) {
		this.guiViewFunction = (Player player) -> guiView;
		this.closeReason = closeReason;
	}
	
	public OpenGUIAction(GUIView guiView) {
		this(guiView, CloseReason.NORMAL);
	}
	
	public OpenGUIAction(Function<Player, GUIView> guiViewFunction, CloseReason closeReason) {
		this.guiViewFunction = guiViewFunction;
		this.closeReason = closeReason;
	}
	
	public OpenGUIAction(Function<Player, GUIView> guiViewFunction) {
		this(guiViewFunction, CloseReason.NORMAL);
	}
	
	@Override
	public void accept(Player player) {
		PlayersGUIsCloseReasonsManager.getInstance().setCloseReason(player.getUniqueId(), closeReason);
		GUIsAPI.showGUIToPlayer(player, guiViewFunction.apply(player));
	}

	@Override
	public Icon getActionIcon() {
		ItemStack iconItemStack = new ItemStack(Material.BOOK_AND_QUILL);
		ItemMeta iconItemMeta = iconItemStack.getItemMeta();
		iconItemMeta.setDisplayName("Open GUI Action");
		iconItemStack.setItemMeta(iconItemMeta);
		return new SimpleIcon(iconItemStack, true);
	}

}
