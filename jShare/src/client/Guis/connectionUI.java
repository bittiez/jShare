package client.Guis;

import client.Helpers.config;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Created by tad on 11/3/14.
 */
public class connectionUI {
    private JPanel mainPanel;
    private JTextField serverAddress;
    private JTextField serverPort;
    private JButton connectButton;
    private JTextField userEmail;
    private JPanel topPanel;
    private JPanel botPanel;
    private JPanel email;
    private JPanel server;
    private JPanel port;
    private JLabel labelEmail;
    private JLabel labelConnection;
    private JLabel labelServer;
    private JLabel labelPort;
    private JLabel labelUserSettings;
    public String[] connection = new String[2];
    public String[] userSettings = new String[2];
    public  JFrame frame = null;
    public config Config = null;


    public connectionUI(config _Config) {
        Config=_Config;
        frame = new JFrame("Connection Settings");
        frame.setContentPane(mainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        serverPort.setText(Config.port);
        serverAddress.setText(Config.address);
        userEmail.setText(Config.userEmail);

        setTheme();

        connectButton.setPreferredSize(new Dimension(frame.getWidth() - 15, 40));

        frame.pack();
        frame.setSize(300, 300);
        frame.setLocationRelativeTo(null);

        frame.setVisible(true);
        connectButton.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON1) {
                    connection[0] = serverAddress.getText();
                    connection[1] = serverPort.getText();
                    userSettings[0] = userEmail.getText();
                    Config.address = connection[0];
                    Config.port = connection[1];
                    Config.userEmail = userSettings[0];
                    Config.Save();


                    frame.setVisible(false);
                    frame.dispose();
                }
            }
        });




        userEmail.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                userEmail.selectAll();
            }
        });
        serverPort.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                serverPort.selectAll();
            }
        });
        serverAddress.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                serverAddress.selectAll();
            }
        });
    }

    public void setTheme(){
        mainPanel.setBackground(Config._Theme.backGround);
        topPanel.setBackground(Config._Theme.backGround);
        botPanel.setBackground(Config._Theme.backGround);

        email.setBackground(Config._Theme.inputFieldBackGround);
        server.setBackground(Config._Theme.inputFieldBackGround);
        port.setBackground(Config._Theme.inputFieldBackGround);

        serverAddress.setBackground(Config._Theme.inputFieldBackGround);
        serverPort.setBackground(Config._Theme.inputFieldBackGround);
        userEmail.setBackground(Config._Theme.inputFieldBackGround);

        Border bord = Config._Theme.border;

        serverAddress.setBorder(bord);
        serverPort.setBorder(bord);
        userEmail.setBorder(bord);

        labelConnection.setForeground(Config._Theme.mainText);
        labelEmail.setForeground(Config._Theme.mainText);
        labelPort.setForeground(Config._Theme.mainText);
        labelServer.setForeground(Config._Theme.mainText);
        labelUserSettings.setForeground(Config._Theme.mainText);
        serverAddress.setForeground(Config._Theme.secondaryText);
        serverPort.setForeground(Config._Theme.secondaryText);
        userEmail.setForeground(Config._Theme.secondaryText);

        connectButton.setBackground(Config._Theme.button);
        connectButton.setForeground(Config._Theme.mainText);
        connectButton.setBorder(BorderFactory.createLineBorder(Config._Theme.backGround.darker()));
    }

}
