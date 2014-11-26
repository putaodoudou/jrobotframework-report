package org.jspringbot.report.stat;

import com.thoughtworks.xstream.XStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import org.jspringbot.report.DateConverter;
import org.jspringbot.report.model.comparator.StatComparator;
import org.jspringbot.report.model.Robot;
import org.jspringbot.report.model.TotalStat;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 *
 * @author yanshuai
 */
public class TotalStatBoTest {

    @Test
    public void testStat() {
        TotalStat stat = statBo.stat(robot.getSuites());
        TotalStat expectedStat = robot.getStatistics().getTotalStat();
        Collections.sort(expectedStat.getStats(), new StatComparator());
        Assert.assertEquals(stat, expectedStat);
    }

    public TotalStatBoTest() throws IOException {
        XStream xstream = new XStream();
        xstream.registerConverter(new DateConverter("yyyyMMdd HH:mm:ss.SSS"));
        xstream.processAnnotations(Robot.class);
        InputStream in = null;
        robot = null;
        try {
            in = getClass().getResourceAsStream("/Robot.xml");
            robot = (Robot) xstream.fromXML(in);
        } finally {
            if (null != in) {
                in.close();
            }
        }
        Assert.assertNotNull(robot);
        statBo = new TotalStatBo();
    }

    private final TotalStatBo statBo;
    private Robot robot;
}
