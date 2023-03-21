package drguis.common.icons.types;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.bukkit.inventory.ItemStack;

import drguis.common.Action;
import drguis.common.Icon;
import drguis.common.icons.IconProperties;
import drguis.common.icons.SerializableIcon;
import drguis.common.icons.properties.SimpleIconProperties;

public class ActionsIcon extends SimpleIcon implements SerializableIcon {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1858640153495680892L;
	
	private transient List<Action> actions;
	
	public ActionsIcon(ItemStack itemStack, IconProperties properties) {
		super(itemStack, properties);
		actions = new ArrayList<>();
	}
	
	public ActionsIcon(ItemStack itemStack, boolean cancelClickEvent) {
		super(itemStack, cancelClickEvent);
		actions = new ArrayList<>();
	}
	
	public ActionsIcon addAction(Action action) {
		actions.add(action);
		return this;
	}
	
	public ActionsIcon addActions(Collection<? extends Action> actions) {
		this.actions.addAll(actions);
		return this;
	}
	
	public ActionsIcon removeAction(Action action) {
		actions.remove(action);
		return this;
	}
	
	public ActionsIcon removeActions(Collection<? extends Action> actions) {
		actions.removeAll(actions);
		return this;
	}
	
	@Override
	public List<Action> getActions() {
		return actions;
	}
	
	public int setAction(int index, Action action) {
		if (index < 0) {
			return -1;
		}
		if (actions.size() > index) {
			actions.set(index, action);
			return index;
		}
		actions.add(action);
		return actions.size() - 1;
	}
	
	@Override
	public String getClassType() {
		return ActionsIcon.getType();
	}
	
	public static String getType() {
		return "actions_icon";
	}
	
	@Override
	public Icon cloneIcon() {
		return new ActionsIcon(new ItemStack(getItemStack()), new SimpleIconProperties(getProperties())).addActions(actions);
	}
	
	private void writeObject(ObjectOutputStream oos) throws IOException {
		oos.defaultWriteObject();
		List<Action> serializableActions = new ArrayList<>();
		for (Action action : getActions()) {
			if (action instanceof Serializable) {
				System.out.println("Adding Serializable Action To ActionsIcon's Serializing " + action.getClass().getName());
				serializableActions.add(action);
			}
		}
		oos.writeObject(serializableActions);
	}
	
	@SuppressWarnings("unchecked")
	private void readObject(ObjectInputStream ois) throws ClassNotFoundException, IOException {
		ois.defaultReadObject();
		actions = (List<Action>) ois.readObject();
	}

}
