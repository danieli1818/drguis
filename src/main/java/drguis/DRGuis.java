package drguis;

import org.bukkit.plugin.java.JavaPlugin;

import drenchantments.enchantments.DREnchantmentsManager;
import drguis.guis.effects.enchantments.Glow;
import drguis.guis.listeners.GUIsListener;

public class DRGuis extends JavaPlugin {

	@Override
	public void onEnable() {
		super.onEnable();
		
		getServer().getPluginManager().registerEvents(new GUIsListener(), this);
		
		DREnchantmentsManager.getInstance().registerEnchantment("Glow", (Integer id)->new Glow(id));
		
		System.out.println("[DRGuis] Plugin has been loaded successfully.");
	}
	
	@Override
	public void onDisable() {
		super.onDisable();
		
		System.out.println("[DRGuis] Plugin has been disabled successfully.");
	}
	
}
