package client.Guis;

import client.mainClient;
import org.jdesktop.swingx.JXLabel;
import org.jdesktop.swingx.VerticalLayout;

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
 * Created by tad on 11/4/14.
 */
public class UI2 {
    private JPanel botPanel;
    private JPanel mainFrame;
    private JPanel chatPane;
    private JScrollPane scrollPane;
    private JTextField inputField;
    private JButton sendButton;
    JFrame frame = null;

    private ArrayList<JPanel> chatPanels = null;
    private ArrayList<JXLabel> chatMessages = null;
    private ArrayList<String> chatHistory = null;
    private int lastChatHistoryRequested = 0;

    private Socket connection;
    private DataOutputStream con_out;
    private BufferedReader con_in;


    public UI2(Socket sock, DataOutputStream output, final BufferedReader input) {
        this.connection = sock;
        this.con_out = output;
        this.con_in = input;

        chatPanels = new ArrayList<JPanel>();
        chatMessages = new ArrayList<JXLabel>();
        chatHistory = new ArrayList<String>();
        frame = new JFrame("UI2");
        frame.setContentPane(mainFrame);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        chatPane.setLayout(new VerticalLayout());
        chatPane.setSize(scrollPane.getWidth(), scrollPane.getHeight());
        chatPane.setAlignmentY(JPanel.TOP_ALIGNMENT);
        chatPane.setAlignmentX(JPanel.LEFT_ALIGNMENT);

        sendButton.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON1) {
                    sendButtonPress();
                }
            }
        });

        inputField.addKeyListener(new KeyListener() {
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
                        inputField.setText(chatHistory.get((chatHistory.size() - lastChatHistoryRequested )-1));
                        lastChatHistoryRequested++;
                    }
                }
            }
        });

        frame.pack();
        frame.setSize(400, 500);
        frame.setVisible(true);
    }

    public void addMessage(String message){
        JXLabel tl = new JXLabel(message);
        tl.setAlignmentX(JPanel.LEFT_ALIGNMENT);

        chatPane.add(tl);
        chatMessages.add(tl);
        chatPane.updateUI();
        //chatPane.repaint();
    }

    public void sendData(String data){
        try {
            con_out.writeBytes(data + "\n");
            con_out.flush();
        } catch (IOException e) {
            //running = false;
            mainClient.log("Error while trying to send data. Closing connection.");
        }
    }
    public void recData(String data){
        addMessage(data);
    }
    public void sendButtonPress(){
        String input = inputField.getText().trim();
        if(!input.isEmpty()) {
            chatHistory.add(input);
            lastChatHistoryRequested = 0;
            while(chatHistory.size() > 20){
                chatHistory.remove(0);
            }
            input = "{COMMAND}::{1}::"+input;
            sendData(input);
            //consoleArea.append("You: " + input + "\n");
            inputField.setText("");
            inputField.requestFocus();
        }
    }
}
