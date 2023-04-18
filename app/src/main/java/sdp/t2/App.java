package sdp.t2;

import java.io.File;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class App{
    public static void main(String[] args) {
        try {
            // Create a new DocumentBuilderFactory
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

            // Use the factory to create a new DocumentBuilder
            DocumentBuilder builder = factory.newDocumentBuilder();

            // Parse the XML file
            File xmlFile = new File("dataSample.xml");
            Document doc = builder.parse(xmlFile);

            // Get the root element of the document
            Element root = doc.getDocumentElement();

            // Get a list of all child elements of the root element
            NodeList nodeList = root.getChildNodes();

            // Loop through each child element and print out the tag name and value
            for (int i = 0; i < nodeList.getLength(); i++) {
                if (nodeList.item(i).getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) nodeList.item(i);
                    System.out.println(element.getTagName() + ": " + element.getTextContent());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
