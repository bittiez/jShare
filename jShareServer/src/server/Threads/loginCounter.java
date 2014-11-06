package server.Threads;

import java.util.ArrayList;

/**
 * Created by tad on 11/6/14.
 */
public class loginCounter extends Thread {
    public ArrayList<login> users = null;
    public ArrayList<String> email = null;
    public loginCounter() {
        users = new ArrayList<login>();
        email = new ArrayList<String>();
    }

    public void recordLogin(String userEmail){
        int i = email.indexOf(userEmail);
        if(i != -1){
            users.get(i).Logins++;
        } else {
            users.add(new login(userEmail));
        }
    }
    public void recordMessage(String userEmail){
        int i = email.indexOf(userEmail);
        if(i != -1){
            users.get(i).Messages++;
        } else {
            users.add(new login(userEmail));
        }
    }



    private class login{
        public String email = "";
        public int Logins = 0;
        public int Messages = 0;

        public login(String userEmail){
            email = userEmail;
        }
    }
}
