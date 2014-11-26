package org.jspringbot.report.model.comparator;

import java.util.Comparator;
import org.jspringbot.report.model.Stat;

/**
 *
 * @author yanshuai
 */
public class StatComparator implements Comparator<Stat> {

    public int compare(Stat firstStat, Stat secondStat) {
        return firstStat.getContent().compareTo(secondStat.getContent());
    }
}
