package drguis.management;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import drguis.common.Icon;
import drguis.common.icons.types.ActionsIcon;
import drguis.common.icons.types.SimpleIcon;
import drguis.models.GUIModel;
import drguis.models.types.editors.common.guis.icons.actions.ActionsIconEditor;
import drguis.models.types.editors.common.guis.icons.simple.SimpleIconEditor;

public class GUIsEditorsManager {

	private Map<String, Function<Icon, GUIModel>> editors;
	
	private static GUIsEditorsManager defaultInstance;
	
	public GUIsEditorsManager() {
		this.editors = new HashMap<>();
	}
	
	public static GUIsEditorsManager getDefaultInstance() {
		if (defaultInstance == null) {
			defaultInstance = new GUIsEditorsManager();
			defaultInstance.registerEditor(SimpleIcon.getType(), (Icon icon) -> new SimpleIconEditor((SimpleIcon) icon));
			defaultInstance.registerEditor(ActionsIcon.getType(), (Icon icon) -> new ActionsIconEditor((ActionsIcon) icon));
		}
		return defaultInstance;
	}
	
	public boolean registerEditor(String id, Function<Icon, GUIModel> editor) {
		if (editors.containsKey(id)) {
			return false;
		}
		editors.put(id, editor);
		return true;
	}
	
	public boolean unregisterEditor(String id) {
		return editors.remove(id) != null;
	}
	
	public boolean hasEditor(Icon icon) {
		return editors.containsKey(icon.getClassType());
	}
	
	public GUIModel getEditor(Icon icon) {
		Function<Icon, GUIModel> editor = editors.get(icon.getClassType());
		if (editor == null) {
			return null;
		}
		return editor.apply(icon);
	}
	
}
