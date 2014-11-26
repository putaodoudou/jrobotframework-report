package org.jspringbot.report.model;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.thoughtworks.xstream.annotations.XStreamImplicit;
import java.util.List;

/**
 *
 * @author yanshuai
 */
@XStreamAlias("kw")
public class Keyword {

    @XStreamAlias("type")
    @XStreamAsAttribute
    private String type;

    @XStreamAlias("name")
    @XStreamAsAttribute
    private String name;

    @XStreamAlias("doc")
    private String doc;

    @XStreamAlias("arguments")
    private Arguments arguments;

    @XStreamImplicit(itemFieldName="kw")
    private List<Keyword> kws;

    @XStreamImplicit(itemFieldName="msg")
    private List<Msg> msgs;

    @XStreamAlias("status")
    private Status status;

    public Keyword() {
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setDoc(String doc) {
        this.doc = doc;
    }

    public String getDoc() {
        return doc;
    }

    public void setArguments(Arguments arguments) {
        this.arguments = arguments;
    }

    public Arguments getArguments() {
        return arguments;
    }

    public void setMsgs(List<Msg> msgs) {
        this.msgs = msgs;
    }

    public List<Msg> getMsgs() {
        return msgs;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Status getStatus() {
        return status;
    }

    public void setKws(List<Keyword> kws) {
        this.kws = kws;
    }

    public List<Keyword> getKws() {
        return kws;
    }
}
