package org.jspringbot.report.model;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

/**
 *
 * @author yanshuai
 */
@XStreamAlias("statistics")
public class Statistics {

    @XStreamAlias("total")
    @XStreamAsAttribute
    private TotalStat totalStat;

    @XStreamAlias("tag")
    @XStreamAsAttribute
    private TagStat tagStat;

    @XStreamAlias("suite")
    @XStreamAsAttribute
    private SuiteStat suiteStat;

    public Statistics() {
    }

    public TotalStat getTotalStat() {
        return totalStat;
    }

    public void setTotalStat(TotalStat totalStat) {
        this.totalStat = totalStat;
    }

    public TagStat getTagStat() {
        return tagStat;
    }

    public void setTagStat(TagStat tagStat) {
        this.tagStat = tagStat;
    }

    public SuiteStat getSuiteStat() {
        return suiteStat;
    }

    public void setSuiteStat(SuiteStat suiteStat) {
        this.suiteStat = suiteStat;
    }
}
