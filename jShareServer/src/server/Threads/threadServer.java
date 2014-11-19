package server.Threads;

import server.Helper.commandHandler;
import server.Helper.commandReturn;
import server.clientListManager;
import server.mainServer;

import java.io.*;
import java.net.Socket;

/**
 * Created by tad on 10/26/14.
 */
public class threadServer extends Thread{

    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;
    private clientListManager clientManager;
    boolean connected = true;
    private String address = "";

    public String email = "Unknown";
    public double clientVersion = 0.0;


    public threadServer(Socket socket, clientListManager cm, double _clientVersion) {

        super("MiniServer");
        this.socket = socket;
        this.clientManager = cm;
        clientVersion = _clientVersion;

        address = socket.getInetAddress().getHostAddress();
    }

    public void run(){
        //Read input and process here
        mainServer.log("Client connecting from " + address);
        //DataOutputStream out = null;
        try {
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);

        } catch (Exception e) {
            e.printStackTrace();
            mainServer.log("Failed to get input or output stream from " + address);
            connected = false;
            this.close();
        }
        if(connected) {
            mainServer.log("Client connected. (" + address + ")");
            sendData(clientVersion + "");
        }
        String rec;
        while(connected){
            try {
                rec = in.readLine();
                if(rec == null) {connected = false; break;}
                if(rec.isEmpty()) break;
                commandReturn recieved = commandHandler.findCommand(rec);

                switch(recieved.TYPE){
                    case MESSAGE:
                        mainServer.sendToAll(this, clientManager, recieved.message);
                        break;
                    case LOGIN:
                        this.email = recieved.message;
                        //sendData("Logged in as " + this.email);
                        mainServer.log(this.address + ": Logged in with " + this.email);
                        mainServer.sendToAll(this, clientManager, "Connected to the server");
                        break;
                    case PING:
                        break;
                }
            } catch (IOException e) {
                e.printStackTrace();
                connected = false;
            }

        }
        close();
    }

    public void sendTorrent(){

    }

    public void sendData(String data){
        out.println(data);
        out.flush();
    }
    public void close(){
        try {
            this.socket.close();
            this.connected = false;
            clientManager.remove(this);
            mainServer.log(address + " disconnected.");
            mainServer.sendToAll(this, clientManager, "Disconnected");
            Thread.currentThread().interrupt();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
