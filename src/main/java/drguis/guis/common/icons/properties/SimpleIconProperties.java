package drguis.guis.common.icons.properties;

import java.util.HashMap;
import java.util.Map;

import drguis.guis.common.IconProperties;

public class SimpleIconProperties implements IconProperties {

	private Map<String, Object> properties;
	
	public SimpleIconProperties() {
		properties = new HashMap<>();
	}
	
	@Override
	public Boolean getBoolean(String propertyName) {
		Object value = properties.get(propertyName);
		if (!(value instanceof Boolean)) {
			return null;
		}
		return (boolean)value;
	}
	
	public void setBoolean(String propertyName, boolean value) {
		properties.put(propertyName, value);
	}

	@Override
	public Integer getInteger(String propertyName) {
		Object value = properties.get(propertyName);
		if (!(value instanceof Integer)) {
			return null;
		}
		return (int)value;
	}
	
	public void setInteger(String propertyName, int value) {
		properties.put(propertyName, value);
	}

	@Override
	public Double getDouble(String propertyName) {
		Object value = properties.get(propertyName);
		if (!(value instanceof Double)) {
			return null;
		}
		return (double)value;
	}
	
	public void setDouble(String propertyName, double value) {
		properties.put(propertyName, value);
	}

	@Override
	public String getString(String propertyName) {
		Object value = properties.get(propertyName);
		if (!(value instanceof String)) {
			return null;
		}
		return (String)value;
	}
	
	public void setString(String propertyName, String value) {
		properties.put(propertyName, value);
	}

}
