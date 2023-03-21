package drguis;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.plugin.java.JavaPlugin;

import drguis.commands.DRGuisCommands;
import drguis.common.icons.types.ActionsIcon;
import drguis.common.items.UsefulItemStacks;
import drguis.listeners.GUIsModelsListener;
import drguis.listeners.IconsListener;
import drguis.management.ActionsManager;
import drguis.management.GUIsManager;
import drguis.management.PlayersGUIsCloseReasonsManager;
import drguis.models.types.editors.common.guis.actions.CommandActionEditor;
import drlibs.common.files.FileConfigurationsLoader;
import drlibs.common.files.FileConfigurationsUtils;
import drlibs.common.plugin.MessagesPlugin;
import drlibs.items.ItemStackBuilder;
import drlibs.services.events.callers.inventory.DragAndDropInventoryEventCaller;
import drlibs.services.events.callers.inventory.ItemSlotSwapEventCaller;
import drlibs.services.events.callers.inventory.moveitemtootherinventory.MoveItemToOtherInventoryEventCaller;
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
		
		initializeServices();

		Bukkit.getPluginManager().registerEvents(new IconsListener(), this);

		Bukkit.getPluginManager().registerEvents(new GUIsModelsListener(), this);

		logger = new PluginLogger(this);

		fileConfigurationsUtils = new FileConfigurationsUtils(new FileConfigurationsLoader(this));
		
		initializeManagers();
		
		registerActionsIcons();

		Bukkit.getPluginCommand("drguis").setExecutor(new DRGuisCommands(this));

	}

	@Override
	public void onDisable() {
		super.onDisable();

	}

	public void registerActionsIcons() {
		ActionsManager actionsManager = ActionsManager.getInstance();
		actionsManager.registerActionIconData("command", new ItemStackBuilder(Material.COMMAND)
				.setDisplayName("Command Action").setLoreString("Add command action to the icon").build(), (ActionsIcon icon) -> new CommandActionEditor(icon));
	}
	
	public void initializeServices() {
		DragAndDropInventoryEventCaller.getInstance().registerService();
		ItemSlotSwapEventCaller.getInstance().registerService();
		MoveItemToOtherInventoryEventCaller.getInstance().registerService();
	}
	
	public void initializeManagers() {
		reloaderManager = new ReloaderManager(this);

		MessagesStorage messagesStorage = new MessagesStorage(this);
		messagesSender = new MessagesSender(this, messagesStorage);
		
		reloaderManager.registerReloadable(logger);
		reloaderManager.registerReloadable(messagesStorage);
		reloaderManager.registerReloadable(UsefulItemStacks.getInstance(this));

		reloaderManager.reloadAllSet();

		PlayersGUIsCloseReasonsManager.getInstance(); // Initialization
		
		GUIsManager.getInstance(this);
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
