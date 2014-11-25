package client;

import client.Dialogs.couldNotConnect;
import client.Guis.UI2;
import client.Guis.connectionUI;
import client.Helpers.config;
import client.Helpers.socketConnection;
import client.Helpers.userLogin;
import client.Threads.pingManager;
import client.Threads.updateDownloader;

import java.net.InetAddress;

/**
 * Created by tad on 10/26/14.
 */
public class mainClient {
    public static double Version =  2.1;


    public static void main(String args[]) throws Exception {

        boolean running = true;
        double serverVersion;

        config Config = new config();
        connectionUI cui = new connectionUI(Config);

        while(true){
            Thread.sleep(1000);
            if(!cui.frame.isVisible() || (cui.connection[0] != "" && cui.connection[0] != null)){
                break;
            }
        }
        cui.connection[0] = InetAddress.getByName(cui.connection[0]).getHostAddress();
        socketConnection con = new socketConnection(cui.connection[0], cui.connection[1]);

        if(!con.openConnection()) {
            running = false;
            new  couldNotConnect();
        }

        if(running) {
            serverVersion = Double.parseDouble(con.in.readLine());

            UI2 ui2 = new UI2(Config, con.out);
            if(serverVersion > Version) {
                socketConnection update_socket = new socketConnection(cui.connection[0], "" + (Integer.parseInt(cui.connection[1]) + 1));
                if (update_socket.openConnection()) {
                    new Thread(new updateDownloader(update_socket, Config)).start();
                }
            }

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
