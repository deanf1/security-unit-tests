package com.aspectsecurity.unittestsweb.xxetestcases;

import com.aspectsecurity.unittestsweb.XXETestCase;
import org.codehaus.stax2.XMLInputFactory2;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;

@WebServlet("/xmlinput2unsafevalidationoff")
public class XMLInput2UnsafeValidationOffTestCase extends XXETestCase {

    /*
     * XMLInputFactory2: Unsafe when Disabling Validation Example
     * Proves that disabling validation for the XMLInputFactory2 leaves the XMLStreamReader parsing entities
     */
    protected void doTest(HttpServletRequest request, HttpServletResponse response) throws IOException {

        final boolean expectedSafe = false;

        // parsing the XML
        try {
            XMLInputFactory xmlInputFactory = XMLInputFactory2.newInstance();
            xmlInputFactory.setProperty("javax.xml.stream.isValidating", "false");

            XMLStreamReader xmlStreamReader = xmlInputFactory.createXMLStreamReader(new ByteArrayInputStream(request.getParameter("payload").getBytes()));

            String result = "";
            while(xmlStreamReader.hasNext()) {
                xmlStreamReader.next();
                if (xmlStreamReader.isStartElement()) {
                    if (xmlStreamReader.getLocalName().equals("test")) {
                        result = xmlStreamReader.getElementText();
                    }
                }
            }

            // testing the result
            printResults(expectedSafe, result, response);
        }
        catch (Exception ex) {
            printResults(expectedSafe, ex, response);	// safe: exception thrown when parsing XML
        }
    }
}
