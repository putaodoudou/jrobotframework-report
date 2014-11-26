package org.jspringbot.report.model.comparator;

import java.util.Comparator;
import org.jspringbot.report.model.Test;

/**
 *
 * @author yanshuai
 */
public class TestComparator implements Comparator<Test> {

    public int compare(Test firstTest, Test secondTest) {
        return firstTest.getName().compareTo(secondTest.getName());
    }
}
