package drguis.guis.pages.types;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import com.google.common.collect.Lists;

import drguis.guis.common.Icon;
import drguis.guis.common.Region;
import drguis.guis.pages.BaseGUIPage;
import drguis.utils.Functions;

public class LavishGUIPage extends BaseGUIPage {

	private Icon[] icons;

	public LavishGUIPage(int pageSize, String title) {
		super(pageSize, title);
		this.icons = new Icon[pageSize];
	}
	
	public LavishGUIPage setIcon(int index, Icon icon) {
		if (isValidIndex(index)) {
			icons[index] = icon;
		}
		return this;
	}
	
	public LavishGUIPage setIcons(Region region, List<Icon> icons) {
		setIcons(Functions.zip(Lists.newArrayList(region), icons));
		return this;
	}
	
	public LavishGUIPage setIcons(Map<Integer, Icon> iconsMap) {
		for (Map.Entry<Integer, Icon> iconEntry : iconsMap.entrySet()) {
			int iconIndex = iconEntry.getKey();
			Icon icon = iconEntry.getValue();
			if (isValidIndex(iconIndex) && icon != null) {
				this.icons[iconIndex] = icon;
			}
		}
		return this;
	}
	
	public LavishGUIPage setIcons(Icon[] icons) {
		if (icons.length == getPageSize()) {
			this.icons = icons;
		}
		return this;
	}

	@Override
	public Inventory getInventory() {
		Inventory inventory = Bukkit.createInventory(this, getPageSize(), getTitle());
		inventory.setContents(Arrays.asList(icons).stream().map((Icon icon) -> icon.getIcon()).collect(Collectors.toList())
				.toArray(new ItemStack[icons.length]));
		return inventory;
	}

	@Override
	public Inventory getInventory(Player player) {
		return getInventory();
	}

	private boolean isValidIndex(int index) {
		return index >= 0 && index < getPageSize();
	}

	@Override
	public Icon getIconInIndex(int index) {
		return icons[index];
	}
	
	@Override
	public Icon[] getIcons() {
		return icons;
	}

	@Override
	public Icon getIconInIndex(Player player, int index) {
		return getIconInIndex(index);
	}

	@Override
	public Icon[] getIcons(Player player) {
		return getIcons();
	}

}
