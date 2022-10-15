package drguis.models.decorators.types;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.entity.Player;

import drguis.views.common.Icon;
import drguis.views.common.actions.ConsumerAction;
import drguis.views.common.events.IconClickEvent;
import drguis.views.common.icons.types.ActionsIcon;
import drguis.views.decorators.types.IconsOverriderGUIViewDecorator;
import drguis.models.GUIModel;
import drguis.models.decorators.GUIModelDecorator;
import drguis.views.GUIView;

public class SelectionGUIModelDecorator extends GUIModelDecorator implements GUIModel {

	private Map<Player, Integer> playersSelections;
	
	public SelectionGUIModelDecorator(GUIModel guiModel) {
		super(guiModel);
		playersSelections = new HashMap<>();
	}

	@Override
	public GUIView getGUI(Player player) {
		GUIView guiView = super.getGUI(player);
		if (playersSelections.containsKey(player)) {
			Integer selectionIndex = playersSelections.get(player);
			if (selectionIndex != null) {
				GUIView guiViewAfterSelection = new IconsOverriderGUIViewDecorator(guiView);
				guiViewAfterSelection.setIcon(selectionIndex, getSelectedVersionOfIcon(guiView.getIcon(selectionIndex)));
				return guiViewAfterSelection;
			}
		}
		return guiView;
	}
	
	private static Icon getSelectedVersionOfIcon(Icon icon) {
		ActionsIcon selectedIcon = new ActionsIcon(icon.getItemStack(), icon.getProperties());
		selectedIcon.addAction(new ConsumerAction((Player player) -> player.sendMessage("Icon already selected!"))); // TODO enchant icon using drenchantments plugin and change the message to be from MessagesStorage and sent by MessagesSender
		return selectedIcon;
	}
	
	@Override
	public void onIconClickEvent(IconClickEvent event) {
		Icon icon = event.getIcon();
		Boolean isSelectable = icon.getProperties().getBoolean("selectable"); // TODO change the "selectable" string to be from MessagesStorage
		if (isSelectable != null && isSelectable) {
			playersSelections.put(event.getPlayer(), event.getIconIndex());
		}
	}
	
}
