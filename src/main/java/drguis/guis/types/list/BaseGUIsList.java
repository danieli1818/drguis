package drguis.guis.types.list;

import drguis.guis.common.SlotData;
import drguis.guis.icons.Icon;

public abstract class BaseGUIsList<T extends Icon> implements GUIsList<T> {

	private int guiSize;
	private String title;
	
	private SlotData prevIcon;
	private SlotData nextIcon;
	
	public BaseGUIsList(int guiSize, String title) {
		this.guiSize = guiSize;
		this.title = title;
	}
	
	public BaseGUIsList(int guiSize, String title, SlotData prevIcon, SlotData nextIcon) {
		this.guiSize = guiSize;
		this.title = title;
		this.prevIcon = prevIcon;
		this.nextIcon = nextIcon;
	}
	
	@Override
	public int getGUISize() {
		return guiSize;
	}
	
	public String getTitle() {
		return title;
	}
	
	public SlotData getPrevIcon() {
		return prevIcon;
	}
	
	public SlotData getNextIcon() {
		return nextIcon;
	}
	
	protected void setPrevIcon(SlotData slotData) {
		this.prevIcon = slotData;
	}
	
	protected void setNextIcon(SlotData slotData) {
		this.nextIcon = slotData;
	}

}
