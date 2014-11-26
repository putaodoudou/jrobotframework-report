package org.jspringbot.report.stat;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.jspringbot.report.model.comparator.StatComparator;
import org.jspringbot.report.model.Stat;
import org.jspringbot.report.model.Suite;
import org.jspringbot.report.model.SuiteStat;
import org.jspringbot.report.model.Test;

/**
 *
 * @author yanshuai
 */
public class SuiteStatBo {

    List<Stat> stats;

    public SuiteStatBo() {
        stats = new ArrayList<Stat>();
    }

    public SuiteStat stat(List<Suite> suites) {
        stats.clear();
        suiteStat(suites);
        SuiteStat stat = new SuiteStat();
        Collections.sort(stats, new StatComparator());
        stat.setStats(stats);
        return stat;
    }

    List<Stat> suiteStat(List<Suite> suites) {
        return suiteStat(suites, null);
    }

    List<Stat> suiteStat(List<Suite> suites, String prefix) {
        List<Stat> statAl = new ArrayList<Stat>();
        if (null == suites) {
            return statAl;
        }
        for (int i = 0; i < suites.size(); ++i) {
            Suite suite = suites.get(i);
            String p = prefix;
            if (null == p) {
                p = suite.getName();
            } else {
                p = p + "." + suite.getName();
            }
            List<Stat> tmpStatAl = suiteStat(suite.getSuites(), p);
            int pass = 0;
            int fail = 0;
            for (Stat stat : tmpStatAl) {
                pass += stat.getPass();
                fail += stat.getFail();
            }
            List<Test> tests = suite.getTests();
            if (null != tests) {
                for (Test test : tests) {
                    if (test.isPass()) {
                        ++pass;
                    } else if (test.isFail()) {
                        ++fail;
                    }
                }
            }
            Stat stat = new Stat();
            stat.setPass(pass);
            stat.setFail(fail);
            stat.setName(suite.getName());
            stat.setId(suite.getId());
            stat.setContent(p);
            statAl.add(stat);
            stats.add(stat);
        }
        return statAl;
    }
}
