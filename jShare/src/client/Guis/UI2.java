package client.Guis;

import client.Design.uScrollBar;
import client.Helpers.SmartScroller;
import client.Helpers.avatar;
import client.mainClient;
import org.jdesktop.swingx.JXLabel;
import org.jdesktop.swingx.VerticalLayout;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;

import static client.staticClasses.staticColors.*;

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
    public JFrame frame = null;

    private ArrayList<JPanel> chatPanels = null;
    private ArrayList<JTextPane> chatMessages = null;
    private ArrayList<JXLabel> chatNames = null;
    private ArrayList<String> chatHistory = null;
    private int lastChatHistoryRequested = 0;
    public String email = "";

    public ArrayList<String> avatarEmails = new ArrayList<String>();
    public ArrayList<avatar> avatars = new ArrayList<avatar>();

    private Socket connection;
    private DataOutputStream con_out;
    private BufferedReader con_in;

    private Color BG = null;
    private Color TEXT = null;


    public UI2(Socket sock, DataOutputStream output, final BufferedReader input) {
        this.connection = sock;
        this.con_out = output;
        this.con_in = input;

        chatPanels = new ArrayList<JPanel>();
        chatMessages = new ArrayList<JTextPane>();
        chatNames = new ArrayList<JXLabel>();
        chatHistory = new ArrayList<String>();
        frame = new JFrame("UI2");
        frame.setContentPane(mainFrame);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        chatPane.setLayout(new VerticalLayout());
        chatPane.setSize(scrollPane.getWidth(), scrollPane.getHeight());
        chatPane.setAlignmentY(JPanel.TOP_ALIGNMENT);
        chatPane.setAlignmentX(JPanel.LEFT_ALIGNMENT);

        chatPane.setBackground(uOrangeLight);
        chatPane.setForeground(white);
        chatPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        mainFrame.setBackground(uOrangeLight);
        botPanel.setBackground(uOrangeLight);
        inputField.setBackground(uOrange);
        inputField.setForeground(white);
        inputField.setBorder(BorderFactory.createLineBorder(uOrange.darker()));
        sendButton.setBackground(uOrange);
        sendButton.setForeground(white);
        sendButton.setBorder(BorderFactory.createLineBorder(uOrange.darker()));


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
                } else if(keyEvent.getKeyCode() == KeyEvent.VK_DOWN){
                    if(chatHistory.size() > 0){
                        inputField.setText(chatHistory.get((chatHistory.size() - lastChatHistoryRequested )-1));
                        if(lastChatHistoryRequested < 1)
                            lastChatHistoryRequested = 0;
                        else
                            lastChatHistoryRequested--;
                    }
                }
            }
        });
        scrollPane.setAutoscrolls(true);
        new SmartScroller(scrollPane);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.getVerticalScrollBar().setUI(new uScrollBar());



        frame.pack();
        frame.setSize(750, 400);
        frame.setLocationRelativeTo(null);
        inputField.requestFocus();
        frame.setVisible(true);
    }

    public void addMessage(String message){
        String[] msg = message.split(":", 2);
        JXLabel name = new JXLabel(msg[0]);
        name.setAlignmentX(JPanel.LEFT_ALIGNMENT);
        name.setForeground(white);
        name.setBackground(uOrange);

        JTextPane tl = new JTextPane();
        tl.setText(msg[1]);
        tl.setEditable(false);
        tl.setAlignmentX(JPanel.RIGHT_ALIGNMENT);
        tl.setForeground(white);
        tl.setBackground(chatPane.getBackground());
        tl.setMaximumSize(new Dimension(chatPane.getWidth() - 10, 150));
        tl.setPreferredSize(new Dimension(chatPane.getWidth() -10, 20));


        JPanel jp = new JPanel();
        jp.setMaximumSize(new Dimension(chatPane.getWidth(), chatPane.getHeight()));


        JXLabel avat = new JXLabel(avatarManager(msg[0]));
        JPanel na = new JPanel();
        na.setLayout(new FlowLayout(FlowLayout.LEFT) );
        na.setBackground(chatPane.getBackground());
        na.add(avat);
        na.add(name);

        jp.setLayout(new GridLayout(2,1));
        jp.setBackground(chatPane.getBackground());
        jp.add(na);
        jp.add(tl);


        if(msg[0].trim().toLowerCase().equals(this.email.trim().toLowerCase())){
            name.setAlignmentX(JLabel.RIGHT_ALIGNMENT);
            name.setForeground(unameColor);
            tl.setAlignmentX(JLabel.RIGHT_ALIGNMENT);
            jp.setAlignmentX(JLabel.RIGHT_ALIGNMENT);
        }


        chatPane.add(jp);
        chatPanels.add(jp);
        chatPanels.add(na);
        chatNames.add(name);
        chatNames.add(avat);
        chatMessages.add(tl);

        //int sp = scrollPane.getVerticalScrollBar().getMaximum();
        //scrollPane.getVerticalScrollBar().setValue(sp);

        chatPane.updateUI();
    }

    public void setThemeuOrange(){
        BG = uOrangeLight;
    }

    public ImageIcon avatarManager(String email){

        int i = avatarEmails.indexOf(email);
        if(i != -1){
            return avatars.get(i)._image;
        } else {
            avatar a = new avatar(email);
            avatarEmails.add(email);
            avatars.add(a);
            return a._image;
        }
    }

    public static float toFloat(int i){
        return (float)i;
    }

    public void sendData(String data){
        try {
            con_out.writeBytes(data + "\n");
            con_out.flush();
        } catch (IOException e) {
            //running = false;
            mainClient.log("Error while trying to send data. Closing connection.");
            System.exit(0);
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
