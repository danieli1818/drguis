package drguis.management;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import drguis.models.GUIModel;
import drguis.utils.GUIsUtils;
import drguis.views.common.Icon;
import drguis.views.common.actions.ConsumerAction;
import drguis.views.common.icons.types.ActionIcon;
import drguis.views.common.icons.types.ActionsIcon;

public class ActionsManager {

	private static ActionsManager instance;

	private Map<String, ActionIconData> actionsIconsData;

	private ActionsManager() {
		this.actionsIconsData = new HashMap<>();
	}

	public static ActionsManager getInstance() {
		if (instance == null) {
			instance = new ActionsManager();
		}
		return instance;
	}

	public boolean registerActionIconData(String actionId, ItemStack itemStack, Function<ActionsIcon, GUIModel> guiModelSupplier) {
		if (actionsIconsData.containsKey(actionId)) {
			return false;
		}
		actionsIconsData.put(actionId, new ActionIconData(itemStack, guiModelSupplier));
		return true;
	}

	public boolean unregisterActionIconData(String actionId) {
		return actionsIconsData.remove(actionId) != null;
	}

	public Set<Icon> getIcons(ActionsIcon icon) {
		Set<Icon> icons = new HashSet<>();
		for (ActionIconData iconData : actionsIconsData.values()) {
			icons.add(new ActionIcon(iconData.getItemStack(), true, new ConsumerAction((Player currentPlayer) -> GUIsUtils.openSubGUI(currentPlayer, iconData.getGuiModelSupplier().apply(icon)::getGUI))));
		}
		return icons;
	}

	private class ActionIconData {

		private ItemStack itemStack;
		private Function<ActionsIcon, GUIModel> guiModelSupplier;

		public ActionIconData(ItemStack itemStack, Function<ActionsIcon, GUIModel> guiModelSupplier) {
			this.itemStack = itemStack;
			this.guiModelSupplier = guiModelSupplier;
		}

		public ItemStack getItemStack() {
			return itemStack;
		}

		public Function<ActionsIcon, GUIModel> getGuiModelSupplier() {
			return guiModelSupplier;
		}

	}

}
