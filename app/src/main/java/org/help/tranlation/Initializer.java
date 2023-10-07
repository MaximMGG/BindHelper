package org.help.tranlation;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class Initializer extends Thread {

    private final String pathToConf = "resources/config.txt";
    private User user;

    public Initializer() {
        user = User.getInstance();
        start();
    }

    @Override
    public void run() {
        checkOrCreateConfig();



        App.setConfigReady();
    }

    public void checkOrCreateConfig() {
        if (!(Files.exists(Path.of(pathToConf)))) {
            System.out.println("Please set path to directory enter: dir \"full_path_to_dir\"");
            addStructureInConfig();
            try {
                Files.createFile(Path.of(pathToConf));
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
        structure.add("<PathToDir>\n");
        structure.add("<Path>p</Path>\n");
        structure.add("</PathToDir>\n");
        structure.add("\n");
        structure.add("<Binds>\n");
        structure.add("</Binds>\n");

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
                        i = j - i;
                        setBinds(buffer);
                    }
                }
            }
        }

    }

    private void setPathToDir(String dirPath) {
        String b = dirPath.replaceAll("<.+>", "");
        user.setPathToDir(b);
    }

    private void setBinds(List<String> buffer) {

    }
    


}
