package drguis.models.types.editors;

import org.bukkit.entity.Player;

import drguis.api.GUIsAPI;

public abstract class BaseGUIEditor implements GUIEditor {

	/**
	 * 
	 */
	private static final long serialVersionUID = -792612415421367522L;
	private transient volatile Player editingPlayer;
	
	public BaseGUIEditor() {
		this.editingPlayer = null;
	}
	
	@Override
	public boolean editGUI(Player player) {
		synchronized (this) {
			if (editingPlayer == null) {
				editingPlayer = player;
				GUIsAPI.showGUIToPlayer(player, getGUI(player));
				return true;
			}
		}
		return false;
	}
	
	@Override
	public Player getEditingPlayer() {
		synchronized (this) {
			return editingPlayer;
		}
	}
	
	public Player clearEditingPlayer() {
		synchronized (this) {
			Player player = editingPlayer;
			editingPlayer = null;
			return player;
		}
	}
	
}
