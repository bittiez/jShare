package client.Guis;

import client.Design.Notification;
import client.Design.uScrollBar;
import client.Helpers.*;
import client.mainClient;
import org.jdesktop.swingx.VerticalLayout;

import javax.swing.*;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;
import java.awt.*;
import java.awt.event.*;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * Created by tad on 11/4/14.
 */
public class UI2 {
    private JPanel botPanel;
    private JPanel mainFrame;
    private JPanel chatPane;
    public JScrollPane scrollPane;
    private JTextField inputField;
    private JButton sendButton;
    private JPanel onlineList;
    private JTabbedPane tabbedPane1;
    private JPanel chatTab;
    private JPanel ChangeLogTab;
    private JTextPane textPaneChaneLog;
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
    public String titleBase = "jChat " + mainClient.Version;
    private int messageCountMissed = 0;
    public onlineListManager onlineListClass;



    public UI2(config _Config, DataOutputStream output, String EMAIL) {
        this.con_out = output;
        Config = _Config;
        email = EMAIL;

        chatPanels = new ArrayList<JPanel>();
        chatMessages = new ArrayList<JTextPane>();
        chatNames = new ArrayList<JLabel>();
        chatHistory = new ArrayList<String>();
        frame = new JFrame(titleBase);
        frame.setContentPane(mainFrame);
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        frame.setIconImage(Config.ICON.getImage());
        textPaneChaneLog.setEditable(false);
        StringBuilder versionPanel = new StringBuilder();
        versionPanel.append("V2.7\n");
        versionPanel.append("--Changed message content to html\n");
        versionPanel.append("--Changed over to new notifications\n");
        versionPanel.append("\n\n");
        versionPanel.append("V2.6\n");
        versionPanel.append("--Added colored emails/user names\n");
        versionPanel.append("--Added Change log\n");
        versionPanel.append("--Fixed Ubuntu not downloading the update file to the right location\n");

        textPaneChaneLog.setText(versionPanel.toString());

        chatPane.setLayout(new VerticalLayout());
        chatPane.setSize(scrollPane.getWidth(), scrollPane.getHeight());
        chatPane.setAlignmentY(JPanel.TOP_ALIGNMENT);
        chatPane.setAlignmentX(JPanel.LEFT_ALIGNMENT);

        onlineList.setPreferredSize(new Dimension(150, scrollPane.getHeight()));
        onlineList.setMaximumSize(onlineList.getPreferredSize());
        onlineList.setMinimumSize(onlineList.getPreferredSize());


        chatPane.setBackground(Config._Theme.backGround);
        mainFrame.setBackground(Config._Theme.backGround);
        botPanel.setBackground(Config._Theme.backGround);
        inputField.setBackground(Config._Theme.inputFieldBackGround);
        sendButton.setBackground(Config._Theme.button);
        onlineList.setBackground(Config._Theme.backGround);
        chatTab.setBackground(Config._Theme.backGround);
        ChangeLogTab.setBackground(Config._Theme.backGround);
        tabbedPane1.setBackground(Config._Theme.backGround);
        textPaneChaneLog.setBackground(Config._Theme.backGround);


        chatPane.setForeground(Config._Theme.mainText);
        inputField.setForeground(Config._Theme.secondaryText);
        sendButton.setForeground(Config._Theme.mainText);
        chatTab.setForeground(Config._Theme.mainText);
        ChangeLogTab.setForeground(Config._Theme.mainText);
        tabbedPane1.setForeground(Config._Theme.mainText);
        textPaneChaneLog.setForeground(Config._Theme.mainText);

        chatPane.setBorder(Config._Theme.border);
        scrollPane.setBorder(Config._Theme.border);
        chatTab.setBorder(Config._Theme.border);
        ChangeLogTab.setBorder(Config._Theme.border);
        tabbedPane1.setBorder(Config._Theme.border);
        onlineList.setBorder(BorderFactory.createMatteBorder(0,1,0,0, Config._Theme.backGround.darker()));
        inputField.setBorder(BorderFactory.createLineBorder(Config._Theme.backGround.darker()));
        sendButton.setBorder(BorderFactory.createLineBorder(Config._Theme.backGround.darker()));


        sendButton.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON1) {
                    sendButtonPress();
                }
            }
        });

        inputField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if(messageCountMissed > 0){
                messageCountMissed = 0;
                frame.setTitle(titleBase);}
            }

            @Override
            public void focusLost(FocusEvent e) {

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

        frame.addWindowListener(new WindowAdapter() {
            public void windowGainedFocus(WindowEvent e) {
                messageCountMissed = 0;
                frame.setTitle(titleBase);
            }

            public void windowClosing(WindowEvent e)
            {
                if(Config.updateAvailable) {
                    System.out.println("Running updates...");
                    ProcessBuilder pb = new ProcessBuilder("java", "-jar", "jUpdate.jar");
                    try {
                        pb.start();
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                }
                System.exit(0);
            }
        });


        scrollPane.setAutoscrolls(true);
        new SmartScroller(scrollPane);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.getVerticalScrollBar().setUI(new uScrollBar(Config));


        frame.pack();
        onlineListClass = new onlineListManager(this, onlineList);
        //onlineListClass.genFullUserList();
        frame.setSize(750, 400);
        frame.setLocationRelativeTo(null);
        inputField.requestFocus();
        frame.setVisible(true);
    }


    final Desktop desktop = Desktop.getDesktop();

    public void addMessage(String message){
        String[] msg = message.split(":", 2);
        JLabel name = new JLabel(msg[0]);
        name.setAlignmentX(JPanel.LEFT_ALIGNMENT);
        name.setForeground(colorManager(msg[0]));
        //name.setForeground(Config._Theme.mainText);

        JTextPane tl = new JTextPane();
        tl.setContentType("text/html");
        String tColor = Integer.toHexString( Config._Theme.mainText.getRGB() & 0x00ffffff );
        tl.setText("<html><span style='color: #"+tColor+"; font: verdana, arial;'>"+msg[1] + "</span></html>");
        tl.setEditable(false);
        tl.setAlignmentX(JPanel.RIGHT_ALIGNMENT);
        tl.setForeground(Config._Theme.mainText);
        tl.setBackground(Config._Theme.backGround);
        tl.setMaximumSize(new Dimension(chatPane.getWidth() - 10, 150));
        tl.setPreferredSize(new Dimension(chatPane.getWidth() -10, 20));

        tl.addHyperlinkListener(new HyperlinkListener() {

            public void hyperlinkUpdate(HyperlinkEvent hle) {
                if (HyperlinkEvent.EventType.ACTIVATED.equals(hle.getEventType())) {
                    try {

                        System.out.println(hle.getURL());
                        try {
                            desktop.browse(new URI(hle.getURL().toString()));
                        } catch (URISyntaxException ex) {
                            ex.printStackTrace();
                        }
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }

                }
            }
        });


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

        chatPane.updateUI();

        if(!frame.isActive()) {
            messageCountMissed++;
            new Notification(messageCountMissed + " unread messages!", messageCountMissed + " unread messages!", 5);
            frame.setTitle(titleBase + " (" + messageCountMissed + ")");
        } else {
            if(messageCountMissed > 0) {
                frame.setTitle(titleBase);
                messageCountMissed = 0;
            }
        }
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

    public Color colorManager(String email){
        int i = avatarEmails.indexOf(email);
        if(i != -1){
            return avatars.get(i).fontColor;
        } else {
            avatar a = new avatar(email);
            avatarEmails.add(email);
            avatars.add(a);
            return a.fontColor;
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
    public void recData(inputHandler received){
        boolean updateUserList = false;

        switch(received.inputtype){
            case MESSAGE:
                addMessage(received.message);
                break;
            case CONNECTED:
                onlineListClass.addUser(received.email);
                updateUserList = true;
                break;
            case DISCONNECTED:
                onlineListClass.remUser(received.email);
                updateUserList = true;
                break;
            case ONLINELIST:
                String[] users = received.message.split("#");
                for (int i = 0; i < users.length; i++) {
                    onlineListClass.addUser(users[i]);
                }
                updateUserList = true;
                break;
        }
        if(updateUserList)
            onlineListClass.genFullUserList();
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
