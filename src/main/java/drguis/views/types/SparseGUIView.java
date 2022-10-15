package drguis.views.types;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import drguis.views.common.Icon;
import drguis.common.Region;
import drguis.models.GUIModel;
import drguis.utils.Functions;
import drguis.views.GUIView;

public class SparseGUIView extends BaseGUIView implements GUIView {

	private Map<Integer, Icon> icons;
	
	public SparseGUIView(GUIModel guiHolder, int size, String title) {
		super(guiHolder, size, title);
		icons = new HashMap<>();
	}
	
	public SparseGUIView(int size, String title) {
		super(size, title);
		icons = new HashMap<>();
	}
	
	@Override
	public Inventory getInventory() {
		Inventory inventory = super.getInventory();
		for (Map.Entry<Integer, Icon> iconEntry : icons.entrySet()) {
			Integer index = iconEntry.getKey();
			if (index == null) {
				continue;
			}
			Icon icon = iconEntry.getValue();
			if (icon == null) {
				continue;
			}
			ItemStack itemStack = icon.getItemStack();
			if (itemStack == null) {
				continue;
			}
			inventory.setItem(index, itemStack);
		}
		return inventory;
	}
	
	public Icon setIcon(int index, Icon icon) {
		return icons.put(index, icon);
	}
	
	public Icon getIcon(int index) {
		return icons.get(index);
	}
	
	public SparseGUIView setIcons(Map<Integer, Icon> icons) {
		this.icons.putAll(icons);
		return this;
	}
	
	public SparseGUIView setIcons(Region region, List<Icon> icons) {
		Map<Integer, Icon> iconsMap = Functions.zip(region, icons);
		return setIcons(iconsMap);
	}

	@Override
	public SparseGUIView clearIcons() {
		icons.clear();
		return this;
	}
	
}
