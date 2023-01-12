package drguis.management;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;
import java.util.UUID;

import drguis.views.GUIView;

public class GUIsStack {
	
	private Map<UUID, Stack<GUIView>> guisStacksPerPlayer;
	
	private static GUIsStack instance;
	
	private GUIsStack() {
		this.guisStacksPerPlayer = new HashMap<>();
	}
	
	public static GUIsStack getInstance() {
		if (instance == null) {
			instance = new GUIsStack();
		}
		return instance;
	}
	
	public void addGUIViewToPlayer(UUID uuid, GUIView guiView) {
		System.out.println("Adding GUIView To Player!");
		Stack<GUIView> guisStackOfPlayer = guisStacksPerPlayer.get(uuid);
		if (guisStackOfPlayer == null) {
			guisStacksPerPlayer.put(uuid, new Stack<>());
			guisStackOfPlayer = guisStacksPerPlayer.get(uuid);
		}
		guisStackOfPlayer.add(guiView);
	}
	
	public GUIView removeGUIViewToPlayer(UUID uuid) {
		Stack<GUIView> guisStackOfPlayer = guisStacksPerPlayer.get(uuid);
		if (guisStackOfPlayer == null || guisStackOfPlayer.isEmpty()) {
			return null;
		}
		GUIView guiView = guisStackOfPlayer.pop();
		if (guisStackOfPlayer.isEmpty()) {
			guisStacksPerPlayer.remove(uuid);
		}
		return guiView;
	}
	
	public GUIView getGUIViewToPlayer(UUID uuid) {
		Stack<GUIView> guisStackOfPlayer = guisStacksPerPlayer.get(uuid);
		if (guisStackOfPlayer == null || guisStackOfPlayer.isEmpty()) {
			return null;
		}
		return guisStackOfPlayer.peek();
	}
	
	public boolean hasGUIView(UUID uuid) {
		return getGUIViewToPlayer(uuid) != null;
	}
	
	public void clearGUIViewOfPlayer(UUID uuid) {
		guisStacksPerPlayer.remove(uuid);
	}

}
