package org.jspringbot.report.model;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;
import java.util.List;

/**
 *
 * @author yanshuai
 */
@XStreamAlias("arguments")
public class Arguments {

    @XStreamImplicit(itemFieldName="arg")
    private List<String> args;

    public Arguments() {
    }

    public void setArgs(List<String> args) {
        this.args = args;
    }

    public List<String> getArgs() {
        return args;
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
        final Arguments other = (Arguments) obj;
        if (args.size() != other.args.size()) {
            return false;
        }
        for (int i = 0; i < args.size(); ++i) {
            if (null == args.get(i) ? null != other.args.get(i) : !args.get(i).equals(other.args.get(i))) {
                return false;
            }
        }
        return true;
    }
}
