package client.Helpers;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Created by tad on 11/4/14.
 */
public class fileHandler {
    public static void saveFile(String fileName, String fileData) {
        String filePath = fileName;
        //while(filePath.indexOf("/") == 0 || filePath.indexOf("\\") == 0)
        filePath = Paths.get(filePath).toAbsolutePath().toString();
        System.out.println(filePath);
        Writer writer = null;

        try {
            if(!Files.exists(Paths.get(filePath))) {
                File ff = new File(filePath);
                //ff.mkdirs();
                ff.createNewFile();
            }


            writer = new FileWriter(filePath);
            writer.write(fileData);

        } catch (IOException e) {

            System.err.println("Error writing the file : ");
            e.printStackTrace();

        } finally {

            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {

                    System.err.println("Error closing the file : ");
                    e.printStackTrace();
                }
            }

        }
    }
}
