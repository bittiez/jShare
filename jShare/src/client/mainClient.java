package client;

import client.Dialogs.couldNotConnect;
import client.Dialogs.login;
import client.Guis.UI2;
import client.Guis.connectionUI;
import client.Guis.gui;

import java.io.*;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;

/**
 * Created by tad on 10/26/14.
 */
public class mainClient {
    public static void main(String args[]) throws Exception {
        boolean running = true;
        Socket clientSocket = null;
        DataOutputStream out = null;
        BufferedReader in = null;
        String[] connectionInfo = new String[2];
        connectionUI cui = new connectionUI();

        boolean connectionWaitin = true;
        while(connectionWaitin){
            Thread.sleep(1000);
            //log("Checking response: " + cui.connection[0]);
            if(!cui.frame.isVisible() || (cui.connection[0] != "" && cui.connection[0] != null)){
                connectionInfo = cui.connection;
                connectionWaitin = false;
                break;
            }
        }
        log("Trying to connect..");
        try {
            clientSocket = new Socket();
            InetAddress addr = InetAddress.getByName(connectionInfo[0]);
            SocketAddress sockaddr = new InetSocketAddress(addr, Integer.parseInt(connectionInfo[1]));
            clientSocket.connect(sockaddr, 2000); // 20 seconds time out


            //clientSocket = new Socket(connectionInfo[0], Integer.parseInt(connectionInfo[1]));
            out = new DataOutputStream(clientSocket.getOutputStream());
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        } catch (Exception err){
            running = false;
            new couldNotConnect(err);
        }

        if(running) {
            UI2 ui2 = new UI2(clientSocket, out, in);

            //gui mainGui = new gui(clientSocket, out, in);

            inputReader inReader = new inputReader(ui2, in);
            Thread t = new Thread(inReader);
            t.start();
            login LoginForm = new login();
            LoginForm.pack();
            LoginForm.setVisible(true);
            boolean waiting = true;
            while (waiting) {
                if (!LoginForm.isVisible()) {
                    out.writeBytes("{COMMAND}::{100}::{" + LoginForm.email + "}\n");
                    out.flush();
                    waiting = false;
                }
            }
        }

    }

    public static void log(String data){
        System.out.println(data);
    }
}
