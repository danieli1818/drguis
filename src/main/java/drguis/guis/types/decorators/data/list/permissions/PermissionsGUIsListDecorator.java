package drguis.guis.types.decorators.data.list.permissions;

import drguis.guis.icons.types.PermissionIcon;
import drguis.guis.types.DataGUI;
import drguis.guis.types.decorators.data.list.GUIsListDataDecorator;
import drguis.guis.types.decorators.data.permissions.PermissionsGUIDecorator;
import drguis.guis.types.list.GUIsList;

public class PermissionsGUIsListDecorator<T extends PermissionIcon> extends GUIsListDataDecorator<T> {

	private String noPermissionGUI;

	public PermissionsGUIsListDecorator(GUIsList<T> gui, String noPermissionGUI) {
		super(gui);
		this.noPermissionGUI = noPermissionGUI;
	}

	@Override
	public DataGUI<T> getGUIInIndex(int guiIndex) {
		return new PermissionsGUIDecorator<T>(super.getGUIInIndex(guiIndex), noPermissionGUI);
	}

}
