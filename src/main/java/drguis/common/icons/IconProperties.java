package drguis.common.icons;

import java.io.Serializable;
import java.util.Set;

public interface IconProperties extends Serializable {

	public Boolean getBoolean(String propertyName);
	
	public Integer getInteger(String propertyName);
	
	public Double getDouble(String propertyName);
	
	public String getString(String propertyName);
	
	public Object getObject(String propertyName);
	
	public Set<String> getProperties();
	
}
