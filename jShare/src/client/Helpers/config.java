package client.Helpers;

import client.Design.themeNames;
import com.sun.org.apache.xpath.internal.operations.Bool;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by tad on 11/9/14.
 */
public class config {
    //Addresses and ports not set up yet

    public String address = null;
    public String[] addresses = null;

    public String port = null;
    public String[] ports = null;

    public String userEmail = "";

    public themeNames theme = themeNames.DarkTheme;

    public config(){
        String settings = fileHandler.readFile("config.jShare");
        ArrayList<String> config = new ArrayList<String>();
        if(!settings.isEmpty()){
            Matcher m = Pattern.compile("\\{([^}]+)\\}").matcher(settings);
            while(m.find()) {
                config.add(m.group(1));
            }
            if(config.get(0).contains("\\|"))
                addresses = config.get(0).split("\\|");
            if(config.get(1).contains("\\|"))
                ports = config.get(1).split("\\|");


            address = (addresses == null ? config.get(0) : addresses[0]);
            port = (ports == null ? config.get(1) : ports[0]);
            if(config.size() > 2)
                userEmail = (config.get(2));

            if(config.size() > 3){
                theme = themeNames.valueOf(config.get(3));
            }
        }

    }

    public Boolean Save() {
        StringBuilder sb = new StringBuilder();

        sb.append(addPar(address));
        sb.append(addPar(port));
        sb.append(addPar(userEmail));
        sb.append(addPar(theme.name()));


        fileHandler.saveFile("config.jShare", sb.toString());
        return false;
    }
    private String addPar(String d){
        return "{" + d + "}";
    }
}
