package drguis.common.items;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.bukkit.inventory.ItemStack;

import drlibs.common.files.FileConfigurationsUtils;
import drlibs.common.plugin.FilesPlugin;
import drlibs.utils.reloader.ReloadFileData;
import drlibs.utils.reloader.Reloadable;

public class UsefulItemStacks implements Reloadable {
	
	private static UsefulItemStacks instance;
	private static final String ITEMS_FILE_PATH = "items.yml";
	
	private FileConfigurationsUtils fcu;
	
	private UsefulItemStacks(FilesPlugin plugin) {
		this.fcu = plugin.getFileConfigurationsUtils();
	}
	
	public ItemStack getItemStack(String id) {
		return new ItemStack(fcu.getItemStack(ITEMS_FILE_PATH, id));
	}

	@Override
	public Collection<ReloadFileData> getReloadFilenames() {
		ReloadFileData data = new ReloadFileData(ITEMS_FILE_PATH, true);
		List<ReloadFileData> datas = new ArrayList<>();
		datas.add(data);
		return datas;
	}

	@Override
	public void reload() {
//		fcu.loadFileConfiguration(ITEMS_FILE_PATH);
	}
	
	public static UsefulItemStacks getInstance() {
		return instance;
	}
	
	public static UsefulItemStacks getInstance(FilesPlugin plugin) {
		if (instance == null) {
			instance = new UsefulItemStacks(plugin);
		}
		return instance;
	}
	
}
