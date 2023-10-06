package org.help.tranlation;

public class Initializer extends Thread {


    public Initializer() {
        start();
    }

    @Override
    public void run() {




        App.setConfigReady();
    }
}
