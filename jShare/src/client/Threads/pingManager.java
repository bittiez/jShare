package client.Threads;

import client.Helpers.socketConnection;

/**
 * Created by tad on 11/7/14.
 */
public class pingManager implements Runnable{
    socketConnection socket = null;
    public pingManager(socketConnection _socket) {
        socket = _socket;
    }

    @Override
    public void run() {
        while (true){
            try {
                Thread.sleep(30000);
                socket.out.writeBytes("{COMMAND}::{101}::{PING}\n");
                socket.out.flush();
            } catch(Exception e){
                break;
            }
        }
    }
}
