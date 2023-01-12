package drguis.views.decorators;

import java.util.List;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;

import drguis.views.common.Icon;
import drguis.common.Region;
import drguis.models.GUIModel;
import drguis.views.GUIView;

public abstract class GUIViewDecorator implements GUIView {
	
	private GUIView guiView;
	
	public GUIViewDecorator(GUIView guiView) {
		this.guiView = guiView;
	}

	@Override
	public Inventory getInventory() {
		return guiView.getInventory();
	}
	
	public Inventory getInventoryCopy() {
		Inventory originalInventory = getInventory();
		Inventory inventory = Bukkit.createInventory(this, originalInventory.getSize(), originalInventory.getTitle());
		inventory.setContents(originalInventory.getContents());
		return inventory;
	}

	@Override
	public int getSize() {
		return guiView.getSize();
	}

	@Override
	public String getTitle() {
		return guiView.getTitle();
	}

	@Override
	public GUIModel getGUIHolder() {
		return guiView.getGUIHolder();
	}
	
	@Override
	public GUIView setGUIHolder(GUIModel guiModel) {
		return guiView.setGUIHolder(guiModel);
	}

	@Override
	public Icon getIcon(int index) {
		return guiView.getIcon(index);
	}

	@Override
	public Icon setIcon(int index, Icon icon) {
		return guiView.setIcon(index, icon);
	}
	
	@Override
	public GUIView setIcons(Map<Integer, Icon> icons) {
		return guiView.setIcons(icons);
	}
	
	@Override
	public GUIView setIcons(Region region, List<Icon> icons) {
		return guiView.setIcons(region, icons);
	}
	
	@Override
	public GUIView clearIcons() {
		return guiView.clearIcons();
	}
	
	@Override
	public GUIView cloneView() {
		return guiView.cloneView();
	}

}
