package drguis.management;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import drguis.common.CloseReason;

public class PlayersGUIsCloseReasonsManager {

	private Map<UUID, CloseReason> closeReasons;
	
	private static PlayersGUIsCloseReasonsManager instance;
	
	private PlayersGUIsCloseReasonsManager() {
		this.closeReasons = new HashMap<>();
	}
	
	public static PlayersGUIsCloseReasonsManager getInstance() {
		if (instance == null) {
			instance = new PlayersGUIsCloseReasonsManager();
		}
		return instance;
	}
	
	public CloseReason getCloseReason(UUID uuid) {
		return closeReasons.getOrDefault(uuid, CloseReason.NORMAL);
	}
	
	public CloseReason setCloseReason(UUID uuid, CloseReason closeReason) {
		CloseReason prevCloseReason = getCloseReason(uuid);
		closeReasons.put(uuid, closeReason);
		return prevCloseReason;
	}
	
	public CloseReason resetCloseReason(UUID uuid) {
		CloseReason prevCloseReason = getCloseReason(uuid);
		closeReasons.remove(uuid);
		return prevCloseReason;
	}
	
}
