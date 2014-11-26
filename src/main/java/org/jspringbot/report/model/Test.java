package org.jspringbot.report.model;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.thoughtworks.xstream.annotations.XStreamImplicit;
import java.util.List;

/**
 *
 * @author yanshuai
 */
@XStreamAlias("test")
public class Test {

    @XStreamAlias("name")
    @XStreamAsAttribute
    private String name;

    @XStreamAlias("id")
    @XStreamAsAttribute
    private String id;

    @XStreamImplicit(itemFieldName="kw")
    private List<Keyword> kws;

    @XStreamAlias("doc")
    private String doc;

    @XStreamAlias("tags")
    private Tags tags;

    @XStreamAlias("status")
    private Status status;

    public Test() {
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setKws(List<Keyword> kws) {
        this.kws = kws;
    }

    public List<Keyword> getKws() {
        return kws;
    }

    public void setDoc(String doc) {
        this.doc = doc;
    }

    public String getDoc() {
        return doc;
    }

    public void setTags(Tags tags) {
        this.tags = tags;
    }

    public Tags getTags() {
        return tags;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Status getStatus() {
        return status;
    }

    public boolean isCritical() {
        return "yes".equals(status.getCritical());
    }

    public boolean isPass() {
        return "PASS".equals(status.getStatus());
    }

    public boolean isFail() {
        return "FAIL".equals(status.getStatus());
    }
}
