package maman18;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

public class Maman18 {

    public static boolean isNumeric(String str) {
        try
        {
            Integer d = Integer.parseInt(str);
        }
        catch(NumberFormatException nfe)
        {
            return false;
        }
        return true;
    }

    public static void printPersons(String[] membersArr) {
        if (membersArr != null) {
            for (int i=0; i<membersArr.length; i++) {
                System.out.println(membersArr[i] +"\n");
            }
        } else { System.out.println("There are no members in the library \n"); }
    }

    public static void printBooks(String[] bookArr) {
        if (bookArr != null) {
            int i = 0;
            while (bookArr[i] != null) {
                System.out.println(bookArr[i] +"\n");
                i++;
            }
        } else { System.out.println("incorrect input please try again \n"); }
    }

    public static void printAddBook(int result, Integer memId, String bookID) {
        if (result > 0){
            System.out.println("The book added successfully to member "+ memId +".\n");
        } else {
            if (result == -1) { System.out.println("the book " + bookID + " is already taken \n");}
            if (result == -2) { System.out.println("the member " + memId + " doesn`t exist \n");}
            if (result == -3) { System.out.println("the member " + memId + " already have 10 books \n");}
        }
    }

    public static void printRemoveBook(int result, Integer memId, String bookID) {
        if (result > 0){
            System.out.println("The book removed successfully from member "+ memId +".\n");
        } else {
            if (result == -1) { System.out.println("The book " + bookID + " wasn`t taken \n");}
            if (result == -2) { System.out.println("The member " + memId + " doesn`t own this book \n");}
        }
    }

    private static void welcome() {
        System.out.println("Data Structure, Winter 2014 \n \n"
                + "MAMAN-18 LIBRARY \n");
    }


    private static void run(String fileName) throws FileNotFoundException {

        Library library1 = new Library();
        int result;
        String input;
        File testFile = new File(fileName);
        Scanner in = new Scanner(testFile);

        do {
            System.out.println("Please enter your command/query (or q to quit):");
            //get user input.
            input=in.next();
            System.out.print( input + " ");

            /**
             * find out the command/query print it and execute.
             */
            // add/remove member
            if (input.equals("q")) {
                break;
            }
            if ((input.equals("+")) || (input.equals("-"))) {
                String tmpFamilyName=in.next();
                Integer tmpId=in.nextInt();
                System.out.println( tmpFamilyName +" "+ tmpId );
                if (input.equals("+")) {
                    //Person tmpPerson = new Person(tmpId, tmpFamilyName);
                    result = library1.addMember(tmpId, tmpFamilyName);
                    if (result > 0){
                        System.out.println("The member " + tmpFamilyName +" "+ tmpId + " added successfully. \n");
                    } else { System.out.println("The member " + tmpFamilyName +" "+ tmpId + " already exists. \n"); }
                } else {
                    result = library1.removeMember(tmpId);
                    if (result > 0){
                        System.out.println("The member " + tmpFamilyName +" "+ tmpId + " removed successfully. \n");
                    } else { System.out.println("The member " + tmpFamilyName +" "+ tmpId + " doesn`t exist. \n"); }
                }

                // queries
            } else if (input.equals("?")) {
                String tmpInput=in.next();
                System.out.println( tmpInput );
                // Get all members with most books
                if (tmpInput.equals("!")) {
                    String[] membersArr;
                    membersArr = library1.getMembersWithMostBooks();
                    printPersons(membersArr);
                    // Get owned books of a specific member
                } else if (isNumeric(tmpInput)) {
                    String[] bookArr;
                    bookArr = library1.getOwnedBooksForMember(Integer.parseInt(tmpInput));
                    printBooks(bookArr);
                    // Get the owner of a specific book
                } else {
                    String per;
                    per = library1.getBookOwner(tmpInput);
                    if (per != null) {
                        System.out.println("The owner is: " + per +" "+ per +"\n");
                    } else { System.out.println("incorrect input please try again \n"); }
                }

                // add/remove book from/to member
            } else {
                Integer tmpMemId=in.nextInt();
                String tmpBookID=in.next();
                String tmpPM=in.next();
                System.out.println( tmpMemId +" "+ tmpBookID +" "+ tmpPM );
                if (tmpPM.equals("+")) {
                    result = library1.addBook(tmpMemId, tmpBookID);
                    printAddBook(result, tmpMemId, tmpBookID);
                } else {
                    result = library1.removeBook(tmpMemId, tmpBookID);
                    printRemoveBook(result, tmpMemId, tmpBookID);
                }

            }
        } while (!input.equals("q"));}

    private static void exit() throws IOException {
        System.out.println("Press enter to exit.");
        System.in.read();
    }

    public static void main(String[] args) throws IOException {
        welcome();
        run("test1.txt");
        run("test3.txt");
        exit();

    }
}