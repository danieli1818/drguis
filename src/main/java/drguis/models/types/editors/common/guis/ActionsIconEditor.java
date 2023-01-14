package drguis.models.types.editors.common.guis;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import drguis.api.GUIsAPI;
import drguis.common.Action;
import drguis.common.CloseReason;
import drguis.common.Icon;
import drguis.common.actions.OpenGUIAction;
import drguis.common.events.IconClickEvent;
import drguis.common.events.PlayerInventoryClickEvent;
import drguis.common.icons.types.ActionsIcon;
import drguis.common.icons.types.SimpleIcon;
import drguis.common.icons.utils.IconMetadata;
import drguis.common.regions.SeriesRegion;
import drguis.management.GUIsStack;
import drguis.models.GUIModel;
import drguis.models.types.editors.common.GUIMode;
import drguis.models.types.editors.common.icons.ModeActionsIcon;
import drguis.models.types.editors.common.icons.PrevGUIIcon;
import drguis.models.types.list.IconsListGUIModel;
import drguis.models.utils.IconsFunctionsUtils;
import drguis.utils.GUIsUtils;
import drguis.views.GUIView;

public class ActionsIconEditor extends IconsListGUIModel implements GUIModel {

	private ActionsIcon icon;
	private ModeActionsIcon modeIcon;
	private ActionsIcon addActionIcon;

	public ActionsIconEditor(ActionsIcon icon, int numOfRows) {
		super(new SeriesRegion((numOfRows - 1) * 9), "Actions Editor Page {PAGE_NUMBER}", numOfRows * 9,
				new IconMetadata(new ItemStack(Material.ARROW), (numOfRows - 1) * 9 + 2),
				new IconMetadata(new ItemStack(Material.ARROW), (numOfRows * 9) - 1));
		this.icon = icon;
		modeIcon = new ModeActionsIcon();
		modeIcon.setIcon(GUIMode.NORMAL, new SimpleIcon(new ItemStack(Material.DIAMOND_PICKAXE), true));
		modeIcon.setIcon(GUIMode.DELETE, new SimpleIcon(new ItemStack(Material.DIAMOND_SWORD), true)); // REMOVE
		addActionIcon = new ActionsIcon(new ItemStack(Material.APPLE), true);
		addActionIcon
				.addAction(new OpenGUIAction((Player player) -> new AddActionMenuEditor(this.icon).getGUI(player), CloseReason.OPENING_GUI));
		for (Action action : icon.getActions()) {
			addIcon(action.getActionIcon());
		}
	}

	@Override
	public GUIView getGUIPage(Player player, int pageIndex) {
		GUIView guiPage = super.getGUIPage(player, pageIndex);
		guiPage.setIcon(getGuiPageSize() - 9, modeIcon);
		if (GUIsStack.getInstance().hasGUIView(player.getUniqueId())) {
			guiPage.setIcon(getGuiPageSize() - 8, new PrevGUIIcon());
		}
		guiPage.setIcon(getGuiPageSize() - 5, addActionIcon);
		return guiPage;
	}

	@Override
	public GUIView getGUI(Player player) {
		return getGUIPage(player, 0); // TODO Check if it is removable and still work since the parent class's
										// function
	}

	@Override
	public void onIconClickEvent(IconClickEvent event) {
		if (event.getIconIndex() >= getGuiPageSize() - 9) {
			IconsFunctionsUtils.defaultOnIconClickEvent(event);
			GUIsAPI.updateGUIToPlayer(event.getPlayer());
			return;
		}
		switch (modeIcon.getMode()) {
		case EDIT:
			event.setCancelled(true);
			Icon icon = event.getIcon();
			if (icon != null) {
				Player player = event.getPlayer();
				GUIsUtils.openSubGUI(player, new ActionsIconMenuEditor(icon)::getGUI);
			}
			break;
		default: // NORMAL
			break;
		}
	}

	@Override
	public void onPlayerInventoryClickEvent(PlayerInventoryClickEvent event) {
		event.setCancelled(true);
	}

}
