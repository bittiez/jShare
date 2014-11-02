package client;

import client.Guis.gui;

import java.io.BufferedReader;
import java.io.IOException;

/**
 * Created by tad on 10/27/14.
 */
public class inputReader implements Runnable {
    private gui mainGui;
    private BufferedReader in;
    public Boolean running = true;
    public inputReader(gui Gui, BufferedReader input){
        this.mainGui = Gui;
        this.in = input;
    }

    @Override
    public void run() {
        String data = "";
        while(running){
            try {
                data = in.readLine();

            } catch (IOException e) {
                e.printStackTrace();
                running = false;
                break;
            }
            mainGui.recData(data);
            //mainClient.log(data);
        }
    }

}
