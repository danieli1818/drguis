package drguis.common.icons.types;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.inventory.ItemStack;

import drguis.common.Action;
import drguis.common.Icon;
import drguis.common.icons.IconProperties;
import drguis.common.icons.IconPropertiesFields;
import drguis.common.icons.SerializableIcon;
import drguis.common.icons.properties.SimpleIconProperties;
import drlibs.utils.functions.ItemsUtils;

public class SimpleIcon implements SerializableIcon {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5481314160307203485L;
	
	private transient ItemStack itemStack;
	private IconProperties properties;
	
	public SimpleIcon(ItemStack itemStack, IconProperties properties) {
		this.itemStack = itemStack;
		this.properties = properties;
	}
	
	public SimpleIcon(ItemStack itemStack, boolean cancelClickEvent) {
		this(itemStack, new SimpleIconProperties().setBoolean(IconPropertiesFields.CANCEL_CLICK_EVENT_FIELD, cancelClickEvent));
	}
	
	@Override
	public ItemStack getItemStack() {
		return itemStack;
	}
	
	@Override
	public boolean cancelClickEvent() {
		Boolean cancelClickEvent = getProperties().getBoolean(IconPropertiesFields.CANCEL_CLICK_EVENT_FIELD);
		if (cancelClickEvent == null) {
			return true;
		}
		return cancelClickEvent.booleanValue();
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

	@Override
	public ItemStack setItemStack(ItemStack itemStack) {
		ItemStack prevItemStack = getItemStack();
		this.itemStack = itemStack;
		return prevItemStack;
	}
	
	@Override
	public String getClassType() {
		return SimpleIcon.getType();
	}

	public static String getType() {
		return "simple_icon";
	}
	
	@Override
	public Icon cloneIcon() {
		return new SimpleIcon(new ItemStack(itemStack), properties); // TODO implement
	}

}
