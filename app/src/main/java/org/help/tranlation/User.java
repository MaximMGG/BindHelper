package org.help.tranlation;

import java.util.ArrayList;
import java.util.List;

public class User {

    private List<Bind> binds = new ArrayList<>();
    
    private static User user = new User();

    private User() {}

    public static User getInstance() {
        return user;
    }

    public void addBind(Bind bind) {
        binds.add(bind);
    }

    public List<Bind> getBinds() {
        return binds;
    }

    public void removeBind(String bindName) {
        for(Bind b : binds) {
            if (b.getName().equals(bindName)) {
                binds.remove(b);
                break;
            }
        }
    }

    public void removeBindChild(String childBindName, String bindName) {
        for(Bind b : binds) {
            if (b.getName().equals(bindName)) {
                for(Bind child : b.getChildren()) {
                    if (child.getName().equals(childBindName)) {
                        b.removeChild(child);
                    }
                }
            }
        }
    }

    public void addChildBind(String parent, Bind bind) {
        for(Bind b : binds) {
            if (b.getName().equals(parent)) {
                b.addChild(bind);
            }
        }
    }

}
