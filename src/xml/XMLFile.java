package xml;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import files.ResourceLoader;

public class XMLFile {
	
	private Document document;
	private Element documentRoot;
	private XMLElement root;
	
	public static final int DEFAULT_BUFFER_SIZE = 8192;
	
	private static void copyInputStreamToFile(InputStream inputStream, File file)
            throws IOException {

        // append = false
        try (FileOutputStream outputStream = new FileOutputStream(file, false)) {
            int read;
            byte[] bytes = new byte[DEFAULT_BUFFER_SIZE];
            while ((read = inputStream.read(bytes)) != -1) {
                outputStream.write(bytes, 0, read);
            }
        }

    }
	
	public XMLFile(String filePath) {
		DocumentBuilderFactory factory =
		DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = null;
		try {
			File file = new File("tmp.xml");
			copyInputStreamToFile(ResourceLoader.getResourceStream(filePath), file);
			builder = factory.newDocumentBuilder();
			document = builder.parse(file);
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
