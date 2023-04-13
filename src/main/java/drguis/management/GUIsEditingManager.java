package drguis.management;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;

import drguis.models.types.editors.GUIEditor;
import drlibs.common.files.FileConfigurationsUtils;
import drlibs.common.plugin.FilesPlugin;
import drlibs.utils.files.FilesUtils;
import drlibs.utils.functions.MapsUtils;
import drlibs.utils.reloader.ReloadFileData;
import drlibs.utils.reloader.Reloadable;

public class GUIsEditingManager implements Reloadable {

	private Map<String, GUIEditorData> editors;
	private File guisDir;
	private String guisDirRelativePath;
	private FilesPlugin plugin;

	private Set<String> loadedFiles;

	private static GUIsEditingManager instance;

	private GUIsEditingManager(FilesPlugin plugin, File guisDir) {
		this.editors = new HashMap<>();
		this.guisDir = guisDir;
		this.guisDirRelativePath = plugin.getFileConfigurationsUtils().getRelativeFilePath(guisDir.getAbsolutePath());
		this.plugin = plugin;
		this.loadedFiles = null;
	}

	public static GUIsEditingManager getInstance(FilesPlugin plugin, File guisDir) {
		if (instance == null) {
			instance = new GUIsEditingManager(plugin, guisDir);
		}
		return instance;
	}

	public static GUIsEditingManager getInstance() {
		return instance;
	}

	public boolean registerEditor(String id, GUIEditor editor) {
		return registerEditor(id, editor, guisDirRelativePath + id + ".yml");
	}
	
	public boolean registerEditor(String id, GUIEditor editor, String filePath) {
		if (editor == null || editors.containsKey(id)) {
			return false;
		}
		editors.put(id, new GUIEditorData(editor, filePath));
		return true;
	}

	public boolean unregisterEditor(String id) {
		return editors.remove(id) != null;
	}

	public GUIEditor getGUIEditor(String id) {
		GUIEditorData data = editors.get(id);
		if (data == null) {
			return null;
		}
		return data.getGuiEditor();
	}

	public boolean hasGUIEditor(String id) {
		return editors.containsKey(id);
	}

	private class GUIEditorData {

		private GUIEditor guiEditor;
		private String filePath;

		public GUIEditorData(GUIEditor guiEditor, String filePath) {
			this.guiEditor = guiEditor;
			this.filePath = filePath;
		}

		public GUIEditor getGuiEditor() {
			return guiEditor;
		}
		
		public String getFilePath() {
			return filePath;
		}

	}

	public boolean loadGUIsFromFile(String filePath, boolean shouldOverride) {
		FileConfigurationsUtils fcu = plugin.getFileConfigurationsUtils();
		Set<String> guisIDs = fcu.getKeys(filePath);
		if (guisIDs == null) {
			return false;
		}
		for (String id : guisIDs) {
			try {
				Object guiEditorObject = fcu.getSerializable(filePath, id);
				if (guiEditorObject == null || !(guiEditorObject instanceof GUIEditor)) {
					continue;
				}
				GUIEditor guiEditor = (GUIEditor) guiEditorObject;
				if (!registerEditor(id, guiEditor)) {
					if (shouldOverride) {
						unregisterEditor(id);
						registerEditor(id, guiEditor, filePath);
					} else {
						plugin.getPluginLogger().logTranslated(Level.WARNING, "same_gui_id_no_override", MapsUtils.mapOf("gui_id", id));
					}
				}
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
				return false;
			}
		}
		return true;
	}
	
	public void saveGUIs() throws IOException {
		FileConfigurationsUtils fcu = plugin.getFileConfigurationsUtils();
		Set<String> loadedFilePaths = new HashSet<>();
		for (Map.Entry<String, GUIEditorData> entry : editors.entrySet()) {
			String id = entry.getKey();
			GUIEditorData data = entry.getValue();
			if (!loadedFilePaths.contains(data.getFilePath())) {
				fcu.loadFileConfigurationOrCreate(data.getFilePath());
			}
			fcu.setSerializable(data.getFilePath(), id, data.getGuiEditor());
		}
	}
	
	public boolean saveGUI(String guiID) throws IOException {
		GUIEditorData data = editors.get(guiID);
		if (data == null) {
			return false;
		}
		FileConfigurationsUtils fcu = plugin.getFileConfigurationsUtils();
		System.out.println("File Path:" + data.getFilePath());
		fcu.loadFileConfigurationOrCreate(data.getFilePath());
		fcu.setSerializable(data.getFilePath(), guiID, data.getGuiEditor());
		return fcu.save(data.getFilePath());
	}
	
	public Set<String> getGUIEditorsIDs() {
		return new HashSet<>(editors.keySet());
	}

	@Override
	public Collection<ReloadFileData> getReloadFilenames() {
		if (guisDir != null && guisDir.isDirectory()) {
			try {
				this.loadedFiles = FilesUtils.getAllFilesRecursively(guisDir);
				Set<ReloadFileData> reloadFileDataSet = new HashSet<>();
				for (String filePath : loadedFiles) {
					filePath = plugin.getFileConfigurationsUtils().getRelativeFilePath(filePath);
					reloadFileDataSet.add(new ReloadFileData(filePath));
				}
				return reloadFileDataSet;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		this.loadedFiles = null;
		return null;
	}

	@Override
	public void reload() {
		if (loadedFiles == null) {
			if (guisDir != null) {
				if (!guisDir.mkdirs()) {
					plugin.getPluginLogger().logTranslated(Level.WARNING, "error_cannot_create_dir",
							MapsUtils.mapOf("dir", guisDir.getAbsolutePath()));
				}
			}
			return;
		}
		editors.clear();
		Set<String> successfullyLoadedFiles = new HashSet<>();
		for (String filePath : loadedFiles) {
			filePath = plugin.getFileConfigurationsUtils().getRelativeFilePath(filePath);
			if (loadGUIsFromFile(filePath, false)) {
				successfullyLoadedFiles.add(filePath);
			}
		}
		loadedFiles = successfullyLoadedFiles;
	}

}
