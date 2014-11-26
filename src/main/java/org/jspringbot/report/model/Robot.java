package org.jspringbot.report.model;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.thoughtworks.xstream.annotations.XStreamImplicit;
import java.util.Date;
import java.util.List;

/**
 *
 * robot
 *   |-  suite
 *   |     |-  test
 *   |     |     |-  kw
 *   |     |     |     |-  doc
 *   |     |     |     |-  arguments
 *   |     |     |     |     |- arg
 *   |     |     |     |-  msg
 *   |     |     |     |-  status
 *   |     |     |-  doc
 *   |     |     |-  tags
 *   |     |     |     |-  tag
 *   |     |     |-  status
 *   |     |-  suite
 *   |     |-  doc
 *   |     |-  metadata
 *   |     |-  status
 *   |-  statistics
 *   |     |-  total
 *   |     |     |-  stat
 *   |     |-  tag
 *   |     |     |-  stat
 *   |     |-  suite
 *   |     |     |-  stat
 *   |-  errors
 *   |     |-  msg
 * @author yanshuai
 */
@XStreamAlias("robot")
public class Robot {

    @XStreamAlias("generator")
    @XStreamAsAttribute
    private String generator;

    @XStreamAlias("generated")
    @XStreamAsAttribute
    private Date generated;

    @XStreamImplicit(itemFieldName="suite")
    private List<Suite> suites;

    @XStreamAlias("statistics")
    private Statistics statistics;

    @XStreamAlias("errors")
    private Errors errors;

    public Robot() {
    }

    public String getGenerator() {
        return generator;
    }

    public void setGenerator(String generator) {
        this.generator = generator;
    }

    public Date getGenerated() {
        return generated;
    }

    public void setGenerated(Date generated) {
        this.generated = generated;
    }

    public List<Suite> getSuites() {
        return suites;
    }

    public void setSuites(List<Suite> suites) {
        this.suites = suites;
    }

    public Statistics getStatistics() {
        return statistics;
    }

    public void setStatistics(Statistics statistics) {
        this.statistics = statistics;
    }

    public void setErrors(Errors errors) {
        this.errors = errors;
    }

    public Errors getErrors() {
        return errors;
    }
}
