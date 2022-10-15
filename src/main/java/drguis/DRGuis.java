package drguis;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import drguis.commands.DRGuisCommands;
import drguis.listeners.GUIsModelsListener;
import drguis.listeners.IconsListener;
import drlibs.common.files.FileConfigurationsLoader;
import drlibs.common.files.FileConfigurationsUtils;
import drlibs.common.plugin.MessagesPlugin;
import drlibs.utils.log.PluginLogger;
import drlibs.utils.messages.MessagesSender;
import drlibs.utils.messages.MessagesStorage;
import drlibs.utils.reloader.ReloaderManager;

public class DRGuis extends JavaPlugin implements MessagesPlugin {

	public static final String ID = "drguis";
	
	private PluginLogger logger;
	private FileConfigurationsUtils fileConfigurationsUtils;
	private ReloaderManager reloaderManager;
	private MessagesSender messagesSender;
	
	@Override
	public void onEnable() {
		super.onEnable();
		
		Bukkit.getPluginManager().registerEvents(new IconsListener(), this);
		
		Bukkit.getPluginManager().registerEvents(new GUIsModelsListener(), this);
		
		logger = new PluginLogger(this);
		
		fileConfigurationsUtils = new FileConfigurationsUtils(new FileConfigurationsLoader(this));
		
		reloaderManager = new ReloaderManager(this);
		
		MessagesStorage messagesStorage = new MessagesStorage(this);
		messagesSender = new MessagesSender(this, messagesStorage);
		
		reloaderManager.registerReloadable(logger);
		reloaderManager.registerReloadable(messagesStorage);
		
		reloaderManager.reloadAllSet();
		
		Bukkit.getPluginCommand("drguis").setExecutor(new DRGuisCommands(this));
		
	}
	
	@Override
	public void onDisable() {
		super.onDisable();
		
	}

	@Override
	public FileConfigurationsUtils getFileConfigurationsUtils() {
		return fileConfigurationsUtils;
	}

	@Override
	public ReloaderManager getReloaderManager() {
		return reloaderManager;
	}

	@Override
	public PluginLogger getPluginLogger() {
		return logger;
	}

	@Override
	public String getID() {
		return ID;
	}

	@Override
	public MessagesSender getMessagesSender() {
		return messagesSender;
	}
	
}
