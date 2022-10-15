package drguis.models.types.editors;

import org.bukkit.entity.Player;

import drguis.api.GUIsAPI;
import drguis.models.GUIModel;
import drguis.models.types.SimpleGUIModel;
import drguis.models.types.editors.common.GUIMode;
import drguis.models.types.editors.common.IconEditor;
import drguis.models.utils.IconsFunctionsUtils;
import drguis.views.GUIView;
import drguis.views.common.Icon;
import drguis.views.common.events.IconClickEvent;
import drguis.views.types.SparseGUIView;

public class SimpleGUIEditor implements GUIEditor {

	private GUIView guiView;

	private GUIMode mode;

	public SimpleGUIEditor(int pageSize, String title) {
		guiView = new SparseGUIView(this, pageSize, title);
		mode = GUIMode.EDIT;
	}

	@Override
	public GUIView getGUI(Player player) {
		return guiView;
	}

	@Override
	public void onIconClickEvent(IconClickEvent event) {
		switch (mode) {
			case EDIT:
				break;
			case INNER_EDIT:
				Icon icon = event.getIcon();
				if (icon != null) {
					Player player = event.getPlayer();
					GUIsAPI.showGUIToPlayer(player, new IconEditor(icon).getGUI(player));
				}
				break;
			default: // NORMAL
				IconsFunctionsUtils.defaultOnIconClickEvent(event);
		}
			
	}

	@Override
	public GUIModel getGUIModel() {
		return new SimpleGUIModel(guiView);
	}

}
