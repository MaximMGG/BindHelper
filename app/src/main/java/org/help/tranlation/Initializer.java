package org.help.tranlation;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class Initializer extends Thread {

    private final String pathToConf = "resources/config.xml";

    private User user;

    public Initializer() {
        user = User.getInstance();
        start();
    }

    @Override
    public void run() {
        checkOrCreateConfig();
    }

    public void checkOrCreateConfig() {
        if (!(Files.exists(Path.of(pathToConf)))) {
            System.out.println("Please set path to directory enter: dir \"full_path_to_dir\"");
            try {
                Files.createDirectory(Path.of("resources"));
                Files.createFile(Path.of(pathToConf));
                addStructureInConfig();
            } catch (IOException e) {
                e.printStackTrace();
                throw new RuntimeException("Can't create config");
            }
        } else {
            loadConfig();
        }
    }


    private void addStructureInConfig() {
        List<String> structure = new ArrayList<>();
        structure.add("<PathToDir>");
        structure.add("<Path>p</Path>");
        structure.add("</PathToDir>");
        structure.add("<Binds>");
        structure.add("</Binds>");
        try {
            Files.write(Path.of(pathToConf), structure);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Can't write sructure in config.xml");
        }
    }

    private void loadConfig() {
        List<String> config;
        try {
            config = Files.readAllLines(Path.of("resources/config.xml"));
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Can't read config, please check it'");
            throw new RuntimeException(e);
        }
        List<String> buffer;

        setPathToDir(config.get(1));

        for (int i = 4; i < config.size(); i++) {
            if (config.get(i).equals("<Bind>")) {
                buffer = new ArrayList<>();
                for (int j = i; j < config.size(); j++) {
                    buffer.add(config.get(j)); 
                    if (config.get(j).equals("</Bind>")) {
                        i += (j - i);
                        setBinds(buffer);
                        break;
                    }
                }
            }
        }

    }

    private void setPathToDir(String dirPath) {
        String b = dirPath.replaceAll("<.[A-z]+>", "");
        user.setPathToDir(b);
    }

    private void setBinds(List<String> buffer) {
        String name = "";
        String value = "";
        Bind parent = new Bind();
        for (int i = 0; i < buffer.size(); i++) {
            if (buffer.get(i).equals("<Bind>")) {
                i++;
                name = buffer.get(i).replaceAll("<.[A-z]+>", "");
                i++;
                value = buffer.get(i).replaceAll("<.[A-z]+>", "");
                parent = new Bind(name, value);
                user.addBind(parent);
                continue;
            }
            
            if (buffer.get(i).equals("<ChildrenBind>")) {
                i++;
                name = buffer.get(i).replaceAll("<.[A-z]+>", "");
                i++;
                value = buffer.get(i).replaceAll("<.[A-z]+>", "");
                user.addChildBind(parent.getName(), new Bind(name, value));
                continue;
            }
        }
    }
}
