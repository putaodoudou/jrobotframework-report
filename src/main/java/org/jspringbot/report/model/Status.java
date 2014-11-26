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
@XStreamAlias("status")
@XStreamConverter(value = ToAttributedValueConverter.class, strings = {"content"})
public class Status {

    @XStreamAlias("endtime")
    @XStreamAsAttribute
    private Date endtime;

    @XStreamAlias("starttime")
    @XStreamAsAttribute
    private Date starttime;

    @XStreamAlias("status")
    @XStreamAsAttribute
    private String status;

    @XStreamAlias("critical")
    @XStreamAsAttribute
    private String critical;

    private String content;

    public Status() {
        content = "";
    }

    public Date getEndtime() {
        return endtime;
    }

    public void setEndtime(Date endtime) {
        this.endtime = endtime;
    }

    public Date getStarttime() {
        return starttime;
    }

    public void setStarttime(Date starttime) {
        this.starttime = starttime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCritical() {
        return critical;
    }

    public void setCritical(String critical) {
        this.critical = critical;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
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
        final Status other = (Status) obj;
        if (this.endtime != other.endtime && (this.endtime == null || !this.endtime.equals(other.endtime))) {
            return false;
        }
        if (this.starttime != other.starttime && (this.starttime == null || !this.starttime.equals(other.starttime))) {
            return false;
        }
        if ((this.status == null) ? (other.status != null) : !this.status.equals(other.status)) {
            return false;
        }
        if ((this.content == null) ? (other.content != null) : !this.content.equals(other.content)) {
            return false;
        }
        return (this.critical == null) ? (other.critical == null) : this.critical.equals(other.critical);
    }
}
