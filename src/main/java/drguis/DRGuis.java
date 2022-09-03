package drguis;

import org.bukkit.plugin.java.JavaPlugin;

public class DRGuis extends JavaPlugin {

	@Override
	public void onEnable() {
		super.onEnable();
		
		System.out.println("[DRGuis] Plugin has been loaded successfully.");
	}
	
	@Override
	public void onDisable() {
		super.onDisable();
		
		System.out.println("[DRGuis] Plugin has been disabled successfully.");
	}
	
}
