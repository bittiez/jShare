package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by tad on 10/26/14.
 */
public class mainServer {
    public static void main(String[] args) throws IOException {
        clientListManager clientManager = new clientListManager();
        ServerSocket serverSocket = null;

        boolean listeningSocket = true;
        try {
            serverSocket = new ServerSocket(2343);
            log("Server listening on: " + serverSocket.getInetAddress().getHostAddress() + ":" + serverSocket.getLocalPort());
        } catch (IOException e) {
            System.err.println("Could not listen on port: 2343");
            listeningSocket = false;
        }

        while(listeningSocket){
            Socket clientSocket = serverSocket.accept();
            threadServer mini = new threadServer(clientSocket, clientManager);
            mini.start();
            clientManager.add(mini);
            //sendToAll(clientManager, "Someone connected to the server..");
        }
        serverSocket.close();


    }

    public static void sendToAll(threadServer ts, clientListManager cm, String data){
        for (int i = 0; i < cm.clientList.size(); i++) {
            try {
                cm.clientList.get(i).sendData(ts.email + ": " +data);}
            catch (Exception e){
                cm.clientList.get(i).close();
                cm.clientList.remove(i);}
        }
    }

    public static void log(String data){
        System.out.println(data);
    }
}
