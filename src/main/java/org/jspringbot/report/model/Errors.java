package org.jspringbot.report.model;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;
import java.util.List;

/**
 *
 * @author yanshuai
 */
@XStreamAlias("errors")
public class Errors {

    @XStreamImplicit(itemFieldName="msg")
    private List<Msg> msgs;

    public Errors() {
    }

    public List<Msg> getMsgs() {
        return msgs;
    }

    public void setMsgs(List<Msg> msgs) {
        this.msgs = msgs;
    }
}
