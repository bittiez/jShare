package server.Threads;

import server.Helper.fileHandler;
import server.mainServer;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by tad on 11/25/2014.
 */
public class configReloader implements Runnable {
    mainServer ms;
    public configReloader(mainServer _ms){
        ms = _ms;
    }

    @Override
    public void run() {
        while(true){
        try {
            int Seconds = 120;
            Thread.sleep(Seconds*1000);
            String settings = fileHandler.readFile("config.jChat");
            ArrayList<String> config = new ArrayList<String>();
            if(!settings.isEmpty()){
                Matcher m = Pattern.compile("\\{([^}]+)\\}").matcher(settings);
                while(m.find()) {
                    config.add(m.group(1));
                }

            } else {
                config.add("25984");
                config.add(ms.clientVersion + "");
                fileHandler.saveFile("config.jChat", "{25984}{"+ms.clientVersion+"}");
            }
            ms.clientVersion = Double.parseDouble(config.get(1));
            System.out.println("Config reloaded. {CV"+ms.clientVersion+"}");


        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    }
}
