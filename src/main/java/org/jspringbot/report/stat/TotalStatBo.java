package org.jspringbot.report.stat;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.jspringbot.report.model.comparator.StatComparator;
import org.jspringbot.report.model.Stat;
import org.jspringbot.report.model.Suite;
import org.jspringbot.report.model.Test;
import org.jspringbot.report.model.TotalStat;

/**
 *
 * @author yanshuai
 */
public class TotalStatBo {

    public TotalStat stat(List<Suite> suites) {
        TotalStat totalStat = new TotalStat();
        List<Stat> stats = new ArrayList<Stat>();
        stats.add(new Stat(getCriticalPassTests(suites), getCriticalFailTests(suites),
                "Critical Tests"));
        stats.add(new Stat(getAllPassTests(suites), getAllFailTests(suites),
                "All Tests"));
        Collections.sort(stats, new StatComparator());
        totalStat.setStats(stats);
        return totalStat;
    }

    public int getCriticalPassTests(List<Suite> suites) {
        if (null == suites) {
            return 0;
        }

        int count = 0;
        for (int i = 0; i < suites.size(); ++i) {
            Suite suite = suites.get(i);
            count += getCriticalPassTests(suite.getSuites());
            List<Test> tests = suite.getTests();
            if (null != tests) {
                for (int j = 0; j < tests.size(); ++j) {
                    Test test = tests.get(j);
                    if (test.isCritical()) {
                        if (test.isPass()) {
                            ++count;
                        }
                    }
                }
            }
        }
        return count;
    }

    public int getCriticalFailTests(List<Suite> suites) {
        if (null == suites) {
            return 0;
        }

        int count = 0;
        for (int i = 0; i < suites.size(); ++i) {
            Suite suite = suites.get(i);
            count += getCriticalFailTests(suite.getSuites());
            List<Test> tests = suite.getTests();
            if (null != tests) {
                for (int j = 0; j < tests.size(); ++j) {
                    Test test = tests.get(j);
                    if (test.isCritical()) {
                        if (test.isFail()) {
                            ++count;
                        }
                    }
                }
            }
        }
        return count;
    }

    public int getAllPassTests(List<Suite> suites) {
        if (null == suites) {
            return 0;
        }

        int count = 0;
        for (int i = 0; i < suites.size(); ++i) {
            Suite suite = suites.get(i);
            count += getAllPassTests(suite.getSuites());
            List<Test> tests = suite.getTests();
            if (null != tests) {
                for (int j = 0; j < tests.size(); ++j) {
                    Test test = tests.get(j);
                    if (test.isPass()) {
                        ++count;
                    }
                }
            }
        }
        return count;
    }

    public int getAllFailTests(List<Suite> suites) {
        if (null == suites) {
            return 0;
        }

        int count = 0;
        for (int i = 0; i < suites.size(); ++i) {
            Suite suite = suites.get(i);
            count += getAllFailTests(suite.getSuites());
            List<Test> tests = suite.getTests();
            if (null != tests) {
                for (int j = 0; j < tests.size(); ++j) {
                    Test test = tests.get(j);
                    if (test.isFail()) {
                        ++count;
                    }
                }
            }
        }
        return count;
    }
}