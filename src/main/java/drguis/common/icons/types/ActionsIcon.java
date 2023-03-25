package drguis.common.icons.types;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
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
		actions = new LinkedList<>();
	}
	
	public ActionsIcon(ItemStack itemStack, boolean cancelClickEvent) {
		super(itemStack, cancelClickEvent);
		actions = new LinkedList<>();
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
	
	public ActionsIcon removeAction(int index) {
		if (isValidIndex(index)) {
			actions.remove(index);
		}
		return this;
	}
	
	@Override
	public List<Action> getActions() {
		return actions;
	}
	
	public int setAction(int index, Action action) {
		if (action == null) {
			if (isValidIndex(index)) {
				removeAction(action);
				return index;
			}
			return -1;
		}
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
	
	public boolean swapActions(int index1, int index2) {
		if (!isValidIndex(index1) || !isValidIndex(index2)) {
			return false;
		}
		if (index1 == index2) {
			return true;
		}
		Action action1 = actions.get(index1);
		actions.set(index1, actions.get(index2));
		actions.set(index2, action1);
		return true;
	}
	
	public ActionsIcon addAction(int index, Action action) {
		if (index < 0) {
			index = 0;
		} else if (index > actions.size()) {
			index = actions.size();
		}
		actions.add(index, action);
		return this;
	}
	
	public boolean moveAction(int fromIndex, int toIndex) {
		if (!isValidIndex(fromIndex)) {
			return false;
		}
		if (toIndex < 0) {
			toIndex = 0;
		} else if (toIndex > actions.size()) {
			toIndex = actions.size();
		}
		if (fromIndex == toIndex) {
			return true;
		}
		Action action = actions.get(fromIndex);
		removeAction(fromIndex);
		if (toIndex > fromIndex) {
			toIndex--;
		}
		addAction(toIndex, action);
		return true;
	}
	
	private boolean isValidIndex(int index) {
		return index >= 0 && index < actions.size();
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
