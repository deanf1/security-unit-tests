package com.aspectsecurity.unittestsweb.xxetestcases;

import com.aspectsecurity.unittestsweb.XXETestCase;
import org.jdom2.input.SAXBuilder;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.IOException;

@WebServlet("/saxbuildersafedoctype")
public class SAXBuilderSafeDOCTYPETestCase extends XXETestCase {

    /*
     * SAXBuilder: Safe when Disallowing DOCTYPE Declarations Example
     * Proves that disallowing DOCTYPE declarations for the SAXBuilder makes it throw an exception when it sees a DTD
     */
    protected void doTest(HttpServletRequest request, HttpServletResponse response) throws IOException {

        final boolean expectedSafe = true;

        // parsing the XML
        try {
            SAXBuilder builder = new SAXBuilder();
            builder.setFeature("http://apache.org/xml/features/disallow-doctype-decl",true);
            org.jdom2.Document document = builder.build(new ByteArrayInputStream(request.getParameter("payload").getBytes()));
            org.jdom2.Element root = document.getRootElement();

            // testing the result
            printResults(expectedSafe, root.getText(), response);
        }
        catch (Exception ex) {
            printResults(expectedSafe, ex, response);	// safe: exception thrown when parsing XML
        }
    }
}
