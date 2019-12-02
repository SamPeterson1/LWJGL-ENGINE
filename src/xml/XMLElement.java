package xml;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class XMLElement {
	
	private XMLElement parent;
	private Map<String, List<XMLElement>> children = new HashMap<>();
	private Map<String, XMLAttribute> attributes = new HashMap<>();
	private String name;
	
	public XMLElement(String name) {
		this.name = name;
	}
	
	public String getName() {
		return this.name;
	}
	
	public void setParent(XMLElement parent) {
		this.parent = parent;
	}
	
	public XMLElement getParent() {
		return this.parent;
	}
	
	public void addChild(XMLElement child) {
		
		if(this.children.containsKey(child.getName())) {
			this.children.get(child.getName()).add(child);
		} else {
			List<XMLElement> elements = new ArrayList<>();
			elements.add(child);
			this.children.put(child.getName(), elements);
		}
		child.setParent(this);
		
	}
	
	public void addAttribute(XMLAttribute attribute) {
		this.attributes.put(attribute.getName(), attribute);
	}
	
	public boolean hasAttribute(String name) {
		return this.attributes.containsKey(name);
	}
	
	public XMLElement anyChildWithName(String name) {
		return this.children.get(name).get(0);
	}
	
	public Collection<XMLAttribute> getAttributes() {
		return this.attributes.values();
	}
	
	public List<XMLElement> getChildren() {
		
		Collection<List<XMLElement>> children = this.children.values();
		List<XMLElement> retVal = new ArrayList<>();
		for(List<XMLElement> elements: children) {
			for(XMLElement element: elements) {
				retVal.add(element);
			}
		}
		
		return retVal;
		
	}
	
	public List<XMLElement> getChildrenByName(String name) {
		return this.children.get(name);
	}
	
	public boolean hasChildWithName(String name) {
		return this.children.containsKey(name);
	}
	
	public XMLAttribute getAttribute(String name) {
		return this.attributes.get(name);
	}
	
}
