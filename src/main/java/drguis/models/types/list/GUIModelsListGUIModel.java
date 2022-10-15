package drguis.models.types.list;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Player;

import drguis.models.GUIModel;
import drguis.views.GUIView;

public class GUIModelsListGUIModel extends BaseListGUIModel implements ListGUIModel {

	private List<GUIModel> guiModels;
	
	public GUIModelsListGUIModel(int guiPageSize) {
		super(guiPageSize, null);
		this.guiModels = new ArrayList<>();
	}
	
	public GUIModelsListGUIModel(int guiPageSize, List<GUIModel> guiModels) {
		this(guiPageSize);
		if (guiModels != null) {
			this.guiModels.addAll(guiModels);
		}
	}
	
	public GUIModelsListGUIModel addGUIModel(GUIModel guiModel) {
		guiModels.add(guiModel);
		return this;
	}
	
	public GUIModelsListGUIModel addGUIModels(List<GUIModel> guiModels) {
		this.guiModels.addAll(guiModels);
		return this;
	}
	
	public GUIModelsListGUIModel removeGUIModel(GUIModel guiModel) {
		guiModels.remove(guiModel);
		return this;
	}
	
	public GUIModelsListGUIModel removeGUIModels(List<GUIModel> guiModels) {
		this.guiModels.removeAll(guiModels);
		return this;
	}

	@Override
	public GUIView getGUIPage(Player player, int pageIndex) {
		if (pageIndex < 0 || pageIndex >= getNumOfPages(player)) {
			return null;
		}
		GUIView guiView = guiModels.get(pageIndex).getGUI(player);
		addPrevNextIcons(player, guiView, pageIndex);
		return guiView;
	}

	@Override
	public int getNumOfPages(Player player) {
		return guiModels.size();
	}

}
