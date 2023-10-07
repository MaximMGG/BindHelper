package org.help.tranlation;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Initializer extends Thread {

    private final String pathToConf = "resources/config.txt";

    public Initializer() {
        start();
    }

    @Override
    public void run() {
        checkOrCreateConfig();



        App.setConfigReady();
    }

    public void checkOrCreateConfig() {
        if (!(Files.exists(Path.of(pathToConf)))) {
            try {
                Files.createFile(Path.of(pathToConf));
            } catch (IOException e) {
                e.printStackTrace();
                throw new RuntimeException("Can't create config");
            }
        }
    }
}
