package maman18;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

/**
 * Main class.
 */
public class Maman18 {


    public static void main(String[] args) throws IOException {
        Ui ui = new Ui();
        ui.run("test1.txt");
        ui.run("test2.txt");
    }
}
