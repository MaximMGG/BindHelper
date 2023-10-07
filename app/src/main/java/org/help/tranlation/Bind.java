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

    public void setChildren(List<Bind> children) {
        this.children = children;
    }

}
