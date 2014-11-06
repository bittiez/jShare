package server.Helper;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;

/**
 * Created by tad on 11/4/14.
 */
public class fileHandler {
    public static boolean saveFile(String fileName, String fileData) {
        String filePath = fileName;
        filePath = Paths.get(filePath).toAbsolutePath().toString();
        System.out.println(filePath);
        Writer writer = null;

        try {
            if(!Files.exists(Paths.get(filePath))) {
                File ff = new File(filePath);
                ff.createNewFile();
            }


            writer = new FileWriter(filePath);
            writer.write(fileData);

        } catch (IOException e) {
            System.err.println("Error writing the file : ");
            e.printStackTrace();
            return false;
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                    return true;
                } catch (IOException e) {

                    System.err.println("Error closing the file : ");
                    e.printStackTrace();
                    return false;
                }
            }
            return true;
        }
    }
    public static String readFile(String fileName){
        Scanner scanner = null;
        StringBuilder fileContents = new StringBuilder();
        try {
            File file = new File(Paths.get(fileName).toAbsolutePath().toString());
            if(!file.exists())
                return "";
            scanner = new Scanner(new BufferedReader(new FileReader(file)));
            String lineSeparator = System.getProperty("line.separator");
            if (scanner.hasNextLine()) {
                fileContents.append(scanner.nextLine());
            }
            while (scanner.hasNextLine()) {
                fileContents.append(lineSeparator + scanner.nextLine());
            }
        }
        catch(Exception e){
            System.err.println(e.getMessage());
            return "";
        }
        finally {
            if(scanner != null)
                scanner.close();
            return fileContents.toString();
        }
    }
}
