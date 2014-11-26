package org.jspringbot.report;

import org.jspringbot.report.model.comparator.TestComparator;
import org.jspringbot.report.model.comparator.SuiteComparator;
import com.thoughtworks.xstream.XStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import org.jspringbot.report.model.Errors;
import org.jspringbot.report.model.Msg;
import org.jspringbot.report.model.Robot;
import org.jspringbot.report.model.Statistics;
import org.jspringbot.report.model.Status;
import org.jspringbot.report.model.Suite;
import org.jspringbot.report.model.SuiteStat;
import org.jspringbot.report.model.TagStat;
import org.jspringbot.report.model.Test;
import org.jspringbot.report.model.TotalStat;
import org.jspringbot.report.stat.SuiteStatBo;
import org.jspringbot.report.stat.TagStatBo;
import org.jspringbot.report.stat.TotalStatBo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author yanshuai
 */
public class RobotFrameworkReport {

    public RobotFrameworkReport() {
        xstream = new XStream();
        xstream.registerConverter(new DateConverter("yyyyMMdd HH:mm:ss.SSS"));
        xstream.processAnnotations(Robot.class);
    }

    public String merge(String first, String second) {
        Robot firstRobot = (Robot) xstream.fromXML(first);
        Robot secondRobot = (Robot) xstream.fromXML(second);
        if (!firstRobot.getGenerator().equals(secondRobot.getGenerator())) {
            LOG.warn(String.format("robot xml generator is not the same: one is %s, the other is %s",
                    firstRobot.getGenerator(), secondRobot.getGenerator()));
        }
        Date generated = firstRobot.getGenerated();
        if (generated.after(secondRobot.getGenerated())) {
            generated = secondRobot.getGenerated();
        }

        List<Suite> suites = merge(firstRobot.getSuites(), secondRobot.getSuites());
        updateSuitesId(suites);
        Robot robot = new Robot();
        robot.setGenerator(firstRobot.getGenerator());
        robot.setGenerated(generated);
        robot.setSuites(suites);
        robot.setStatistics(generateStatisticsFromSuites(suites));
        robot.setErrors(merge(firstRobot.getErrors(), secondRobot.getErrors()));
        return xstream.toXML(robot);
    }

    List<Suite> merge(List<Suite> firstSuites, List<Suite> secondSuites) {
        if (null == firstSuites) {
            return secondSuites;
        }

        if (null == secondSuites) {
            return firstSuites;
        }

        SuiteComparator suiteComparator = new SuiteComparator();
        Collections.sort(firstSuites, suiteComparator);
        Collections.sort(secondSuites, suiteComparator);
        int i = 0;
        int j = 0;
        List<Suite> suites = new ArrayList<Suite>();
        while (i < firstSuites.size() && j < secondSuites.size()) {
            Suite firstSuite = firstSuites.get(i);
            Suite secondSuite = secondSuites.get(j);
            int rc = suiteComparator.compare(firstSuite, secondSuite);
            if (0 == rc) {
                suites.add(merge(firstSuite, secondSuite));
                ++i;
                ++j;
            } else if (0 > rc) {
                suites.add(firstSuite);
                ++i;
            } else {
                suites.add(secondSuite);
                ++j;
            }
        }
        while (i < firstSuites.size()) {
            suites.add(firstSuites.get(i));
            ++i;
        }
        while (j < secondSuites.size()) {
            suites.add(secondSuites.get(j));
            ++j;
        }
        return suites;
    }

    Suite merge(Suite firstSuite, Suite secondSuite) {
        Suite suite = new Suite();
        suite.setSource(firstSuite.getSource());
        suite.setName(firstSuite.getName());
        suite.setId(firstSuite.getId());
        suite.setSuites(merge(firstSuite.getSuites(), secondSuite.getSuites()));
        suite.setTests(mergeTests(firstSuite.getTests(), secondSuite.getTests()));
        suite.setMetadata(firstSuite.getMetadata());
        suite.setDoc(firstSuite.getDoc());
        suite.setStatus(mergeStatus(firstSuite.getStatus(), secondSuite.getStatus()));
        return suite;
    }

