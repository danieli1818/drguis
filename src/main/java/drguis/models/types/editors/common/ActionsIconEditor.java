package drguis.models.types.editors.common;


import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import drguis.common.regions.SeriesRegion;
import drguis.models.GUIModel;
import drguis.models.types.editors.common.icons.ModeActionsIcon;
import drguis.models.types.list.IconsListGUIModel;
import drguis.views.GUIView;
import drguis.views.common.Action;
import drguis.views.common.icons.types.ActionsIcon;
import drguis.views.common.icons.types.SimpleIcon;
import drguis.views.common.icons.utils.IconMetadata;

public class ActionsIconEditor extends IconsListGUIModel implements GUIModel {

	private ActionsIcon icon;
	private ModeActionsIcon modeIcon;

	public ActionsIconEditor(ActionsIcon icon, int numOfRows) {
		super(new SeriesRegion(numOfRows * 8), "Actions Editor Page {PAGE_NUMBER}", numOfRows * 9,
				new IconMetadata(new ItemStack(Material.ARROW), numOfRows * 8),
				new IconMetadata(new ItemStack(Material.ARROW), (numOfRows * 9) - 1));
		this.icon = icon;
		modeIcon = new ModeActionsIcon();
		modeIcon.setIcon(GUIMode.NORMAL, new SimpleIcon(new ItemStack(Material.DIAMOND_PICKAXE), true));
		modeIcon.setIcon(GUIMode.EDIT, new SimpleIcon(new ItemStack(Material.DIAMOND_SWORD), true)); // REMOVE
		for (Action action : icon.getActions()) {
			addIcon(action.getActionIcon());
		}
	}

	@Override
	public GUIView getGUIPage(Player player, int pageIndex) {
		GUIView guiPage = super.getGUIPage(player, pageIndex);
		guiPage.setIcon(getGuiPageSize() - 8, modeIcon);
		return guiPage;
	}

	@Override
	public GUIView getGUI(Player player) {
		return getGUIPage(player, 0); // TODO Check if it is removable and still work since the parent class's
										// function
	}

}
