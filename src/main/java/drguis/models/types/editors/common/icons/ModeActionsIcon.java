package drguis.models.types.editors.common.icons;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import drguis.common.Action;
import drguis.common.Icon;
import drguis.common.actions.ConsumerAction;
import drguis.common.icons.IconProperties;
import drguis.models.types.editors.common.CommonGUIMode;

public class ModeActionsIcon implements Icon {

	private String mode;
	private Map<String, Icon> icons;
	
	public ModeActionsIcon() {
		this(CommonGUIMode.NORMAL);
	}
	
	public ModeActionsIcon(String mode) {
		this.mode = mode;
		this.icons = new LinkedHashMap<>();
	}
	
	public ModeActionsIcon setIcon(String mode, Icon icon) {
		if (icon == null) {
			icons.remove(mode);
		} else {
			icons.put(mode, icon);
		}
		return this;
	}
	
	public boolean setMode(String mode) {
		if (this.mode.equals(mode)) {
			return false;
		}
		this.mode = mode;
		return true;
	}

	public Icon getIcon() {
		return icons.get(mode);
	}
	
	public String getMode() {
		return mode;
	}
	
	public void toggleMode() {
		boolean passedMode = false;
		String newMode = null;
		for (String mode : icons.keySet()) {
			if (passedMode) {
				newMode = mode;
				break;
			}
			if (this.mode.equals(mode)) {
				passedMode = true;
			}
		}
		if (newMode == null) {
			newMode = icons.keySet().iterator().next();
		}
		this.mode = newMode;
	}
	
	@Override
	public ItemStack getItemStack() {
		return getIcon().getItemStack();
	}

	@Override
	public boolean cancelClickEvent() {
		System.out.println("Mode Icon: " + getIcon());
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

	@Override
	public ItemStack setItemStack(ItemStack itemStack) {
		return null;
	}
	
	@Override
	public String getClassType() {
		return ModeActionsIcon.getType();
	}
	
	public static String getType() {
		return "mode_actions_icon";
	}

	@Override
	public Icon cloneIcon() {
		ModeActionsIcon icon = new ModeActionsIcon(mode);
		for (Map.Entry<String, Icon> iconEntry : icons.entrySet()) {
			icon.setIcon(iconEntry.getKey(), iconEntry.getValue());
		}
		return icon;
	}
	
}
