package org.jspringbot.report.model;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;
import java.util.List;

/**
 *
 * @author yanshuai
 */
@XStreamAlias("metadata")
public class Metadata {

    @XStreamImplicit(itemFieldName="item")
    private List<Item> items;

    public Metadata() {
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public List<Item> getItems() {
        return items;
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
        final Metadata other = (Metadata) obj;
        if (items.size() != other.items.size()) {
            return false;
        }
        for (int i = 0; i < items.size(); ++i) {
            if (null == items.get(i) ? null != other.items.get(i) : !items.get(i).equals(other.items.get(i))) {
                return false;
            }
        }
        return true;
    }
}
