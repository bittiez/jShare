package server;

import java.util.ArrayList;

/**
 * Created by tad on 10/27/14.
 */
public class clientListManager {
    public ArrayList<threadServer> clientList;
    public clientListManager() {
        clientList = new ArrayList<threadServer>();
    }
    public void add(threadServer serv){
        clientList.add(serv);
    }
    public void remove(threadServer serv){
        try {
            clientList.remove(serv);
        } catch(Exception e){

        }
    }

}
