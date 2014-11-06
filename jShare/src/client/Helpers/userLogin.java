package client.Helpers;

/**
 * Created by tad on 11/6/14.
 */
public class userLogin {
    private socketConnection socket = null;
    String userName = "", userPassword = "";
    public userLogin(socketConnection con, String user, String password){
        socket = con;
        userName = user;
        userPassword = password;
    }

    public boolean loginV1(){
        try {
            socket.out.writeBytes("{COMMAND}::{100}::{" + userName + "|" + userPassword + "}\n");
            socket.out.flush();
        } catch(Exception e){
            return false;
        }
        return true;
    }
}
