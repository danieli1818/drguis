package drguis.views.decorators.types;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.inventory.Inventory;

import drguis.views.common.Icon;
import drguis.views.decorators.GUIViewDecorator;
import drguis.common.Region;
import drguis.utils.Functions;
import drguis.views.GUIView;

public class IconsOverriderGUIViewDecorator extends GUIViewDecorator {

	private Map<Integer, Icon> icons;
	
	public IconsOverriderGUIViewDecorator(GUIView guiView) {
		super(guiView);
		icons = new HashMap<>();
	}
	
	@Override
	public Inventory getInventory() {
		if (icons.isEmpty()) {
			return super.getInventory();
		}
		Inventory inventory = getInventoryCopy();
		for (Map.Entry<Integer, Icon> iconEntry : icons.entrySet()) {
			inventory.setItem(iconEntry.getKey(), iconEntry.getValue().getItemStack());
		}
		return inventory;
	}
	
	@Override
	public Icon getIcon(int index) {
		if (icons.containsKey(index)) {
			return icons.get(index);
		}
		return super.getIcon(index);
	}
	
	@Override
	public Icon setIcon(int index, Icon icon) {
		return icons.put(index, icon);
	}
	
	@Override
	public IconsOverriderGUIViewDecorator setIcons(Map<Integer, Icon> icons) {
		this.icons.putAll(icons);
		return this;
	}
	
	@Override
	public IconsOverriderGUIViewDecorator setIcons(Region region, List<Icon> icons) {
		Map<Integer, Icon> iconsMap = Functions.zip(region, icons);
		return setIcons(iconsMap);
	}
	
	@Override
	public IconsOverriderGUIViewDecorator clearIcons() {
		icons.clear();
		return this;
	}
	
	@Override
	public GUIView cloneView() {
		return new IconsOverriderGUIViewDecorator(super.cloneView()).setIcons(icons);
	}
	
}
