package org.jspringbot.report.model;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.thoughtworks.xstream.annotations.XStreamImplicit;
import java.util.List;

/**
 *
 * @author yanshuai
 */
@XStreamAlias("suite")
public class Suite {

    @XStreamAlias("source")
    @XStreamAsAttribute
    private String source;

    @XStreamAlias("name")
    @XStreamAsAttribute
    private String name;

    @XStreamAlias("id")
    @XStreamAsAttribute
    private String id;

    @XStreamImplicit(itemFieldName="suite")
    private List<Suite> suites;

    @XStreamImplicit(itemFieldName="test")
    private List<Test> tests;

    @XStreamAlias("doc")
    private String doc;

    @XStreamAlias("metadata")
    private Metadata metadata;

    @XStreamAlias("status")
    private Status status;

    public Suite() {
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getSource() {
        return source;
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

    public void setSuites(List<Suite> suites) {
        this.suites = suites;
    }

    public List<Suite> getSuites() {
        return suites;
    }

    public void setTests(List<Test> tests) {
        this.tests = tests;
    }

    public List<Test> getTests() {
        return tests;
    }

    public void setDoc(String doc) {
        this.doc = doc;
    }

    public String getDoc() {
        return doc;
    }

    public void setMetadata(Metadata metadata) {
        this.metadata = metadata;
    }

    public Metadata getMetadata() {
        return metadata;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Status getStatus() {
        return status;
    }
}
