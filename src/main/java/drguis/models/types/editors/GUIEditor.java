package drguis.models.types.editors;

import java.io.Serializable;

import drguis.models.GUIModel;

public interface GUIEditor extends GUIModel, Serializable {

	public GUIModel getGUIModel();
	
}
