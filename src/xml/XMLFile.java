package xml;

import files.TextFile;
import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.*;
import java.io.*;

public class XMLFile {
	
	private Document document;
	private Element documentRoot;
	private XMLElement root;
	
	public XMLFile(String filePath) {
		DocumentBuilderFactory factory =
		DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = null;
		try {
			File inputFile = new File(filePath);
			builder = factory.newDocumentBuilder();
			document = builder.parse(inputFile);
		} catch (ParserConfigurationException | SAXException | IOException e) {
			e.printStackTrace();
		}
		
		document.getDocumentElement().normalize();
		documentRoot = document.getDocumentElement();
		this.root = new XMLElement(documentRoot.getNodeName());
		System.out.println("PARSING");
		this.parseElement(documentRoot, this.root);
	}
	
	public XMLElement getRoot() {
		return this.root;
	}
	
	private void parseElement(Element element, XMLElement xmlElement) {
		
		NodeList nodes = element.getChildNodes();
		NamedNodeMap attributes = element.getAttributes();
		
		for(int i = 0; i < nodes.getLength(); i ++) {
			if(nodes.item(i).getNodeType() == Node.ELEMENT_NODE) {
				Element child = (Element) nodes.item(i);
				XMLElement xmlChild = new XMLElement(child.getNodeName());
				System.out.println("Processing node: " + child.getNodeName());
				xmlElement.addChild(xmlChild);
				this.parseElement(child, xmlChild);
			}
		}
		
		for(int i = 0; i < attributes.getLength(); i ++) {
			Node attribute = attributes.item(i);
			System.out.println("Processing attribute " + attribute.getNodeName() + ": " + attribute.getNodeValue());
			xmlElement.addAttribute(new XMLAttribute(attribute.getNodeValue(), attribute.getNodeName()));
		}
		
	}
	
}
