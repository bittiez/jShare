package client.Threads;

import client.Helpers.socketConnection;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.InputStream;

/**
 * Created by tad on 11/11/14.
 */
public class torrentDownloader implements Runnable {
    private socketConnection _sock = null;
    public torrentDownloader(socketConnection sock){
        _sock = sock;
    }

    @Override
    public void run() {
        fetchFile();
    }

    public void fetchFile(){
        try {
            byte[] myByteArray = new byte[1024];
            InputStream is = _sock.clientSocket.getInputStream();
            FileOutputStream fos = new FileOutputStream("jShare.jar.torrent");
            BufferedOutputStream bos = new BufferedOutputStream(fos);
            int bytesRead = is.read(myByteArray, 0, myByteArray.length);
            bos.write(myByteArray, 0, bytesRead);
            bos.close();
            System.out.println("Downloaded torrent file.");
        }catch (Exception e){
            System.err.println(e.getMessage());
    }
    }
}
