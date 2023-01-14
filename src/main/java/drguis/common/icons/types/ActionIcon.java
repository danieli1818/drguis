package drguis.common.icons.types;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.inventory.ItemStack;

import drguis.common.Action;
import drguis.common.icons.IconProperties;
import drguis.common.icons.SerializableIcon;

public class ActionIcon extends SimpleIcon implements SerializableIcon {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5120961416648494750L;
	
	private transient Action action;

	public ActionIcon(ItemStack itemStack, IconProperties properties, Action action) {
		super(itemStack, properties);
		this.action = action;
	}
	
	public ActionIcon(ItemStack itemStack, boolean cancelClickEvent, Action action) {
		super(itemStack, cancelClickEvent);
		this.action = action;
	}
	
	@Override
	public List<Action> getActions() {
		List<Action> actions = new ArrayList<>();
		actions.add(action);
		return actions;
	}
	
	private Action setAction(Action action) {
		Action prevAction = this.action;
		this.action = action;
		return prevAction;
	}
	
	private void writeObject(ObjectOutputStream oos) throws IOException {
		oos.defaultWriteObject();
		if (action instanceof Serializable) {
			oos.writeObject(true);
			oos.writeObject(action);
		} else {
			oos.writeObject(false);
		}
	}
	
	private void readObject(ObjectInputStream ois) throws ClassNotFoundException, IOException {
		ois.defaultReadObject();
		boolean hasAction = (boolean)ois.readObject();
		if (hasAction) {
			setAction((Action) ois.readObject());
		}
	}

}
