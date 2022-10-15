package drguis.models.types.editors.common;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import drguis.api.GUIsAPI;
import drguis.models.GUIModel;
import drguis.views.GUIView;
import drguis.views.common.Icon;
import drguis.views.common.actions.ConsumerAction;
import drguis.views.common.events.IconClickEvent;
import drguis.views.common.icons.types.ActionIcon;
import drguis.views.types.SparseGUIView;

public class IconEditor implements GUIModel {

	private Icon icon;

	public IconEditor(Icon icon) {
		this.icon = icon;
	}

	@Override
	public GUIView getGUI(Player player) {
		GUIView guiView = new SparseGUIView(this, 36, "Icon Editor"); // TODO Change string to be retrieved from
																		// MessagesStorage
		guiView.setIcon(4, icon);
		guiView.setIcon(12, new ActionIcon(new ItemStack(Material.DIAMOND_PICKAXE), true,
				new ConsumerAction((Player currentPlayer) -> GUIsAPI.showGUIToPlayer(currentPlayer, guiView)))); // TODO
																													// Change
																													// guiView
																													// in
																													// the action function
																													// to the
																													// ActionsIconEditor
		guiView.setIcon(14, new ActionIcon(new ItemStack(Material.SHEARS), true,
				new ConsumerAction((Player currentPlayer) -> GUIsAPI.showGUIToPlayer(player, guiView)))); // TODO Change
																											// guiView
																											// in the
																											// action function to the
																											// IconPropertiesEditor
		return guiView;
	}

	@Override
	public void onIconClickEvent(IconClickEvent event) {
	}

}
