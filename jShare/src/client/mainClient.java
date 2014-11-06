package client;

import client.Dialogs.couldNotConnect;
import client.Dialogs.login;
import client.Guis.UI2;
import client.Guis.connectionUI;
import client.Guis.gui;
import client.Helpers.socketConnection;
import client.Helpers.userLogin;

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

        connectionUI cui = new connectionUI();

        while(true){
            Thread.sleep(1000);
            if(!cui.frame.isVisible() || (cui.connection[0] != "" && cui.connection[0] != null)){
                break;
            }
        }

        socketConnection con = new socketConnection(cui.connection[0], cui.connection[1]);

        if(!con.openConnection()) {
            running = false;
            new  couldNotConnect();
        }

        if(running) {
            UI2 ui2 = new UI2(con.clientSocket, con.out, con.in);

            inputReader inReader = new inputReader(ui2, con.in);
            new Thread(inReader).start();

            userLogin ul = new userLogin(con, cui.userSettings[0], "--");
            if(!ul.loginV1())
            {
                ui2.frame.setVisible(false);
                ui2.frame.dispose();
                mainClient.main(null);
            }
        }

    }

    public static void log(String data){
        System.out.println(data);
    }
}
