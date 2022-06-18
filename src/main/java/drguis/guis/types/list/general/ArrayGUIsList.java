package drguis.guis.types.list.general;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import drguis.guis.GUI;
import drguis.guis.common.IndexSlotData;
import drguis.guis.common.SlotData;
import drguis.guis.icons.BaseIcon;
import drguis.guis.icons.Icon;
import drguis.guis.icons.actions.ClickAction;
import drguis.guis.icons.spaces.RangeSpace;
import drguis.guis.icons.spaces.Space;
import drguis.guis.types.decorators.general.basic.MapGUIDecorator;
import drguis.guis.types.general.ArrayGUI;
import drguis.guis.types.list.BaseGUIsList;
import drguis.utils.SchedulerUtils;

public class ArrayGUIsList<T extends Icon> extends BaseGUIsList<T> {
	
	private List<T> icons; // Relative slots
	private Space dataIconsSpace;
	
	public ArrayGUIsList(int guiSize, String title) {
		this(guiSize, title, new RangeSpace(0, guiSize - 9));
	}
	
	public ArrayGUIsList(int guiSize, String title, Space dataIconsSpace) {
		super(guiSize, title, new IndexSlotData(-9, new ItemStack(Material.ARROW)), new IndexSlotData(-1, new ItemStack(Material.ARROW)));
		this.icons = new ArrayList<>();
		this.dataIconsSpace = dataIconsSpace;
	}
	
	public ArrayGUIsList(int guiSize, String title, SlotData prevIconSlotData, SlotData nextIconSlotData) {
		this(guiSize, title, new RangeSpace(0, guiSize), prevIconSlotData, nextIconSlotData);
	}
	
	public ArrayGUIsList(int guiSize, String title, Space dataIconsSpace, SlotData prevIconSlotData, SlotData nextIconSlotData) {
		super(guiSize, title, prevIconSlotData, nextIconSlotData);
		this.icons = new ArrayList<>();
		this.dataIconsSpace = dataIconsSpace;
	}

	@Override
	public T getDataIconInSlot(int slot) {
		return this.icons.get(slot);
	}

	@Override
	public boolean onClickOnSlot(Player player, int slot, InventoryClickEvent event) {
		Icon icon = this.icons.get(slot);
		if (icon == null) {
			return false;
		}
		for (ClickAction action : icon.getClickActions()) {
			action.execute(player);
		}
		player.closeInventory();
		event.setCancelled(true);
		return true;
	}

	@Override
	public Inventory getInventory(Player player) {
		return getInventory();
	}

	@Override
	public int getSize() {
		return icons.size();
	}

	@Override
	public Inventory getInventory() {
		return getGUIInIndex(0).getInventory();
	}
	
	private List<T> getGUIIconsByIndex(int guiIndex) {
		int fromIndex = guiIndex * getDataIconsSpace().getSize();
		int toIndex = Math.min(fromIndex + getDataIconsSpace().getSize(), icons.size());
		return icons.subList(fromIndex, toIndex);
	}

	@Override
	public Map<Integer, T> getDataIcons() {
		Map<Integer, T> dataIconsMap = new HashMap<>();
		int iconIndex = 0;
		for (T icon : icons) {
			dataIconsMap.put(iconIndex, icon);
			iconIndex++;
		}
		return dataIconsMap;
	}

	@Override
	public Map<Integer, T> getDataIcons(Player player) {
		return getDataIcons();
	}
	
	private GUI getGUIInIndex(int guiIndex) {
		if (!doesGUIExist(guiIndex)) {
			return null;
		}
		ArrayGUI<T> arrayGUI = new ArrayGUI<T>(getGUISize(), getTitle().replace("<PAGE_NUMBER>", String.valueOf(guiIndex)), dataIconsSpace);
		List<T> currentGUIIcons = getGUIIconsByIndex(guiIndex);
		for (T icon : currentGUIIcons) {
			arrayGUI.addIcon(icon);
		}
		MapGUIDecorator<GUI> finalGUI = new MapGUIDecorator<GUI>(arrayGUI);
		Icon prevIcon = getPrevIcon(guiIndex);
		if (prevIcon != null) {
			finalGUI.setIcon(getPrevIcon().getSlot(getSize()), prevIcon);
		}
		Icon nextIcon = getNextIcon(guiIndex);
		if (nextIcon != null) {
			finalGUI.setIcon(getNextIcon().getSlot(getSize()), nextIcon);
		}
		return finalGUI;
	}
	
	private Icon getPrevIcon(int currentGUIIndex) {
		if (!doesGUIExist(currentGUIIndex - 1) || getPrevIcon() == null) {
			return null;
		}
		return new BaseIcon(getPrevIcon().getItemStack()).addClickAction((Player player) -> {
			GUI prevGUI = getGUIInIndex(currentGUIIndex - 1);
			if (prevGUI != null) {
				SchedulerUtils.getInstance().scheduleSyncTask(1, () -> {
					player.openInventory(prevGUI.getInventory());
				});
			}
		});
	}
	
	private Icon getNextIcon(int currentGUIIndex) {
		if (!doesGUIExist(currentGUIIndex + 1) || getNextIcon() == null) {
			return null;
		}
		return new BaseIcon(getNextIcon().getItemStack()).addClickAction((Player player) -> {
			GUI nextGUI = getGUIInIndex(currentGUIIndex + 1);
			if (nextGUI != null) {
				SchedulerUtils.getInstance().scheduleSyncTask(1, () -> {
					player.openInventory(nextGUI.getInventory());
				});
			}
		});
	}
	
	private boolean doesGUIExist(int index) {
		if (index < 0 || index >= getNumOfGUIs()) {
			return false;
		}
		return true;
	}
	
	private int getNumOfGUIs() {
		return (int)Math.ceil(((double)getSize()) / getDataIconsSpace().getSize());
	}

	@Override
	public Space getDataIconsSpace() {
		return dataIconsSpace;
	}

	@Override
	public Icon getIconInSlot(int slot) {
		return icons.get(dataIconsSpace.getRelativeSlot(slot));
	}
	
	public ArrayGUIsList<T> addIcon(T icon) {
		icons.add(icon);
		return this;
	}
	
	public boolean removeIcon(T icon) {
		return icons.remove(icon);
	}

}
