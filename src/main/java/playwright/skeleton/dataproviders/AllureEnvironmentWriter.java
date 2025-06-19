package playwright.skeleton.dataproviders;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.util.Map;

public class AllureEnvironmentWriter {

    private static final String OUTPUT_PATH = "target/allure-results/environment.xml";

    private AllureEnvironmentWriter() {}

    public static void write(Map<String, String> environment) {
        try {
            // Create XML document
            DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document doc = builder.newDocument();
            Element root = doc.createElement("environment");
            doc.appendChild(root);

            // Add parameters
            for (Map.Entry<String, String> entry : environment.entrySet()) {
                Element parameter = doc.createElement("parameter");

                Element key = doc.createElement("key");
                key.setTextContent(entry.getKey());

                Element value = doc.createElement("value");
                value.setTextContent(entry.getValue());

                parameter.appendChild(key);
                parameter.appendChild(value);
                root.appendChild(parameter);
            }

            // Ensure output directory exists
            File outputFile = new File(OUTPUT_PATH);
            File parentDir = outputFile.getParentFile();
            if (!parentDir.exists() && !parentDir.mkdirs()) {
                throw new RuntimeException("Failed to create directory for Allure environment file");
            }

            // Write to environment.xml
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            transformerFactory.setAttribute(XMLConstants.ACCESS_EXTERNAL_DTD, "");
            transformerFactory.setAttribute(XMLConstants.ACCESS_EXTERNAL_STYLESHEET, "");
            Transformer transformer = transformerFactory.newTransformer();

            transformer.transform(new DOMSource(doc), new StreamResult(outputFile));
        } catch (Exception e) {
            throw new RuntimeException("Failed to write Allure environment.xml", e);
        }
    }
}