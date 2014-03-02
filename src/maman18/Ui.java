package maman18;

import java.io.*;


/**
 *
 */
public class Ui {
    private Library library;
    private BufferedReader reader;
    private BufferedWriter writer;

    public Ui() throws IOException {
        library = new Library();

    }

    /**
     * @param fileName
     * @throws IOException
     */
    public void run(String fileName) throws IOException {
        String[] command;
        String line;
        welcome();
        writer = prepareWriter();
        reader = prepareReader(fileName);
        line = reader.readLine();
        while (line != null) {
            command = splitLine(line);
            readCommand(command);
            line = reader.readLine();
        }
        writer.close();
    }

    /**
     *
     */
    private void welcome() {
        System.out.println("Data Structure, Winter 2014 \n \n"
                + "MAMAN-18 LIBRARY \n"
                + "Please enter your commands/queries: \n");
    }

    /**
     * @param fileName
     * @return
     * @throws FileNotFoundException
     */
    private BufferedReader prepareReader(String fileName) throws FileNotFoundException {
        return new BufferedReader(new FileReader(fileName));
    }

    /**
     * @return
     * @throws IOException
     */
    private BufferedWriter prepareWriter() throws IOException {
        File file = new File("output.txt");

        if (!file.exists()) {
            file.createNewFile();
        }

        FileWriter fw = new FileWriter(file.getAbsoluteFile(), true);
        return new BufferedWriter(fw);
    }

    /**
     * @param line
     * @return
     */
    private String[] splitLine(String line) {
        String[] words;
        words = line.split(" ");
        return words;
    }

    /**
     * @param word
     * @return
     */
    private int checkWordType(String word) {
        if (Character.isDigit(word.charAt(0))) {
            return 1;
        }
        if (Character.isLetter(word.charAt(0)) && Character.isDigit(word.charAt(2))) {
            return 2;
        }
        if (Character.isLetter(word.charAt(0)) && Character.isLetter(word.charAt(2))) {
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
        if (word.charAt(0) == '!') {
            return 7;
        }
        return -1;
    }

    /**
     * @param command
     * @throws IOException
     */
    private void addMemberAction(String[] command) throws IOException {
        int result = this.library.addMember(Integer.parseInt(command[2]), command[1]);
        if (result == 1) {
            this.writer.write("User: " + command[1] + " ID: " + command[2] + " was added.");
            this.writer.newLine();
        }
        if (result == -1) {
            this.writer.write("User: " + command[1] + " ID: " + command[2] + " already exists.");
            this.writer.newLine();
        }
    }

    /**
     * @param command
     * @throws IOException
     */
    private void removeMemberAction(String[] command) throws IOException {
        int result = this.library.removeMember(Integer.parseInt(command[2]));
        if (result == 1) {
            this.writer.write("User: " + command[1] + " ID: " + command[2] + " was deleted.");
            this.writer.newLine();
        }
        if (result == -1) {
            this.writer.write("User: " + command[1] + " ID: " + command[2] + " did not exist.");
            this.writer.newLine();
        }
    }

    /**
     * @param command
     * @throws IOException
     */
    private void bookAction(String[] command) throws IOException {
        if (checkWordType(command[3]) == 4) {
            int result = this.library.addBook(Integer.parseInt(command[1]), command[2]);
            if (result == 1) {
                this.writer.write("User: " + command[0] + " ID: " + command[1] + " loaned book: " + command[2]);
                this.writer.newLine();
            }
            if (result == -1) {
                this.writer.write("Book: " + command[2] + " is already taken");
                this.writer.newLine();
            }
            if (result == -2) {
                this.writer.write("User: " + command[0] + " ID: " + command[1] + " does not exists.");
                this.writer.newLine();
            }
            if (result == -3) {
                this.writer.write("User: " + command[0] + " ID: " + command[1] + " already have 10 books in his possession.");
                this.writer.newLine();
            }
        }
        if (checkWordType(command[3]) == 5) {
            int result = this.library.removeBook(Integer.parseInt(command[1]), command[2]);
            if (result == 1) {
                this.writer.write("User: " + command[0] + " ID: " + command[1] + " returned book: " + command[2]);
                this.writer.newLine();
            }
            if (result == -1){
                this.writer.write("Book: " + command[2] + " does not exists.");
                this.writer.newLine();
            }
            if (result == -2){
                this.writer.write("User: " + command[0] + " ID: " + command[1] + " does not own book: " + command[2]);
                this.writer.newLine();
            }
        }
    }

    /**
     * @param member
     * @throws IOException
     */
    private void memberQuery (String member) throws IOException {
        String[] result = this.library.getOwnedBooksForMember(Integer.parseInt(member));
        if (result == null) {
            this.writer.write("User: " + member + " was not found.");
            this.writer.newLine();
        } else {
            if (result.length == 0) {
                this.writer.write("User: " + member + " has no books.");
                this.writer.newLine();
            }
            if (result.length > 0){
                this.writer.write("User: " + member + " has " + result.length + " books:");
                writeStringArray(result);
                this.writer.newLine();
            }
        }
    }

    /**
     * @param book
     * @throws IOException
     */
    private void bookQuery (String book) throws IOException {
        String result = this.library.getBookOwner(book);
        if (result == null) {
            this.writer.write("Book: " + book + " does not exists.");
            this.writer.newLine();
        } else  {
            this.writer.write("The owner of book: " + book + " is " + result);
            this.writer.newLine();
        }
    }

    /**
     * @throws IOException
     */
    private void amountQuery () throws IOException {
        String[] result = this.library.getMembersWithMostBooks();
        if (result == null) {
            this.writer.write("No users in the system");
            this.writer.newLine();
        } else {
            if (result.length == 0) {
                this.writer.write("All users have 0 books.");
                this.writer.newLine();
            }
            if (result.length > 0) {
                this.writer.write("The users who hold the highest amount of books: ");
                writeStringArray(result);
                this.writer.newLine();
            }

        }
    }

    /**
     * @param command
     * @throws IOException
     */
    private void query(String command) throws IOException {
        if (checkWordType(command) == 1) {
            memberQuery(command);
        }
        if (checkWordType(command) == 2) {
            bookQuery(command);
        }
        if (checkWordType(command) == 7) {
            amountQuery();
        }
    }

    /**
     * @param command
     * @throws IOException
     */
    private void readCommand(String[] command) throws IOException {
        int firstWordType = checkWordType(command[0]);
        if (firstWordType == 4) {
            addMemberAction(command);
        }
        if (firstWordType == 5) {
            removeMemberAction(command);
        }
        if (firstWordType == 3) {
            bookAction(command);
        }
        if (firstWordType == 6) {
            query(command[1]);
        }
    }

    /**
     * @param result
     * @throws IOException
     */
    private void writeStringArray(String[] result) throws IOException {
        for (int i = 0; i<result.length; i++) {
            if (i%6 == 0) {
                this.writer.newLine();
            }
            this.writer.write(result[i] + " ");
        }
    }
}
