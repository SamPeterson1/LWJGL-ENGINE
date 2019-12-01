package xml;

import files.TextFile;
import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.*;
import java.io.*;

public class XMLFile {
	
	private Document document;
	private Element root;
	
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
		root = document.getDocumentElement();
	}
	
	public Element getRoot() {
		return this.root;
	}
	
}
