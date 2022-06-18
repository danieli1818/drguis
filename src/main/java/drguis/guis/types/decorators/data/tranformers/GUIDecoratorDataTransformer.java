package drguis.guis.types.decorators.data.tranformers;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

import drguis.guis.icons.Icon;
import drguis.guis.icons.spaces.Space;
import drguis.guis.types.DataGUI;
import drguis.guis.types.decorators.general.GUIDecorator;

public class GUIDecoratorDataTransformer<T extends Icon> implements DataGUI<T> {
	
	private GUIDecorator<DataGUI<T>> guiDecorator;

	public GUIDecoratorDataTransformer(GUIDecorator<DataGUI<T>> guiDecorator) {
		this.guiDecorator = guiDecorator;
	}

	@Override
	public Icon getIconInSlot(int slot) {
		return guiDecorator.getIconInSlot(slot);
	}

	@Override
	public boolean onClickOnSlot(Player player, int slot, InventoryClickEvent event) {
		return guiDecorator.onClickOnSlot(player, slot, event);
	}

	@Override
	public Inventory getInventory(Player player) {
		return guiDecorator.getInventory(player);
	}

	@Override
	public int getSize() {
		return guiDecorator.getSize();
	}

	@Override
	public Inventory getInventory() {
		return guiDecorator.getInventory();
	}

	@Override
	public T getDataIconInSlot(int slot) {
		return guiDecorator.getGUI().getDataIconInSlot(slot);
	}

	@Override
	public Space getDataIconsSpace() {
		return guiDecorator.getGUI().getDataIconsSpace();
	}

}
