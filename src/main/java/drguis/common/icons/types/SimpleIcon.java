package drguis.common.icons.types;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.inventory.ItemStack;

import drguis.common.Action;
import drguis.common.icons.IconProperties;
import drguis.common.icons.SerializableIcon;
import drlibs.utils.functions.ItemsUtils;

public class SimpleIcon implements SerializableIcon {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5481314160307203485L;
	
	private transient ItemStack itemStack;
	private boolean cancelClickEvent;
	private IconProperties properties;
	
	public SimpleIcon(ItemStack itemStack, boolean cancelClickEvent, IconProperties properties) {
		this(itemStack, cancelClickEvent);
		this.properties = properties;
	}
	
	public SimpleIcon(ItemStack itemStack, IconProperties properties) {
		this(itemStack, true, properties);
	}
	
	public SimpleIcon(ItemStack itemStack, boolean cancelClickEvent) {
		this.itemStack = itemStack;
		this.cancelClickEvent = cancelClickEvent;
	}
	
	@Override
	public ItemStack getItemStack() {
		return itemStack;
	}
	
	@Override
	public boolean cancelClickEvent() {
		return cancelClickEvent;
	}
	
	@Override
	public List<Action> getActions() {
		return new ArrayList<>();
	}

	@Override
	public IconProperties getProperties() {
		return properties;
	}
	
	private void writeObject(ObjectOutputStream oos) throws IOException {
		oos.defaultWriteObject();
		oos.writeObject(ItemsUtils.toString(itemStack));
	}
	
	private void readObject(ObjectInputStream ois) throws ClassNotFoundException, IOException {
		ois.defaultReadObject();
		Object object = ois.readObject();
		if (object instanceof String) {
			String string = (String)object;
			itemStack = ItemsUtils.fromString(string);
		} else {
			itemStack = null;
		}
	}

}
