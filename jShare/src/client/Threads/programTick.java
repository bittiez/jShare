package client.Threads;

import client.Design.Notification;
import client.Guis.UI2;
import client.Helpers.config;

import java.awt.*;

/**
 * Created by tad on 11/25/2014.
 */
public class programTick implements Runnable {
    config Config = null;
    UI2 UI = null;
    public programTick(config _config, UI2 ui){
        Config = _config;
        UI = ui;
    }

    @Override
    public void run() {
        while(true){
            try {
                Thread.sleep(60000);
                if(Config.updateAvailable)
                    new Notification("Update available", UI.titleBase + " has an update available, restart the program to update!", 5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
