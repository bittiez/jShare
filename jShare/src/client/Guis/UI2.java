package client.Guis;

import client.Design.uScrollBar;
import client.Helpers.SmartScroller;
import client.Helpers.avatar;
import client.Helpers.config;
import client.mainClient;
import org.jdesktop.swingx.VerticalLayout;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.DataOutputStream;
import java.io.IOException;
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
    public JFrame frame = null;

    private ArrayList<JPanel> chatPanels = null;
    private ArrayList<JTextPane> chatMessages = null;
    private ArrayList<JLabel> chatNames = null;
    private ArrayList<String> chatHistory = null;
    private int lastChatHistoryRequested = 0;
    public String email = "";

    public ArrayList<String> avatarEmails = new ArrayList<String>();
    public ArrayList<avatar> avatars = new ArrayList<avatar>();

    private DataOutputStream con_out;
    private config Config= null;



    public UI2(config _Config, DataOutputStream output) {
        this.con_out = output;
        Config = _Config;

        chatPanels = new ArrayList<JPanel>();
        chatMessages = new ArrayList<JTextPane>();
        chatNames = new ArrayList<JLabel>();
        chatHistory = new ArrayList<String>();
        frame = new JFrame("UI2");
        frame.setContentPane(mainFrame);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        chatPane.setLayout(new VerticalLayout());
        chatPane.setSize(scrollPane.getWidth(), scrollPane.getHeight());
        chatPane.setAlignmentY(JPanel.TOP_ALIGNMENT);
        chatPane.setAlignmentX(JPanel.LEFT_ALIGNMENT);




        chatPane.setBackground(Config._Theme.backGround);
        mainFrame.setBackground(Config._Theme.backGround);
        botPanel.setBackground(Config._Theme.backGround);
        inputField.setBackground(Config._Theme.inputFieldBackGround);
        sendButton.setBackground(Config._Theme.button);

        chatPane.setForeground(Config._Theme.mainText);
        inputField.setForeground(Config._Theme.secondaryText);
        sendButton.setForeground(Config._Theme.mainText);

        chatPane.setBorder(Config._Theme.border);
        scrollPane.setBorder(Config._Theme.border);
        inputField.setBorder(BorderFactory.createLineBorder(Config._Theme.backGround.darker()));
        sendButton.setBorder(BorderFactory.createLineBorder(Config._Theme.backGround.darker()));


        /*
        MenuBar menuBar = new MenuBar();

        Menu fileMenu = new Menu();
        fileMenu.setLabel("File");
        fileMenu.add(new MenuItem("Exit"));
        menuBar.add(fileMenu);

        Menu themeMenu = new Menu();
        themeMenu.setLabel("Themes");
        themeMenu.add(new MenuItem("uOrange"));
        themeMenu.add(new MenuItem("Dark"));
        menuBar.add(themeMenu);
        frame.setMenuBar(menuBar);
        */




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
        scrollPane.getVerticalScrollBar().setUI(new uScrollBar(Config));



        frame.pack();
        frame.setSize(750, 400);
        frame.setLocationRelativeTo(null);
        inputField.requestFocus();
        frame.setVisible(true);
    }

    public void addMessage(String message){
        String[] msg = message.split(":", 2);
        JLabel name = new JLabel(msg[0]);
        name.setAlignmentX(JPanel.LEFT_ALIGNMENT);
        name.setForeground(Config._Theme.mainText);
        //name.setBackground(Config._Theme.backGround);

        JTextPane tl = new JTextPane();
        tl.setText(msg[1]);
        tl.setEditable(false);
        tl.setAlignmentX(JPanel.RIGHT_ALIGNMENT);
        tl.setForeground(Config._Theme.mainText);
        tl.setBackground(Config._Theme.backGround);
        tl.setMaximumSize(new Dimension(chatPane.getWidth() - 10, 150));
        tl.setPreferredSize(new Dimension(chatPane.getWidth() -10, 20));


        JPanel jp = new JPanel();
        jp.setMaximumSize(new Dimension(chatPane.getWidth(), chatPane.getHeight()));


        JLabel avat = new JLabel(avatarManager(msg[0]));
        JPanel na = new JPanel();
        na.setLayout(new FlowLayout(FlowLayout.LEFT) );
        na.setBackground(Config._Theme.backGround);
        na.add(avat);
        na.add(name);

        jp.setLayout(new GridLayout(2,1));
        jp.setBackground(chatPane.getBackground());



        if(msg[0].trim().toLowerCase().equals(this.email.trim().toLowerCase())){
            name.setAlignmentX(JLabel.RIGHT_ALIGNMENT);
            name.setForeground(Config._Theme.userName);
            tl.setAlignmentX(JLabel.RIGHT_ALIGNMENT);
            jp.setAlignmentX(JLabel.RIGHT_ALIGNMENT);
        }
            jp.add(na);
            jp.add(tl);



        chatPane.add(jp);
        chatPanels.add(jp);
        chatPanels.add(na);
        chatNames.add(name);
        chatNames.add(avat);
        chatMessages.add(tl);

        //int sp = scrollPane.getVerticalScrollBar().getMaximum();
        //scrollPane.getVerticalScrollBar().setValue(sp);

        chatPane.updateUI();

        if(!frame.isActive())
            frame.toFront();
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

    public void sendData(String data){
        try {
            con_out.writeBytes(data + "\n");
            con_out.flush();
        } catch (IOException e) {
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
