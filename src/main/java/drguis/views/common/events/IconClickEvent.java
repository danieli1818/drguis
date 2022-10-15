package drguis.views.common.events;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerEvent;

import drguis.views.common.Icon;
import drguis.views.GUIView;

public class IconClickEvent extends PlayerEvent implements Cancellable {
	
	private static final HandlerList HANDLERS = new HandlerList();
	
	private GUIView guiView;
	private Icon icon;
	private int iconIndex;
	
	private boolean cancelled;
	
	private InventoryClickEvent event;

	public static HandlerList getHandlerList() {
		return HANDLERS;
	}
	
	@Override
	public HandlerList getHandlers() {
		return HANDLERS;
	}

	public IconClickEvent(final InventoryClickEvent event, final Player player, final GUIView guiView, final Icon icon, int iconIndex) {
		super(player);
		this.event = event;
		this.guiView = guiView;
		this.icon = icon;
		this.iconIndex = iconIndex;
		cancelled = false;
	}
	
	public GUIView getGuiView() {
		return guiView;
	}
	
	public Icon getIcon() {
		return icon;
	}
	
	public int getIconIndex() {
		return iconIndex;
	}

	@Override
	public boolean isCancelled() {
		return cancelled;
	}

	@Override
	public void setCancelled(boolean cancel) {
		cancelled = cancel;
		event.setCancelled(cancel);
	}
	
}
