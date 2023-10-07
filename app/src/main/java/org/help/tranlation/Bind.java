package org.help.tranlation;

import java.util.ArrayList;
import java.util.List;

public class Bind {

    private String name;
    private String value;
    private List<Bind> children = new ArrayList<>();

    public Bind(String name, String value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public List<Bind> getChildren() {
        return children;
    }

    public void addChild(Bind child) {
        children.add(child);
    }

    public void removeChild(Bind child) {
        for(Bind b : children) {
            if (b.equals(child)) {
                children.remove(b);
            }
        }
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result + ((value == null) ? 0 : value.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Bind other = (Bind) obj;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        if (value == null) {
            if (other.value != null)
                return false;
        } else if (!value.equals(other.value))
            return false;
        return true;
    }


}
