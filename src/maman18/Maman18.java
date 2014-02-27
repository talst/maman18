package maman18;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;
import maman18.obj.Person;
import maman18.Library;
import maman18.obj.Book;

public class Maman18 {

public static boolean isNumeric(String str)  
{  
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
    
public static void printPersons(Person[] membersArr) {
    if (membersArr != null) {
        int i = 0;
        while (membersArr[i] != null) {
            System.out.println(membersArr[i].getId() +" "+ membersArr[i].getLastName() +"\n");
            i++;
            }
    } else { System.out.println("There are no members in the library \n"); }
}

public static void printBooks(Book[] bookArr) {
    if (bookArr != null) {
        int i = 0;
        while (bookArr[i] != null) {
            System.out.println(bookArr[i].getId() +"\n");
            i++;
        }
    } else { System.out.println("incorrect input please try again \n"); }
}                 

    private static void welcome() {
        System.out.println("Data Structure, Winter 2014 \n \n"
                + "MAMAN-18 LIBRARY \n"
                + "Please enter your commands/queries: \n");
    }
       
    
private static void run() throws FileNotFoundException {
        
        Library library1 = new Library();
        int result;
        String input;
        File testFile = new File("test1.txt");
        Scanner in = new Scanner(testFile);
        
        do {
        System.out.println("Please enter your command/query (or q to quit): \n");
        //get user input.
        input=in.next();
        
         /**
          * find out which command/query and execute.
          */
            // add/remove member
        if ((input.equals("+")) || (input.equals("-"))) {
            String tmpFamilyName=in.next();
            Integer tmpId=in.nextInt();
            if (input.equals("+")) {
                Person tmpPerson = new Person(tmpId, tmpFamilyName);
                result = library1.addMember(tmpPerson);
                if (result > 0){
                    System.out.println("The member " + tmpFamilyName +" "+ tmpId + " added successfully. \n");
                } else { System.out.println("incorrect input please try again \n"); }
            } else {
                result = library1.removeMember(tmpId);
                if (result > 0){
                   System.out.println("The member " + tmpFamilyName +" "+ tmpId + " removed successfully. \n");
                } else { System.out.println("incorrect input please try again \n"); }
            }
            
            // queries
        } else if (input.equals("?")) {
            String tmpInput=in.next();
                // Get all members with most books
            if (tmpInput.equals("!")) {
                Person[] membersArr;
                membersArr = library1.getMembersWithMostBooks();
                printPersons(membersArr);
                // Get owned books of a specific member
            } else if (isNumeric(tmpInput)) {
                Book[] bookArr;
                bookArr = library1.getOwnedBooksForMember(Integer.parseInt(tmpInput));
                printBooks(bookArr);
                // Get the owner of a specific book
            } else {
                Person per;
                per = library1.getBookOwner(tmpInput);
                if (per != null) {
                    System.out.println("The owner is: " + per.getLastName() +" "+ per.getId() +"\n");
                } else { System.out.println("incorrect input please try again \n"); }
            }
            
            // add/remove book from/to member
        } else {
            Integer tmpId=in.nextInt();    
            String tmpBookID=in.next(" ");
            String tmpPM=in.next(" ");
            if (tmpPM.equals("+")) {
                result = library1.addBook(tmpId, tmpBookID);
                if (result > 0){
                    System.out.println("The book " + tmpBookID +" added successfully to "+ tmpId +".\n");
                } else { System.out.println("incorrect input please try again \n"); }
            } else {
                result = library1.removeBook(tmpId, tmpBookID);
                if (result > 0){
                    System.out.println("The book " + tmpBookID +" removed successfully from "+ tmpId +".\n");
                } else { System.out.println("incorrect input please try again \n"); }
            }

        }
    } while (!input.equals("q"));} 

    private static void exit() throws IOException {
        System.out.println("Press enter to exit.");
        System.in.read();
    }

    public static void main(String[] args) throws IOException {
        welcome();
        run();
        exit();

    }
}
