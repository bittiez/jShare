package server.Threads.sendTorrent;

import server.Threads.threadServer;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by tad on 11/11/14.
 */
public class sendTorrentThread implements Runnable {
    int _port = 0;
    public sendTorrentThread(int port){
        _port = port +1;
    }

    @Override
    public void run() {
        ServerSocket serverSocket = null;

        boolean listeningSocket = true;
        try {
            serverSocket = new ServerSocket(_port);
        } catch (IOException e) {
            System.err.println("Could not listen on port: " + serverSocket.getLocalPort());
            listeningSocket = false;
        } finally {
            System.out.println("Torrent sending server listening on: " + serverSocket.getInetAddress().getHostAddress() + ":" + serverSocket.getLocalPort());
        }

        while(listeningSocket){
            Socket clientSocket = null;
            try {
                clientSocket = serverSocket.accept();
            } catch (IOException e) {
                e.printStackTrace();
            }

            sendTorrentClient mini = new sendTorrentClient(clientSocket);
            mini.start();
        }
        try {
            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
