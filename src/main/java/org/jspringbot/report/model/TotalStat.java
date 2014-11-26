package org.jspringbot.report.model;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author yanshuai
 */
@XStreamAlias("total")
public class TotalStat {

    @XStreamImplicit(itemFieldName="stat")
    private List<Stat> stats;

    public TotalStat() {
    }

    public List<Stat> getStats() {
        return stats;
    }

    public void setStats(List<Stat> stats) {
        this.stats = stats;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final TotalStat other = (TotalStat) obj;
        if (stats.size() != other.stats.size()) {
            return false;
        }
        for (int i = 0; i < stats.size(); ++i) {
            if (null == stats.get(i) ? null != other.stats.get(i) : !stats.get(i).equals(other.stats.get(i))) {
                return false;
            }
        }
        return true;
    }
}
