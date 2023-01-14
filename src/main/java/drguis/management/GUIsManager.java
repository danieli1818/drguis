package drguis.management;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import drguis.models.GUIModel;
import drguis.models.types.editors.GUIEditor;
import drlibs.common.files.FileConfigurationsUtils;
import drlibs.common.plugin.FilesPlugin;

public class GUIsManager {

	private FilesPlugin plugin;
	private Map<String, GUIEditor> guis;
	
	private static GUIsManager instance;
	
	private GUIsManager(FilesPlugin plugin) {
		this.plugin = plugin;
		this.guis = new HashMap<>();
	}
	
	public static GUIsManager getInstance() {
		return instance;
	}
	
	public static GUIsManager getInstance(FilesPlugin plugin) {
		if (instance == null) {
			instance = new GUIsManager(plugin);
		}
		return instance;
	}
	
	public GUIEditor getGUIEditor(String id) {
		return guis.get(id);
	}
	
	public GUIModel getGUIModel(String id) {
		GUIEditor editor = getGUIEditor(id);
		if (editor == null) {
			return null;
		}
		return editor.getGUIModel();
	}
	
	public boolean loadGUIsFromConfigurationFile(String filePath) {
		FileConfigurationsUtils fcu = plugin.getFileConfigurationsUtils();
		if (!fcu.loadFileConfiguration(filePath)) {
			return false;
		}
		for (String key : fcu.getKeys(filePath)) {
			Serializable serializable;
			try {
				serializable = fcu.getSerializable(filePath, key);
				if (serializable == null || !(serializable instanceof GUIEditor)) {
					continue;
				}
				guis.put(key, (GUIEditor) serializable);
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
		return true;
	}
	
	public boolean saveGUIsToConfigurationFile(String filePath) {
		FileConfigurationsUtils fcu = plugin.getFileConfigurationsUtils();
		if (!fcu.loadFileConfiguration(filePath)) {
			return false;
		}
		for (Map.Entry<String, GUIEditor> guiEntry : guis.entrySet()) {
			fcu.setObject(filePath, guiEntry.getKey(), guiEntry.getValue()); // TODO Serialize GUIEditor
		}
		return fcu.save(filePath);
	}
	
}
