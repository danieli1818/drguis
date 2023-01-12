package drguis.views.types;

import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;

import drguis.models.GUIModel;
import drguis.views.GUIView;

public abstract class BaseGUIView implements GUIView {

	private GUIModel guiHolder;
	private int size;
	private String title;
	
	public BaseGUIView(GUIModel guiHolder, int size, String title) throws IllegalArgumentException {
		if (size <= 0) {
			throw new IllegalArgumentException("Size of the GUI must be bigger than zero!");
		}
		this.guiHolder = guiHolder;
		this.size = size;
		this.title = title;
	}
	
	public BaseGUIView(int size, String title) throws IllegalArgumentException {
		if (size <= 0) {
			throw new IllegalArgumentException("Size of the GUI must be bigger than zero!");
		}
		this.size = size;
		this.title = title;
	}

	@Override
	public Inventory getInventory() {
		return Bukkit.createInventory(this, getSize(), getTitle());
	}

	@Override
	public int getSize() {
		return size;
	}

	@Override
	public String getTitle() {
		return title;
	}

	@Override
	public GUIModel getGUIHolder() {
		return guiHolder;
	}
	
	public GUIView setGUIHolder(GUIModel guiHolder) {
		this.guiHolder = guiHolder;
		return this;
	}
	
}