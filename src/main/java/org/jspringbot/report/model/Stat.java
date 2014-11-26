package org.jspringbot.report.model;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.thoughtworks.xstream.annotations.XStreamConverter;
import com.thoughtworks.xstream.converters.extended.ToAttributedValueConverter;

/**
 *
 * @author yanshuai
 */
@XStreamAlias("msg")
@XStreamConverter(value = ToAttributedValueConverter.class, strings = {"content"})
public class Stat {

    @XStreamAlias("pass")
    @XStreamAsAttribute
    private int pass;

    @XStreamAlias("fail")
    @XStreamAsAttribute
    private int fail;

    @XStreamAlias("name")
    @XStreamAsAttribute
    private String name;

    @XStreamAlias("id")
    @XStreamAsAttribute
    private String id;

    @XStreamAlias("doc")
    @XStreamAsAttribute
    private String doc;

    @XStreamAlias("links")
    @XStreamAsAttribute
    private String links;

    @XStreamAlias("combined")
    @XStreamAsAttribute
    private String combined;

    @XStreamAlias("info")
    @XStreamAsAttribute
    private String info;

    private String content;

    public Stat() {
        content = "";
    }

    public Stat(int pass, int fail, String content) {
        this.pass = pass;
        this.fail = fail;
        this.content = content;
    }

    public int getPass() {
        return pass;
    }

    public void setPass(int pass) {
        this.pass = pass;
    }

    public int getFail() {
        return fail;
    }

    public void setFail(int fail) {
        this.fail = fail;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDoc() {
        return doc;
    }

    public void setDoc(String doc) {
        this.doc = doc;
    }

    public String getLinks() {
        return links;
    }

    public void setLinks(String links) {
        this.links = links;
    }

    public String getCombined() {
        return combined;
    }

    public void setCombined(String combined) {
        this.combined = combined;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
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
        final Stat other = (Stat) obj;
        if (this.pass != other.pass) {
            return false;
        }
        if (this.fail != other.fail) {
            return false;
        }
        if ((this.name == null) ? (other.name != null) : !this.name.equals(other.name)) {
            return false;
        }
        if ((this.id == null) ? (other.id != null) : !this.id.equals(other.id)) {
            return false;
        }
        if ((this.doc == null) ? (other.doc != null) : !this.doc.equals(other.doc)) {
            return false;
        }
        if ((this.links == null) ? (other.links != null) : !this.links.equals(other.links)) {
            return false;
        }
        if ((this.combined == null) ? (other.combined != null) : !this.combined.equals(other.combined)) {
            return false;
        }
        if ((this.info == null) ? (other.info != null) : !this.info.equals(other.info)) {
            return false;
        }
        return (this.content == null) ? (other.content == null) : this.content.equals(other.content);
    }
}
