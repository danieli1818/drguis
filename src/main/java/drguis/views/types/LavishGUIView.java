package drguis.views.types;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.inventory.Inventory;

import drguis.common.Icon;
import drguis.common.Region;
import drguis.models.GUIModel;
import drguis.utils.Functions;
import drguis.views.GUIView;

public class LavishGUIView extends BaseGUIView implements GUIView {

	private Icon[] icons;

	public LavishGUIView(GUIModel guiHolder, int pageSize, String title) {
		super(guiHolder, pageSize, title);
		icons = new Icon[getSize()];
	}
	
	public LavishGUIView(int pageSize, String title) {
		super(pageSize, title);
		icons = new Icon[getSize()];
	}

	@Override
	public Inventory getInventory() {
		Inventory inventory = super.getInventory();
		for (int i = 0; i < icons.length; i++) {
			Icon icon = icons[i];
			if (icon != null) {
				inventory.setItem(i, icon.getItemStack());
			}
		}
		return inventory;
	}

	@Override
	public Icon getIcon(int index) {
		return icons[index];
	}

	@Override
	public Icon setIcon(int index, Icon icon) {
		Icon prevIcon = icons[index];
		icons[index] = icon;
		return prevIcon;
	}

	@Override
	public LavishGUIView setIcons(Map<Integer, Icon> icons) {
		for (Map.Entry<Integer, Icon> iconEntry : icons.entrySet()) {
			Integer iconIndex = iconEntry.getKey();
			Icon icon = iconEntry.getValue();
			if (icon == null || !isValidIconIndex(iconIndex)) {
				// TODO log error
				continue;
			}
			this.icons[iconIndex] = icon;
		}
		return this;
	}
	
	private LavishGUIView setIcons(Icon[] icons) {
		this.icons = icons.clone();
		return this;
	}
	
	private boolean isValidIconIndex(Integer iconIndex) {
		if (iconIndex == null) {
			return false;
		}
		return iconIndex >= 0 && iconIndex < getSize();
	}

	@Override
	public LavishGUIView setIcons(Region region, List<Icon> icons) {
		Map<Integer, Icon> iconsMap = Functions.zip(region, icons);
		return setIcons(iconsMap);
	}

	@Override
	public LavishGUIView clearIcons() {
		for (int i = 0; i < icons.length; i++) {
			icons[i] = null;
		}
		return this;
	}

	@Override
	public GUIView cloneView() {
		return new LavishGUIView(getGUIHolder(), getSize(), getTitle()).setIcons(icons);
	}

	@Override
	public Map<Integer, Icon> getIcons() {
		Map<Integer, Icon> icons = new HashMap<>();
		for (int i = 0; i < this.icons.length; i++) {
			Icon icon = this.icons[i];
			if (icon != null) {
				icons.put(i, icon);
			}
		}
		return null;
	}

}
