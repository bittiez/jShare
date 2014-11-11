package server;

import server.Helper.fileHandler;
import server.Threads.loginCounter;
import server.Threads.sendTorrent.sendTorrentThread;
import server.Threads.threadServer;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by tad on 10/26/14.
 */
public class mainServer {
    public static void main(String[] args) throws IOException {



        ArrayList<String> config = new ArrayList<String>();
        
        String settings = fileHandler.readFile("config.jChat");
        if(!settings.isEmpty()){
            Matcher m = Pattern.compile("\\{([^}]+)\\}").matcher(settings);
            while(m.find()) {
                config.add(m.group(1));
            }
        } else {
            config.add("25984");
            fileHandler.saveFile("config.jChat", "{25984}");
        }

        Thread torrent = new Thread(new sendTorrentThread(Integer.parseInt(config.get(0))));
        torrent.start();


        clientListManager clientManager = new clientListManager();
        ServerSocket serverSocket = null;

        boolean listeningSocket = true;
        try {
            serverSocket = new ServerSocket(Integer.parseInt(config.get(0)));
            log("Server listening on: " + serverSocket.getInetAddress().getHostAddress() + ":" + serverSocket.getLocalPort());
        } catch (IOException e) {
            System.err.println("Could not listen on port: " + serverSocket.getLocalPort());
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
