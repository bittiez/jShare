package client.Threads;

import client.Helpers.config;
import client.Helpers.socketConnection;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Created by tad on 11/11/14.
 */
public class updateDownloader implements Runnable {
    private socketConnection _sock = null;
    private config Config = null;
    public updateDownloader(socketConnection sock, config _Config){
        _sock = sock;
        Config = _Config;
    }

    @Override
    public void run() {
        fetchFile();
    }

    public void fetchFile(){
        try {
            Path tpath = Paths.get(config.UpdateFile);
            if(Files.exists(tpath)){
                Files.deleteIfExists(tpath);
            }


            byte[] myByteArray = new byte[1024];
            InputStream is = _sock.clientSocket.getInputStream();
            FileOutputStream fos = new FileOutputStream(config.UpdateFile);
            BufferedOutputStream bos = new BufferedOutputStream(fos);
            int bytesRead;
            bytesRead = is.read(myByteArray);

            while(bytesRead != -1){
                bos.write(myByteArray, 0, bytesRead);
                myByteArray = new byte[1024];
                bytesRead = is.read(myByteArray);
            }
            bos.close();
            System.out.println("Downloaded update file.");
            Config.updateAvailable = true;
        }catch (Exception e){
            //System.err.println(e.toString());
            e.printStackTrace();
        }
    }

}
