package client;

import client.Dialogs.couldNotConnect;
import client.Guis.UI2;
import client.Guis.connectionUI;
import client.Helpers.config;
import client.Helpers.socketConnection;
import client.Helpers.userLogin;
import client.Threads.pingManager;
import client.Threads.torrentDownloader;

import javax.swing.*;
import java.net.InetAddress;

/**
 * Created by tad on 10/26/14.
 */
public class mainClient {
    public static double Version =  0.8;

    public static void main(String args[]) throws Exception {

        boolean running = true;

        config Config = new config();
        connectionUI cui = new connectionUI(Config);

        while(true){
            Thread.sleep(1000);
            if(!cui.frame.isVisible() || (cui.connection[0] != "" && cui.connection[0] != null)){
                break;
            }
        }
        cui.connection[0] = InetAddress.getByName(cui.connection[0]).getHostAddress();
        System.out.println(cui.connection[0]);
        socketConnection con = new socketConnection(cui.connection[0], cui.connection[1]);

        if(!con.openConnection()) {
            running = false;
            new  couldNotConnect();
        }

        if(running) {
            UI2 ui2 = new UI2(Config, con.out);

            new Thread(new torrentDownloader(con)).start();

            inputReader inReader = new inputReader(ui2, con.in);
            new Thread(inReader).start();

            userLogin ul = new userLogin(con, cui.userSettings[0], "--");
            ui2.email = cui.userSettings[0];

            pingManager pm = new pingManager(con);
            new Thread(pm).start();

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
