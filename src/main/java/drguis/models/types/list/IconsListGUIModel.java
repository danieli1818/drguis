package drguis.models.types.list;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import drguis.common.Region;
import drguis.common.regions.SeriesRegion;
import drguis.models.GUIModel;
import drguis.views.GUIView;
import drguis.views.common.Icon;
import drguis.views.common.icons.utils.IconMetadata;

public class IconsListGUIModel extends BaseListGUIModel implements GUIModel {

	private List<Icon> icons;
	private Region region;

	public IconsListGUIModel(int guiPageSize, String title) throws IllegalArgumentException {
		this(new SeriesRegion(getDefaultRegionSize(guiPageSize)), title, guiPageSize);
	}

	public IconsListGUIModel(Region region, String title, int guiPageSize) {
		this(region, title, guiPageSize,
				new IconMetadata(new ItemStack(Material.ARROW),
						guiPageSize % 9 == 0 ? guiPageSize - 9 : guiPageSize - guiPageSize % 9),
				new IconMetadata(new ItemStack(Material.ARROW), guiPageSize - 1));
	}

	public IconsListGUIModel(Region region, String title, int guiPageSize, IconMetadata prevIconMetadata, IconMetadata nextIconMetadata) {
		super(guiPageSize, title, prevIconMetadata, nextIconMetadata);
		icons = new ArrayList<>();
		this.region = region;
	}
	
	private static int getDefaultRegionSize(int guiPageSize) throws IllegalArgumentException {
		if (guiPageSize <= 9) {
			throw new IllegalArgumentException("GUIPageSize must be bigger than 9");
		}
		int diff = guiPageSize % 9;
		if (diff == 1) {
			throw new IllegalArgumentException("GUIPageSize % 9 must be different than 1!");
		}
		if (diff == 0) {
			diff = 9;
		}
		return guiPageSize - diff;
	}

	public IconsListGUIModel addIcon(Icon icon) {
		icons.add(icon);
		return this;
	}
	
	public IconsListGUIModel addIcons(Set<Icon> icons) {
		for (Icon icon : icons) {
			this.icons.add(icon);
		}
		return this;
	}

	public IconsListGUIModel removeIcon(Icon icon) {
		icons.remove(icon);
		return this;
	}

	public GUIView getGUIPage(Player player, int pageIndex) {
		List<Icon> guiPageIcons = getIconsOfGUIPage(pageIndex);
		GUIView guiPage = getOptimalGUIView(player, pageIndex, guiPageIcons.size());
		guiPage.setIcons(getRegion(), guiPageIcons);
		return guiPage;
	}
	
	public int getNumberOfPages() {
		return (getIcons().size() / getRegion().getSize()) + 1;
	}

	private List<Icon> getIconsOfGUIPage(int pageIndex) {
		int startIconIndex = getRegion().getSize() * pageIndex;
		if (startIconIndex >= getIcons().size()) {
			return new ArrayList<>();
		}
		int endIconIndex = Math.min(startIconIndex + getRegion().getSize(), getIcons().size());
		return getIcons().subList(startIconIndex, endIconIndex);
	}

	private List<Icon> getIcons() {
		return icons;
	}

	private Region getRegion() {
		return region;
	}

	@Override
	public int getNumOfPages(Player player) {
		System.out.println("Yay in getNumOfPages: " + getIcons().size() + ", " + getRegion().getSize());
		return (int) Math.ceil(((double) getIcons().size()) / getRegion().getSize());
	}

}