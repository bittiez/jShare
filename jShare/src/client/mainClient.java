package client;

import client.Dialogs.couldNotConnect;
import client.Dialogs.login;
import client.Guis.connectionUI;
import client.Guis.gui;

import java.io.*;
import java.net.Socket;

/**
 * Created by tad on 10/26/14.
 */
public class mainClient {
    public static void main(String args[]) throws Exception {
        boolean running = true;
        Socket clientSocket = null;
        DataOutputStream out = null;
        BufferedReader in = null;

        new connectionUI();

        try {
            clientSocket = new Socket("127.0.0.1", 2343);
            out = new DataOutputStream(clientSocket.getOutputStream());
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

        } catch (Exception err){
            running = false;
            new couldNotConnect();

        }

        if(running) {
            gui mainGui = new gui(clientSocket, out, in);
            inputReader inReader = new inputReader(mainGui, in);
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
