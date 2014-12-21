package server.Threads.Sessions;

import server.Threads.sendTorrent.sendUpdateClient;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by tad on 12/21/2014.
 */
public class SessionListener {
    public Map<String, String> sessions;

    public SessionListener(int port){
        sessions = new HashMap<String, String>();
        ServerSocket serverSocket = null;
        port += 2;
        String email ="DummyEmailForNow";

        boolean listeningSocket = true;
        try {
            serverSocket = new ServerSocket(port);
        } catch (IOException e) {
            System.err.println("Could not listen on port: " + serverSocket.getLocalPort());
            listeningSocket = false;
        } finally {
            System.out.println("Session server listening on: " + serverSocket.getInetAddress().getHostAddress() + ":" + serverSocket.getLocalPort());
        }

        while(listeningSocket){
            Socket clientSocket = null;
            try {
                clientSocket = serverSocket.accept();
            } catch (IOException e) {
                e.printStackTrace();
            }
            String sessionID = SessionGenerator.getSession(this, email);
            if(sessionID != null){
                sessions.put(sessionID, email);
                //Send session
            } else {
                //Send failed
            }

        }
        try {
            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
