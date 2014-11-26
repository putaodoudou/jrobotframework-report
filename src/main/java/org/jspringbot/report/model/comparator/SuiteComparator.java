package org.jspringbot.report.model.comparator;

import java.util.Comparator;
import org.jspringbot.report.model.Suite;

/**
 *
 * @author yanshuai
 */
public class SuiteComparator implements Comparator<Suite> {

    public int compare(Suite firstSuite, Suite secondSuite) {
        return firstSuite.getName().compareTo(secondSuite.getName());
    }
}
