package drguis.guis.types.general;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import drguis.api.GUIsAPI;
import drguis.guis.GUI;
import drguis.guis.GUIPage;
import drguis.guis.common.Icon;
import drguis.guis.common.Region;
import drguis.guis.common.icons.ListActionsIcon;
import drguis.guis.common.regions.SerialRegion;
import drguis.guis.pages.types.LavishGUIPage;

public class ArrayGUI implements GUI {

	private Region iconsRegion;
	private List<Icon> icons;

	private int prevPageIconIndex;
	private int nextPageIconIndex;

	private ItemStack prevPageIcon;
	private ItemStack nextPageIcon;

	private int pageSize;
	private String title;

	public ArrayGUI(int pageSize, String title) {
		icons = new ArrayList<>();
		this.pageSize = pageSize;
		if (pageSize <= 0) {
			throw new IllegalArgumentException("PageSize argument must be bigger than 0");
		}
		if (getNumOfRows() == 1) {
			iconsRegion = new SerialRegion(0, pageSize);
		}
		iconsRegion = new SerialRegion(0, pageSize - getNumOfColsInLastRow());
		this.title = title;
	}

	public ArrayGUI(int pageSize, String title, int prevPageIconIndex, ItemStack prevPageIcon, int nextPageIconIndex,
			ItemStack nextPageIcon) {
		this(pageSize, title);
		this.prevPageIconIndex = prevPageIconIndex;
		this.prevPageIcon = prevPageIcon;
		this.nextPageIconIndex = nextPageIconIndex;
		this.nextPageIcon = nextPageIcon;
	}

	private Icon getIconToPage(ItemStack itemStack, int pageIndex) {
		return new ListActionsIcon(itemStack)
				.addAction((Player player) -> GUIsAPI.showGUIToPlayer(player, getPage(pageIndex, player)));
	}

	private GUIPage createPage(int pageIndex) {
		List<Icon> pageIcons = getPageIcons(pageIndex);
		LavishGUIPage guiPage = new LavishGUIPage(pageSize, title.replace("{PAGE_NUMBER}", String.valueOf(pageIndex)))
				.setIcons(iconsRegion, pageIcons);
		if (prevPageIcon != null && isValidPage(pageIndex - 1)) {
			guiPage.setIcon(prevPageIconIndex, getIconToPage(prevPageIcon, pageIndex - 1));
		}
		if (nextPageIcon != null && isValidPage(pageIndex + 1)) {
			guiPage.setIcon(nextPageIconIndex, getIconToPage(nextPageIcon, pageIndex + 1));
		}
		return guiPage;
	}
	
	@Override
	public GUIPage getPage(int pageIndex) {
		return createPage(pageIndex);
	}

	@Override
	public GUIPage getPage(int pageIndex, Player player) {
		return getPage(pageIndex); // TODO Add caching
	}

	private List<Icon> getPageIcons(int pageIndex) {
		if (pageIndex >= getNumOfPages()) {
			return new ArrayList<>();
		}
		int lastIndex = Math.min((pageIndex + 1) * iconsRegion.getSize(), icons.size());
		return icons.subList(pageIndex * iconsRegion.getSize(), lastIndex);
	}

	private int getNumOfPages() {
		return icons.size() / iconsRegion.getSize();
	}

	private int getNumOfRows() {
		return (pageSize / 9) + 1;
	}

	private int getNumOfColsInLastRow() {
		return pageSize % 9;
	}
	
	private boolean isValidPage(int pageIndex) {
		return pageIndex >= 0 && pageIndex < getNumOfPages();
	}

	public ArrayGUI addIcon(Icon icon) {
		icons.add(icon);
		return this;
	}

	public ArrayGUI removeIcon(Icon icon) {
		icons.add(icon);
		return this;
	}

	public ArrayGUI addIcons(Collection<Icon> icons) {
		this.icons.addAll(icons);
		return this;
	}

	public ArrayGUI removeIcons(Collection<Icon> icons) {
		this.icons.removeAll(icons);
		return this;
	}

}
