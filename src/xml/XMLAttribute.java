package xml;

public class XMLAttribute {
	
	String attribute;
	String name;
	
	public XMLAttribute(String attribute, String name) {
		this.attribute = attribute;
		this.name = name;
	}
	
	public String[] getArray() {
		return this.attribute.split(",");
	}
	
	public int getInt() {
		return Integer.parseInt(attribute);
	}
	
	public String getName() {
		return this.name;
	}
	
	public String getString() {
		return this.attribute;
	}
	
	public boolean getBoolean() {
		return Boolean.valueOf(attribute);
	}
	
	public float getFloat() {
		return Float.valueOf(attribute);
	}
	
}
