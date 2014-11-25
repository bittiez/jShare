package server;

import server.Helper.fileHandler;
import server.Threads.configReloader;
import server.Threads.sendTorrent.sendUpdateListener;
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
    public static String UpdateFile = "Update.zip";
    public static void main(String[] args) throws IOException {
        new mainServer();
    }
    public double clientVersion = 1.8;
    public mainServer() throws IOException {



        ArrayList<String> config = new ArrayList<String>();

        String settings = fileHandler.readFile("config.jChat");
        if(!settings.isEmpty()){
            Matcher m = Pattern.compile("\\{([^}]+)\\}").matcher(settings);
            while(m.find()) {
                config.add(m.group(1));
            }
        } else {
            config.add("25984");
            config.add(clientVersion+"");
        }
        if(config.size() > 1)
            clientVersion = Double.parseDouble(config.get(1));
        else{
            config.add(clientVersion+"");
        }
        fileHandler.saveFile("config.jChat", "{"+config.get(0)+"}{"+config.get(1)+"}");

        Thread FileServer = new Thread(new sendUpdateListener(Integer.parseInt(config.get(0))));
        FileServer.start();


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

        configReloader CI = new configReloader(this);
        new Thread(CI).start();

        while(listeningSocket){
            Socket clientSocket = serverSocket.accept();
            threadServer mini = new threadServer(clientSocket, clientManager, clientVersion);
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
