package org.help.tranlation;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

public class App {

    private ConsoleCommand cc = new ConsoleCommand();
    private User user;
    
    public static void main(String[] args) throws IOException {
        App app = new App();
        app.user = User.getInstance();
        new Initializer();
        app.work();
    }

    public void work() {
        Scanner scan = new Scanner(System.in, Charset.forName("cp866"));
        String command;
        System.out.println("Hello, enter help, for looking on existing commands or start work");
        while(!(command = scan.nextLine()).equals("exit")) {
            String[] commands = command.split(" ");
            switch (commands[0]) {
                case "t" -> {cc.askGoogleTrans(concatinateWord(commands));}
                case "c" -> {createNewFile(commands[1]);}
                case "s" -> {setCurrentFileName(commands[1]);}
                case "a" -> {addWord(concatinateWord(commands));}
                case "dw" -> {deleteWordFromCurrentFile(commands[1]);}
                case "b" -> {bindWorker(commands);}
                case "sh" -> {showAllWordsFromCurrentFile();}
                case "shd" -> {showAllDictionaries();}
                case "dir" -> {user.setPathToDir(commands[1]);}
                case "help" -> {showOptions();}
            }
        }
        user.writeConfigEndClose();
        scan.close();
    }

    private void deleteWordFromCurrentFile(String word) {
        try {
            List<String> currentLib = Files.readAllLines(Path.of(user.getPathToDir() + user.getCurrentFile()));
            for(String s : currentLib) {
                String tr[] = s.split(" - ");
                if (tr[0].trim().equals(word)) {
                    currentLib.remove(s);
                    break;
                }
            }
            Files.write(Path.of(user.getPathToDir() + user.getCurrentFile()), currentLib);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Can't read from current file");
        }
    }

    private void bindWorker(String[] commands) {
        if (commands.length <= 1) {
            System.out.println("Command do not exist");
            return;
        }
        try {
            if (commands[1].equals("cp")) {
                Bind bind = new Bind(commands[2], commands[3]);
                user.addBind(bind);
            } else if (commands[1].equals("cc")) {
                Bind bind = new Bind(commands[3], commands[4]);
                user.addChildBind(commands[2], bind);
            } else if (commands[1].equals("dp")) {
                user.removeBind(commands[2]);
            } else if (commands[1].equals("dc")) {
                user.removeBindChild(commands[4], commands[3]);
            } else if (commands[1].equals("show")) {
                user.showAllBinds();
            } else if (commands.length == 2) {
                user.executBind(commands[1]);
            } else if (commands.length > 2) {
                user.executChildBind(commands[1], commands[2]);
            }
        } catch (Exception e) {
            System.out.println("You wrote intorrect command, try agane");
        }
    }

    private void showAllWordsFromCurrentFile() {
        List<String> allWords = new ArrayList<>();
        if (user.getCurrentFile() == null) {
            System.out.println("Current file not define, please set current file");
        } else {
            try {
               allWords = Files.readAllLines(Path.of(user.getPathToDir() + user.getCurrentFile()));
            } catch (IOException e) {
                System.out.println("Can't read from that file, please set another");
                e.printStackTrace();
            }
        }
        int i = 1;
        System.out.println("--------------------------------------");
        for(String s : allWords) {
            System.out.printf("%d. %s\n", i, s);
            i++;
        }
        System.out.println("--------------------------------------");
    }


    private void showOptions() {
        System.out.println("t - translate word (t cat)");
        System.out.println("c - create new file in your directory (c Dictionary2)");
        System.out.println("s - set current name of file very you want to save words (s Dictionary1)");
        System.out.println("a - add word in your file (a cat - your translations)");
        System.out.println("sh - show all words from current file");
        System.out.println("shd - show all dictionaries from dir");
        System.out.println("b - bind menu");
        System.out.println("\tb cp - create parent bind(b cp parent_name value)");
        System.out.println("\tb cc - create child bind(b cp parent_name child_name value)");
        System.out.println("\tb dp - delete parent bind(b dp parent_name");
        System.out.println("\tb dc - delete child bind(b dp parent_name child_name");
        System.out.println("dir - set directory for saving words");
    }


    private void setCurrentFileName(String fileName) {
        user.setCurrentFile(fileName);
    }


    public void createNewFile(String fileName) {
        File f = new File(user.getPathToDir() + fileName + ".txt");
        try {
            f.createNewFile();
        } catch (IOException e) {
            System.out.println("Sorry, can't create new file");
        }
    }

    public void addWord(String word) {
        if (user.getCurrentFile() == null) {
            System.out.println("You didn't set file for word saving");
        } else {
            Path path = Path.of(user.getPathToDir() + user.getCurrentFile());
            try {
                Files.writeString(path, (word + "\n"), StandardOpenOption.APPEND);
            } catch (IOException e) {
                System.out.println("Sorry, can't write word in file");
            }
        }
    }

    public String concatinateWord(String[] command) {
        StringBuilder sb = new StringBuilder();
        for (int i = 1; i < command.length; i++) {
           sb.append(command[i] + " ");
        }
        return sb.toString();
    }


    private void showAllDictionaries() {
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(Path.of(user.getPathToDir()))) {
            Iterator<Path> iterator = stream.iterator();
            while(iterator.hasNext()) {
                String file = iterator.next().getFileName().toString();
                System.out.println(file.replaceAll(".txt", ""));
            }
        } catch (IOException e) {
            System.out.println("Sorry, can't read fro this dir any files");
            e.printStackTrace();
        }
    }

}
