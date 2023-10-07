package org.help.tranlation;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class User {

    private List<Bind> binds = new ArrayList<>();
    
    private static User user = new User();
    private String pathToDir;
    private String currentFile;

    private User() {}

    public void setPathToDir(String path) {
        this.pathToDir = path;
    }

    public String getPathToDir() {
        return pathToDir;
    }

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

    public String getCurrentFile() {
        return currentFile;
    }

    public void setCurrentFile(String fileName) {
        this.currentFile = fileName + ".txt";
    }


    public void writeConfigEndClose() {
        try {
            Files.write(Path.of(user.getPathToDir() + user.getCurrentFile()), configXML());
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Can't write configXML, sothing went wrong");
        }

    }

    private List<String> configXML() {
        List<String> configXML = new ArrayList<>();
        
        configXML.add("<PathToDir>");
        configXML.add(user.getPathToDir());
        configXML.add("</PathToDir>");

        configXML.add("<Binds>");
        
        for(Bind b : user.getBinds()) {

            configXML.add("<Bind>");
            
            configXML.add(b.getName());
            configXML.add(b.getValue());
            if (b.getChildren() != null) {

                configXML.add("<ChildrenBinds>");
                
                for(Bind child : b.getChildren()) {
                    configXML.add("<ChildrenBind>");
                    configXML.add(child.getName());
                    configXML.add(child.getValue());
                    configXML.add("</ChildrenBind>");
                }
                configXML.add("</ChildrenBinds>");
            }
            configXML.add("</Bind>");
        }
        configXML.add("</Binds>");
        return configXML;
    }
}