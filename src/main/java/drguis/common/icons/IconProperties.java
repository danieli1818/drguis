package drguis.common.icons;

public interface IconProperties {

	public Boolean getBoolean(String propertyName);
	
	public Integer getInteger(String propertyName);
	
	public Double getDouble(String propertyName);
	
	public String getString(String propertyName);
	
	public Object getObject(String propertyName);
	
}
