package sdp.t2;

import java.io.File;
import java.util.Scanner;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class App {
    public static void main(String[] args) {
        try {
            // Create a new DocumentBuilderFactory
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

            // Use the factory to create a new DocumentBuilder
            DocumentBuilder builder = factory.newDocumentBuilder();

            // Parse the XML file
            File xmlFile = new File("dataSample.xml");
            Document doc = builder.parse(xmlFile);

            // Get user input for fields to output
            Scanner scanner = new Scanner(System.in);
            System.out.print("Enter fields to output (comma-separated): ");
            String[] fields = scanner.nextLine().split(",");

            // Get a list of all records
            NodeList records = doc.getElementsByTagName("record");

            // Loop through each record and print out the selected fields
            for (int i = 0; i < records.getLength(); i++) {
                Element record = (Element) records.item(i);
                for (String field : fields) {
                    NodeList fieldNodes = record.getElementsByTagName(field.trim());
                    for (int j = 0; j < fieldNodes.getLength(); j++) {
                        Element fieldElement = (Element) fieldNodes.item(j);
                        System.out.println(field + ": " + fieldElement.getTextContent());
                    }
                }
                System.out.println();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
