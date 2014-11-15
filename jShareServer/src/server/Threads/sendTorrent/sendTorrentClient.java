package server.Threads.sendTorrent;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
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
        sendFile();
    }


    public void sendFile(){
        try {
            System.out.println("Sending torrent file to " + _sock.getInetAddress().getHostAddress());
            File myFile = new File("jShare.jar.torrent");
            byte[] myByteArray = new byte[(int) myFile.length()];
            BufferedInputStream bis = new BufferedInputStream(new FileInputStream(myFile));
            bis.read(myByteArray, 0, myByteArray.length);
            OutputStream os = _sock.getOutputStream();
            os.write(myByteArray, 0, myByteArray.length);
            os.flush();
            _sock.close();
            System.out.println("Send torrent file to " + _sock.getInetAddress().getHostAddress());
        } catch (Exception e){
            System.err.println(e.getMessage());
        }
    }
}
