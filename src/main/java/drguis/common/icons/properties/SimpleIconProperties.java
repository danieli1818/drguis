package drguis.common.icons.properties;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import drguis.common.icons.IconProperties;

public class SimpleIconProperties implements IconProperties {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3084433944226423011L;
	private Map<String, Object> properties;
	
	public SimpleIconProperties() {
		properties = new HashMap<>();
	}
	
	public SimpleIconProperties(IconProperties iconProperties) {
		this();
		if (iconProperties != null) {
			for (String property : iconProperties.getProperties()) {
				properties.put(property, iconProperties.getObject(property));
			}
		}
	}

	@Override
	public Boolean getBoolean(String propertyName) {
		Object propertyValue = properties.get(propertyName);
		if (propertyValue instanceof Boolean) {
			return (Boolean) propertyValue;
		}
		return null;
	}
	
	public SimpleIconProperties setBoolean(String propertyName, boolean propertyValue) {
		properties.put(propertyName, propertyValue);
		return this;
	}

	@Override
	public Integer getInteger(String propertyName) {
		Object propertyValue = properties.get(propertyName);
		if (propertyValue instanceof Integer) {
			return (Integer) propertyValue;
		}
		return null;
	}
	
	public SimpleIconProperties setInteger(String propertyName, int propertyValue) {
		properties.put(propertyName, propertyValue);
		return this;
	}

	@Override
	public Double getDouble(String propertyName) {
		Object propertyValue = properties.get(propertyName);
		if (propertyValue instanceof Double) {
			return (Double) propertyValue;
		}
		return null;
	}
	
	public SimpleIconProperties setDouble(String propertyName, double propertyValue) {
		properties.put(propertyName, propertyValue);
		return this;
	}

	@Override
	public String getString(String propertyName) {
		Object propertyValue = properties.get(propertyName);
		if (propertyValue instanceof String) {
			return (String) propertyValue;
		}
		return null;
	}
	
	public SimpleIconProperties setString(String propertyName, String propertyValue) {
		properties.put(propertyName, propertyValue);
		return this;
	}

	@Override
	public Object getObject(String propertyName) {
		return properties.get(propertyName);
	}
	
	public SimpleIconProperties setObject(String propertyName, Object propertyValue) {
		properties.put(propertyName, propertyValue);
		return this;
	}
	
	public Object removeProperty(String propertyName) {
		return properties.remove(propertyName);
	}

	@Override
	public Set<String> getProperties() {
		return new HashSet<>(properties.keySet());
	}
	
}
