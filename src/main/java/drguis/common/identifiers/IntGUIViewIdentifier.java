package drguis.common.identifiers;

import drguis.common.GUIViewIdentifier;

public class IntGUIViewIdentifier implements GUIViewIdentifier {

	private int id;
	
	public IntGUIViewIdentifier(int id) {
		this.id = id;
	}
	
	@Override
	public Object getValue(String key) {
		return id;
	}

}
