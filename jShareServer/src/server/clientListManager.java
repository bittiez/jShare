package server;

import server.Threads.threadServer;

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

    public String onlineUserList(){
        StringBuilder OL = new StringBuilder();
        for (int i = 0; i < clientList.size(); i++) {
            String temail = clientList.get(i).email;
            if(temail != null && temail != "Unknown" && !temail.isEmpty())
                OL.append(temail);
                OL.append("#");
        }
        return OL.toString();
    }

}
