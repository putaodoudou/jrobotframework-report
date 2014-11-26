package org.jspringbot.report;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import org.apache.commons.io.IOUtils;
import org.custommonkey.xmlunit.DetailedDiff;
import org.custommonkey.xmlunit.XMLUnit;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.xml.sax.SAXException;

/**
 *
 * @author yanshuai
 */
public class RobotFrameworkReportTest {

    @Test(enabled = false)
    public void testMerge() throws IOException, SAXException {
        String firstXml = readXml("/Robot.xml");
        String secondXml = readXml("/ReRobot.xml");
        String content = readXml("/ExpectRobot.xml");
        String xml = report.merge(firstXml, secondXml);
        XMLUnit.setIgnoreWhitespace(true);
        XMLUnit.setIgnoreAttributeOrder(true);
        XMLUnit.setIgnoreDiffBetweenTextAndCDATA(true);
        XMLUnit.setIgnoreComments(true);

        DetailedDiff diff = new DetailedDiff(XMLUnit.compareXML(content, xml));
        List<?> allDifferences = diff.getAllDifferences();
        Assert.assertEquals(allDifferences.size(), 0, "Differences found: " + diff.toString());
    }

    String readXml(String resource) throws IOException {
        InputStream in = null;
        String content = null;
        try {
            in = getClass().getResourceAsStream(resource);
            content = IOUtils.toString(in);
        } finally {
            if (null != in) {
                IOUtils.closeQuietly(in);
            }
        }
        return content;
    }

    private final RobotFrameworkReport report = new RobotFrameworkReport();
}
