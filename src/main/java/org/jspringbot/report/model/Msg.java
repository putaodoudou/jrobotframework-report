package org.jspringbot.report.model;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.thoughtworks.xstream.annotations.XStreamConverter;
import com.thoughtworks.xstream.converters.extended.ToAttributedValueConverter;
import java.util.Date;

/**
 *
 * @author yanshuai
 */
@XStreamAlias("msg")
@XStreamConverter(value = ToAttributedValueConverter.class, strings = {"content"})
public class Msg {

    @XStreamAlias("timestamp")
    @XStreamAsAttribute
    private Date timestamp;

    @XStreamAlias("level")
    @XStreamAsAttribute
    private String level;

    @XStreamAlias("html")
    @XStreamAsAttribute
    private String html;

    private String content;

    public Msg() {
        content = "";
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getLevel() {
        return level;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public void setHtml(String html) {
        this.html = html;
    }

    public String getHtml() {
        return html;
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
        final Msg other = (Msg) obj;
        if (this.timestamp != other.timestamp && (this.timestamp == null || !this.timestamp.equals(other.timestamp))) {
            return false;
        }
        if ((this.level == null) ? (other.level != null) : !this.level.equals(other.level)) {
            return false;
        }
        if ((this.html == null) ? (other.html != null) : !this.html.equals(other.html)) {
            return false;
        }
        return (this.content == null) ? (other.content == null) : this.content.equals(other.content);
    }
}
