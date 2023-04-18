package sdp.t2;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class App {
    
    public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException {
        // Get user input for fields to output
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter fields to output (comma-separated): ");
        String[] fields = scanner.nextLine().split(",");

        // Create a SAXParserFactory and configure it
        SAXParserFactory factory = SAXParserFactory.newInstance();
        factory.setNamespaceAware(true);
        factory.setValidating(false);

        // Create a SAXParser and parse the XML file
        SAXParser parser = factory.newSAXParser();
        parser.parse(new File("data.xml"), new MySAXHandler(fields));
    }

    private static class MySAXHandler extends DefaultHandler {
        private List<String> fields;
        private boolean inRecord;
        private StringBuilder currentValue;
        private List<String> currentFields;

        public MySAXHandler(String[] fields) {
            this.fields = new ArrayList<>();
            for (String field : fields) {
                this.fields.add(field.trim());
            }
            this.inRecord = false;
            this.currentValue = new StringBuilder();
            this.currentFields = new ArrayList<>();
        }

        @Override
        public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
            if (qName.equalsIgnoreCase("record")) {
                inRecord = true;
                currentFields.clear();
                currentValue.setLength(0);
            }
        }

        @Override
        public void characters(char[] ch, int start, int length) throws SAXException {
            if (inRecord) {
                currentValue.append(ch, start, length);
            }
        }

        @Override
        public void endElement(String uri, String localName, String qName) throws SAXException {
            if (inRecord) {
                if (qName.equalsIgnoreCase("record")) {
                    inRecord = false;
                    // Print out the selected fields in JSON format
                    StringBuilder output = new StringBuilder("{");
                    for (String field : fields) {
                        if (currentFields.contains(field)) {
                            String value = currentValue.toString().trim();
                            value = value.replaceAll("\\n", "").replaceAll("\\r", "").replaceAll("\\s+", " ");
                            output.append("\"").append(field).append("\":\"").append(value).append("\",");
                        }
                    }
                    output.setLength(output.length() - 1);
                    output.append("}");
                    System.out.println(output.toString());
                } else {
                    String fieldName = qName.trim();
                    if (fields.contains(fieldName) && !currentFields.contains(fieldName)) {
                        currentFields.add(fieldName);
                    }
                }
            }
        }
    }
}
