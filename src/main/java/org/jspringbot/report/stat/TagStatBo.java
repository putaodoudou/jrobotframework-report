package org.jspringbot.report.stat;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.jspringbot.report.model.comparator.StatComparator;
import org.jspringbot.report.model.Stat;
import org.jspringbot.report.model.Suite;
import org.jspringbot.report.model.TagStat;
import org.jspringbot.report.model.Test;

/**
 *
 * @author yanshuai
 */
public class TagStatBo {

    Map<String, Integer> tagPassStatMap;
    Map<String, Integer> tagFailStatMap;

    public TagStatBo() {
        tagPassStatMap = new HashMap<String, Integer>();
        tagFailStatMap = new HashMap<String, Integer>();
    }

    public TagStat stat(List<Suite> suites) {
        tagPassStatMap.clear();
        tagFailStatMap.clear();
        TagStat tagStat = new TagStat();
        tagStat(suites);
        List<Stat> stats = new ArrayList<Stat>();
        Set<String> tags = new HashSet<String>();
        tags.addAll(tagPassStatMap.keySet());
        tags.addAll(tagFailStatMap.keySet());
        for (String tag : tags) {
            Integer pass = tagPassStatMap.get(tag);
            if (null == pass) {
                pass = 0;
            }
            Integer fail = tagFailStatMap.get(tag);
            if (null == fail) {
                fail = 0;
            }
            Stat stat = new Stat();
            stat.setCombined("");
            stat.setContent(tag);
            stat.setDoc("");
            stat.setFail(fail);
            stat.setInfo("");
            stat.setLinks("");
            stat.setFail(fail);
            stats.add(stat);
        }
        Collections.sort(stats, new StatComparator());
        tagStat.setStats(stats);
        return tagStat;
    }

    void tagStat(List<Suite> suites) {
        if (null != suites) {
            for (int i = 0; i < suites.size(); ++i) {
                Suite suite = suites.get(i);
                tagStat(suite.getSuites());
                List<Test> tests = suite.getTests();
                if (null == tests) {
                    continue;
                }
                for (int j = 0; j < tests.size(); ++j) {
                    Test test = tests.get(j);
                    List<String> tags = test.getTags().getTags();
                    if (test.isPass()) {
                        incrementTagStatCount(tags, tagPassStatMap);
                    } else if (test.isFail()) {
                        incrementTagStatCount(tags, tagFailStatMap);
                    }
                }
            }
        }
    }

    void incrementTagStatCount(List<String> tags, Map<String, Integer> m) {
        if (null != tags) {
            for (String tag : tags) {
                incrementTagStatCount(tag, m);
            }
        }
    }

    void incrementTagStatCount(String tag, Map<String, Integer> m) {
        Integer count = m.get(tag);
        if (null == count) {
            count = 0;
        }
        ++count;
        m.put(tag, count);
    }
}
