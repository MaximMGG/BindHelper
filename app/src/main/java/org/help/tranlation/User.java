package org.help.tranlation;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
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
           Files.write(Path.of("resources/config.xml"), configXML(), StandardOpenOption.TRUNCATE_EXISTING);
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
            
            configXML.add("<name>%s</name>".formatted(b.getName()));
            configXML.add("<value>%s</value>".formatted(b.getValue()));
            if (b.getChildren() != null) {

                configXML.add("<ChildrenBinds>");
                
                for(Bind child : b.getChildren()) {
                    configXML.add("<ChildrenBind>");
                    configXML.add("<name>%s</name>".formatted(child.getName()));
                    configXML.add("<value>%s</value>".formatted(child.getValue()));
                    configXML.add("</ChildrenBind>");
                }
                configXML.add("</ChildrenBinds>");
            }
            configXML.add("</Bind>");
        }
        configXML.add("</Binds>");
        return configXML;
    }

    public void showAllBinds() {
        for(Bind b : binds) {
            System.out.println("--------------------------------------");
            System.out.printf("bind : %s, value is : %s\n", b.getName(), b.getValue());
            if (b.getChildren() != null) {
                List<Bind> children = b.getChildren();
                for(Bind child : children) {
                    System.out.printf("\tbind : %s, value is : %s\n", child.getName(), child.getValue());
                }
            }
            System.out.println("--------------------------------------");
        }
    }

    public void executBind(String name) {
        for(Bind b : binds) {
            if (b.getName().equals(name)) {
                ConsoleCommand.execute(b.getValue());
            }
        }
    }

    public void executChildBind(String parentName, String child) {
        for(Bind b : binds) {
            if (b.getName().equals(parentName)) {
                for(Bind c : b.getChildren()) {
                    if (c.getName().equals(child)) {
                        ConsoleCommand.execute(c.getValue());
                    }
                }
            }
        }
    }
}
