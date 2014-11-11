package server.Threads.sendTorrent;

import java.io.*;
import java.net.Socket;

/**
 * Created by tad on 11/11/14.
 */
public class sendTorrentClient extends Thread{
    public Socket _sock = null;
    public sendTorrentClient(Socket sock){
        _sock = sock;
    }
    public void run() {
        FileInputStream fis;
        BufferedInputStream bis = null;
        OutputStream os = null;

        File myFile = new File ("jShare.jar.torrent");
        byte [] mybytearray  = new byte [(int)myFile.length()];
        try {
            fis = new FileInputStream(myFile);
            bis = new BufferedInputStream(fis);
            bis.read(mybytearray,0,mybytearray.length);
            os = _sock.getOutputStream();
            System.out.println("Sending " + myFile.toString() + "(" + mybytearray.length + " bytes)");
            os.write(mybytearray,0,mybytearray.length);
            os.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (bis != null) try {
                bis.close();
                if (os != null) os.close();
                if (_sock!=null) _sock.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        System.out.println("Done.");


    }
}