    Status mergeStatus(Status firstStatus, Status secondStatus) {
        Status status = new Status();
        Date firstStartTime = firstStatus.getStarttime();
        Date firstEndTime = firstStatus.getEndtime();
        Date secondStartTime = secondStatus.getStarttime();
        Date secondEndTime = secondStatus.getEndtime();
        if (firstStartTime.before(secondStartTime)) {
            status.setStarttime(firstStartTime);
        } else {
            status.setStarttime(secondStartTime);
        }
        if (firstEndTime.before(secondEndTime)) {
            status.setEndtime(firstEndTime);
        } else {
            status.setEndtime(secondEndTime);
        }
        if ("PASS".equals(firstStatus.getStatus()) && "PASS".equals(secondStatus.getStatus())) {
            status.setStatus("PASS");
        } else {
            status.setStatus("FAIL");
        }
        return status;
    }

    List<Test> mergeTests(List<Test> firstTests, List<Test> secondTests) {
        if (null == firstTests) {
            return secondTests;
        }

        if (null == secondTests) {
            return firstTests;
        }

        TestComparator testComparator = new TestComparator();
        Collections.sort(firstTests, testComparator);
        Collections.sort(secondTests, testComparator);
        int i = 0;
        int j = 0;
        List<Test> tests = new ArrayList<Test>();
        while (i < firstTests.size() && j < secondTests.size()) {
            Test firstTest = firstTests.get(i);
            Test secondTest = secondTests.get(j);
            int rc = testComparator.compare(firstTest, secondTest);
            if (0 == rc) {
                tests.add(secondTest);
                ++i;
                ++j;
            } else if (0 > rc) {
                tests.add(firstTest);
                ++i;
            } else {
                tests.add(secondTest);
                ++j;
            }
        }
        while (i < firstTests.size()) {
            tests.add(firstTests.get(i));
            ++i;
        }
        while (j < secondTests.size()) {
            tests.add(secondTests.get(j));
            ++j;
        }
        return tests;
    }

    Errors merge(Errors firstErrors, Errors secondErrors) {
        List<Msg> msgAl = new ArrayList<Msg>();
        if (null != firstErrors.getMsgs()) {
            msgAl.addAll(firstErrors.getMsgs());
        }
        if (null != secondErrors.getMsgs()) {
            msgAl.addAll(secondErrors.getMsgs());
        }
        Errors errors = new Errors();
        errors.setMsgs(msgAl);
        return errors;
    }

    Statistics generateStatisticsFromSuites(List<Suite> suites) {
        Statistics statistics = new Statistics();
        statistics.setTotalStat(generateTotalStat(suites));
        statistics.setTagStat(generateTagStat(suites));
        statistics.setSuiteStat(generateSuiteStat(suites));
        return statistics;
    }

    TotalStat generateTotalStat(List<Suite> suites) {
        TotalStatBo totalStatBo = new TotalStatBo();
        return totalStatBo.stat(suites);
    }

    TagStat generateTagStat(List<Suite> suites) {
        TagStatBo tagStatBo = new TagStatBo();
        return tagStatBo.stat(suites);
    }

    SuiteStat generateSuiteStat(List<Suite> suites) {
        SuiteStatBo suiteStatBo = new SuiteStatBo();
        return suiteStatBo.stat(suites);
    }

    void updateSuitesId(List<Suite> suites) {
        updateSuitesId(suites, null);
    }

    void updateSuitesId(List<Suite> suites, String prefix) {
        if (null != suites) {
            for (int i = 0; i < suites.size(); ++i) {
                Suite suite = suites.get(i);
                String suiteId;
                if (null == prefix) {
                    suiteId = String.format("s%d", i + 1);
                } else {
                    suiteId = prefix + "-s" + (i + 1);
                }
                suite.setId(suiteId);
                updateSuitesId(suite.getSuites(), suiteId);
                List<Test> tests = suite.getTests();
                if (null != tests) {
                    for (int j = 0; j < tests.size(); ++j) {
                        Test test = tests.get(j);
                        test.setId(suiteId + "-t" + (j + 1));
                    }
                }
            }
        }
    }

    private static final Logger LOG = LoggerFactory.getLogger(RobotFrameworkReport.class);
    private final XStream xstream;
}
