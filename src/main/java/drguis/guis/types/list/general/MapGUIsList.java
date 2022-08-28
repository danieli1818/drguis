package drguis.guis.types.list.general;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

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
import drguis.guis.types.general.MapGUI;
import drguis.guis.types.list.BaseGUIsList;
import drguis.utils.SchedulerUtils;

public class MapGUIsList<T extends Icon> extends BaseGUIsList<T> {
	
	private Map<Integer, T> icons; // Relative slots
	private Space dataIconsSpace;

	public MapGUIsList(int guiSize, String title) {
		this(guiSize, title, new RangeSpace(0, guiSize - 9));
	}
	
	public MapGUIsList(int guiSize, String title, Space dataIconsSpace) {
		super(guiSize, title, new IndexSlotData(-9, new ItemStack(Material.ARROW)), new IndexSlotData(-1, new ItemStack(Material.ARROW)));
		this.icons = new HashMap<>();
		this.dataIconsSpace = dataIconsSpace;
	}
	
	public MapGUIsList(int guiSize, String title, SlotData prevIconSlotData, SlotData nextIconSlotData) {
		this(guiSize, title, new RangeSpace(0, guiSize), prevIconSlotData, nextIconSlotData);
	}
	
	public MapGUIsList(int guiSize, String title, Space dataIconsSpace, SlotData prevIconSlotData, SlotData nextIconSlotData) {
		super(guiSize, title, prevIconSlotData, nextIconSlotData);
		this.icons = new HashMap<>();
		this.dataIconsSpace = dataIconsSpace;
	}

	@Override
	public Map<Integer, T> getDataIcons() {
		return icons;
	}

	@Override
	public Map<Integer, T> getDataIcons(Player player) {
		return icons;
	}

	@Override
	public T getDataIconInSlot(int slot) {
		return icons.get(slot);
	}

	@Override
	public Space getDataIconsSpace() {
		return dataIconsSpace;
	}

	@Override
	public Icon getIconInSlot(int slot) {
		return icons.get(dataIconsSpace.getRelativeSlot(slot));
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
	
	private Map<Integer, T> getGUIIconsByIndex(int guiIndex) {
		int fromIndex = guiIndex * getDataIconsSpace().getSize();
		int toIndex = Math.min(fromIndex + getDataIconsSpace().getSize(), icons.size());
		return icons.entrySet().stream()
				.filter((Map.Entry<Integer, T> entry) -> entry.getKey() >= fromIndex && entry.getKey() < toIndex)
				.collect(Collectors.toMap((Map.Entry<Integer, T> entry) -> entry.getKey(), (Map.Entry<Integer, T> entry) -> entry.getValue()));
	}
	
	public GUI getGUIInIndex(int guiIndex) {
		if (!doesGUIExist(guiIndex)) {
			return null;
		}
		MapGUI<T> mapGUI = new MapGUI<T>(getGUISize(), getTitle().replace("<PAGE_NUMBER>", String.valueOf(guiIndex)), dataIconsSpace);
		Map<Integer, T> currentGUIIcons = getGUIIconsByIndex(guiIndex);
		for (Map.Entry<Integer, T> iconEntry : currentGUIIcons.entrySet()) {
			mapGUI.setIconInSlot(iconEntry.getValue(), dataIconsSpace.getAbsoluteSlot(iconEntry.getKey()));
		}
		MapGUIDecorator<GUI> finalGUI = new MapGUIDecorator<GUI>(mapGUI);
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
	
	private boolean doesGUIExist(int guiIndex) {
		return getMaxRelativeIndex() >= guiIndex * getDataIconsSpace().getSize();
	}
	
	private int getMaxRelativeIndex() {
		return icons.keySet().stream().reduce(-1, (Integer valueTillNow, Integer newValue) -> Math.max(valueTillNow, newValue));
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
	
	public void removeAllIcons() {
		icons.clear();
	}

}
