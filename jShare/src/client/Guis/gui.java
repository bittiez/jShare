package client.Guis;

import client.mainClient;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;

/**
 * Created by tad on 10/26/14.
 */
public class gui {
    private JPanel mainPanel;
    private JTextField Input;
    private JTextArea consoleArea;
    private JButton sendButton;
    private JButton commandButton;
    private JTabbedPane tabbedPane1;
    private JPanel topPanel;
    private JPanel botPanel;
    private JFrame mFrame;
    private Socket connection;
    private DataOutputStream con_out;
    private BufferedReader con_in;
    public boolean running = true;
    private ArrayList<String> chatHistory;
    private int lastChatHistoryRequested = 0;

    public gui(Socket sock, DataOutputStream output, final BufferedReader input) {
        this.connection = sock;
        this.con_out = output;
        this.con_in = input;
        chatHistory = new ArrayList<String>();

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            javax.swing.UIManager.setLookAndFeel("com.sun.java.swing.plaf.gtk.GTKLookAndFee‌​l");
        } catch (Exception e) {
            System.out.println("Unable to set native look and feel: " + e);
        }

        this.mFrame = new JFrame("Client");
        mFrame.add(mainPanel);
        mFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        mFrame.pack();
        mFrame.setSize(550, 400);
        mFrame.setVisible(true);
        Input.requestFocus();
        sendButton.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON1) {
                    sendButtonPress();
               }
            }
        });

        Input.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent keyEvent) {

            }

            @Override
            public void keyPressed(KeyEvent keyEvent) {

            }

            @Override
            public void keyReleased(KeyEvent keyEvent) {
                if(keyEvent.getKeyCode() == KeyEvent.VK_ENTER){
                    sendButtonPress();
                }
                if(keyEvent.getKeyCode() == keyEvent.VK_UP){
                    if(chatHistory.size() > 0){
                        Input.setText(chatHistory.get((chatHistory.size() - lastChatHistoryRequested )-1));
                        lastChatHistoryRequested++;
                    }
                }
            }
        });
        commandButton.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON1) {
                    commandButtonPress();
                }
            }
        });
    }

    public void close(){
        this.mFrame.setVisible(false);
    }
    public void sendData(String data){
        try {
            con_out.writeBytes(data + "\n");
            con_out.flush();
        } catch (IOException e) {
            running = false;
            mainClient.log("Error while trying to send data. Closing connection.");
        }
    }
    public void recData(String data){
        consoleArea.append(data + "\n");
    }
    public void sendButtonPress(){
        String input = Input.getText().trim();
        if(!input.isEmpty()) {
            chatHistory.add(input);
            lastChatHistoryRequested = 0;
            while(chatHistory.size() > 20){
                chatHistory.remove(0);
            }
            input = "{COMMAND}::{1}::"+input;
            sendData(input);
            //consoleArea.append("You: " + input + "\n");
            Input.setText("");
            Input.requestFocus();
        }
    }
    public void commandButtonPress(){
        String input = Input.getText().trim();
        if(!input.isEmpty()) {
            sendData(input);
            consoleArea.append("Command: " + input + "\n");
            Input.setText("");
            Input.requestFocus();
        }
    }
}
