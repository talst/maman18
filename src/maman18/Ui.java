package maman18;

import java.io.*;

/**
 * Created by Tal on 28/02/14.
 */
public class Ui implements Runnable {
    private Library library = new Library();
    private BufferedReader reader;
    private BufferedWriter writer;

    @Override
    public void run() {
        welcome();

    }

    private void welcome() {
        System.out.println("Data Structure, Winter 2014 \n \n"
                + "MAMAN-18 LIBRARY \n"
                + "Please enter your commands/queries: \n");
    }

    private BufferedReader prepareReader() throws FileNotFoundException {
        return new BufferedReader(new FileReader("input.txt"));
    }

    private BufferedWriter preaperWriter() throws IOException {
        File file = new File("output.txt");

        if (!file.exists()) {
            file.createNewFile();
        }

        FileWriter fw = new FileWriter(file.getAbsoluteFile());
        return new BufferedWriter(fw);
    }

    private String[] splitLine(String line) {
        String[] words;
        words = line.split(" ");
        return words;
    }

    private int checkWordType(String word) {
        if (Character.isDigit(word.charAt(0))) {
            return 1;
        }
        if (Character.isLetter(word.charAt(0)) && Character.isDigit(2)) {
            return 2;
        }
        if (Character.isLetter(word.charAt(0)) && Character.isLetter(2)) {
            return 3;
        }
        if (word.equals("+")) {
            return 4;
        }
        if (word.equals("-")) {
            return 5;
        }
        if (word.charAt(0) == '?') {
            return 6;
        }
        return -1;
    }

    private void addMember(String[] command, BufferedWriter writer) throws IOException {
        int result = library.addMember(Integer.parseInt(command[2]), command[1]);
        if (result == 1) {
            writer.write("User: " + command[1] + " ID: " + command[2] + " was added.");
            writer.newLine();
        }
        if (result == -1) {
            writer.write("User: " + command[1] + " ID: " + command[2] + " already exists.");
            writer.newLine();
        }
    }

    private void removeMember(String[] command, BufferedWriter writer) throws IOException {
        int result = library.removeMember(Integer.parseInt(command[2]));
        if (result == 1) {
            writer.write("User: " + command[1] + " ID: " + command[2] + " was deleted.");
            writer.newLine();
        }
        if (result == -1) {
            writer.write("User: " + command[1] + " ID: " + command[2] + " did not exist.");
            writer.newLine();
        }
    }

    private void book(String[] command, BufferedWriter writer) throws IOException {
        if (checkWordType(command[3]) == 4) {
            int result = library.addBook(Integer.parseInt(command[1]), command[2]);
            if (result == 1) {
                writer.write("User: " + command[0] + " ID: " + command[1] + " loaned book: " + command[2]);
                writer.newLine();
            }
            //TODO finish the book handle method.
        }

    }

}
