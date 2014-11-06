package client.Helpers;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;

/**
 * Created by tad on 11/5/14.
 */
public class socketConnection {
    public Socket clientSocket = null;
    public DataOutputStream out = null;
    public BufferedReader in = null;
    public String con_ip = "";
    public String con_port = "";

    public socketConnection(String ip, String port) {
        clientSocket = new Socket();
        con_ip = ip;
        con_port = port;
    }

    public boolean openConnection(){
        try {
            InetAddress addr = InetAddress.getByName(con_ip);
            SocketAddress sockaddr = new InetSocketAddress(addr, Integer.parseInt(con_port));
            clientSocket.connect(sockaddr, 2000); // 2 seconds time out
            out = new DataOutputStream(clientSocket.getOutputStream());
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        } catch(Exception e){
            return false;
        }
        return true;
    }
}
