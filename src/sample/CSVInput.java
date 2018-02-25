package sample;

import java.io.*;
import java.util.Scanner;

public class CSVInput {

    public static String readFromFile(String fileName) {
        File file = new File(fileName);
        String fileString = "";
        Scanner sc = null;
        try {
            sc = new Scanner(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        while (sc.hasNextLine())
            fileString += sc.nextLine() + "\n";

        return fileString;
    }
}
