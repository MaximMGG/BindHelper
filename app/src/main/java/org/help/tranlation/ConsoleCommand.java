package org.help.tranlation;

public class ConsoleCommand {



    public void run(String word) {
        askGoogleTrans(word);

    }
    //TODO (maxim) neet to add functionality for translation more then one word
    public void askGoogleTrans(String word) {
        String[] command = new String[2];
        command[0] = "chrome.exe";
        String com2 = "https://translate.google.com/?sl=en&tl=ru&text=" + word + "%0A&op=translate";
        command[1] = com2;

        try {
            Runtime.getRuntime().exec(command);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void execute(String value) {
        String[] command = new String[2];
        command[0] = "chrome.exe";
        command[1] = value;

        try {
            Runtime.getRuntime().exec(command);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Can't execute yuor bind");
        }
    }

}

