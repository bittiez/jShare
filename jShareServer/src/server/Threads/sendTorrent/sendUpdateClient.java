package server.Threads.sendTorrent;

import server.mainServer;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.net.Socket;

/**
 * Created by tad on 11/11/14.
 */
public class sendUpdateClient extends Thread{
    public Socket _sock = null;
    public sendUpdateClient(Socket sock){
        _sock = sock;
    }
    public void run() {
        sendFile();
    }


    public void sendFile(){
        try {
            System.out.println("Sending update file to " + _sock.getInetAddress().getHostAddress());
            File myFile = new File(mainServer.UpdateFile);
            byte[] myByteArray = new byte[(int) myFile.length()];
            BufferedInputStream bis = new BufferedInputStream(new FileInputStream(myFile));
            int br;
            OutputStream os = _sock.getOutputStream();
            while((br = bis.read(myByteArray)) != -1){
                os.write(myByteArray, 0, br);
            }
            os.close();
            _sock.close();
            System.out.println("Sent update file to " + _sock.getInetAddress().getHostAddress());
        } catch (Exception e){
            System.err.println(e.getMessage());
        }
    }
}
