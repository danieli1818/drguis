package drguis.common.events;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerEvent;

import drguis.views.GUIView;

public class PlayerInventoryClickEvent extends PlayerEvent implements Cancellable {
	
	private static final HandlerList HANDLERS = new HandlerList();
	
	private GUIView guiView;
	
	private InventoryClickEvent event;

	public static HandlerList getHandlerList() {
		return HANDLERS;
	}
	
	@Override
	public HandlerList getHandlers() {
		return HANDLERS;
	}

	public PlayerInventoryClickEvent(final InventoryClickEvent event, final Player player, final GUIView guiView) {
		super(player);
		this.event = event;
		this.guiView = guiView;
	}
	
	public InventoryClickEvent getInventoryClickEvent() {
		return event;
	}
	
	public GUIView getGuiView() {
		return guiView;
	}
	
	@Override
	public boolean isCancelled() {
		return event.isCancelled();
	}

	@Override
	public void setCancelled(boolean cancel) {
		event.setCancelled(cancel);
	}
	
}
