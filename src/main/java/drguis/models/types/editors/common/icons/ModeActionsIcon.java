package drguis.models.types.editors.common.icons;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import drguis.models.types.editors.common.GUIMode;
import drguis.views.common.Action;
import drguis.views.common.Icon;
import drguis.views.common.actions.ConsumerAction;
import drguis.views.common.icons.IconProperties;

public class ModeActionsIcon implements Icon {

	private GUIMode mode;
	private Map<GUIMode, Icon> icons;
	
	public ModeActionsIcon() {
		this(GUIMode.NORMAL);
	}
	
	public ModeActionsIcon(GUIMode mode) {
		this.mode = mode;
		this.icons = new HashMap<>();
	}
	
	public ModeActionsIcon setIcon(GUIMode mode, Icon icon) {
		if (icon == null) {
			icons.remove(mode);
		} else {
			icons.put(mode, icon);
		}
		return this;
	}
	
	public boolean setMode(GUIMode mode) {
		if (this.mode == mode) {
			return false;
		}
		this.mode = mode;
		return true;
	}

	public Icon getIcon() {
		return icons.get(mode);
	}
	
	public GUIMode getMode() {
		return mode;
	}
	
	public void toggleMode() {
		List<GUIMode> modes = Arrays.asList(GUIMode.values());
		int currentGUIModeIndex = modes.indexOf(mode);
		do {
			currentGUIModeIndex++;
			currentGUIModeIndex %= modes.size();
			mode = modes.get(currentGUIModeIndex);
		} while (mode == null);
	}
	
	@Override
	public ItemStack getItemStack() {
		return getIcon().getItemStack();
	}

	@Override
	public boolean cancelClickEvent() {
		return getIcon().cancelClickEvent();
	}

	@Override
	public List<Action> getActions() {
		List<Action> actions = getIcon().getActions();
		actions.add(new ConsumerAction((Player player) -> toggleMode()));
		return actions;
	}

	@Override
	public IconProperties getProperties() {
		return getIcon().getProperties();
	}
	
}
