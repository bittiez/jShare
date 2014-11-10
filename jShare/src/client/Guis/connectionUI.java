package client.Guis;

import client.Helpers.fileHandler;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static client.staticClasses.staticColors.*;

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
    private JLabel labelConection;
    private JLabel labelServer;
    private JLabel labelPort;
    private JLabel labelUserSettings;
    public String[] connection = new String[2];
    public String[] userSettings = new String[2];
    public  JFrame frame = null;
    private ArrayList<String> config = new ArrayList<String>();


    public connectionUI() {
        frame = new JFrame("Connection Settings");
        frame.setContentPane(mainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        serverPort.setText("25984");

        //Get settings if there is a settings file
        String settings = fileHandler.readFile("config.jShare");
        if(!settings.isEmpty()){
            Matcher m = Pattern.compile("\\{([^}]+)\\}").matcher(settings);
            while(m.find()) {
                config.add(m.group(1));
            }
                String[] addresses = null, ports = null;
                if(config.get(0).contains("\\|"))
                    addresses = config.get(0).split("\\|");
                if(config.get(1).contains("\\|"))
                    ports = config.get(1).split("\\|");


                serverAddress.setText(addresses == null ? config.get(0) : addresses[0]);
                serverPort.setText(ports == null ? config.get(1) : ports[0]);
                if(config.size() > 2)
                    userEmail.setText(config.get(2));

            }


        setThemeDark();

        connectButton.setPreferredSize(new Dimension(frame.getWidth() - 15, 45));

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


                    StringBuilder sb = new StringBuilder();
                    sb.append("{");
                    sb.append(serverAddress.getText());
                    sb.append("}");
                    sb.append("{");
                    sb.append(serverPort.getText());
                    sb.append("}");
                    sb.append("{");
                    sb.append(userEmail.getText());
                    sb.append("}");


                    fileHandler.saveFile("config.jShare", sb.toString());

                    connectButton.requestFocus();
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
    public void setThemeuOrange(){
        mainPanel.setBackground(uOrangeLight);
        topPanel.setBackground(uOrangeLight);
        botPanel.setBackground(uOrangeLight);

        email.setBackground(uOrange);
        server.setBackground(uOrange);
        port.setBackground(uOrange);

        serverAddress.setBackground(uOrange);
        serverPort.setBackground(uOrange);
        userEmail.setBackground(uOrange);

        Border bord = BorderFactory.createEmptyBorder();

        serverAddress.setBorder(bord);
        serverPort.setBorder(bord);
        userEmail.setBorder(bord);

        labelConection.setForeground(white);
        labelEmail.setForeground(white);
        labelPort.setForeground(white);
        labelServer.setForeground(white);
        labelUserSettings.setForeground(white);
        serverAddress.setForeground(uPurple);
        serverPort.setForeground(uPurple);
        userEmail.setForeground(uPurple);

        connectButton.setBackground(uButtonColor);
        connectButton.setForeground(white);
        connectButton.setBorder(BorderFactory.createLineBorder(uOrange.darker()));
    }
    public void setThemeDark(){
        mainPanel.setBackground(DarkBG);
        topPanel.setBackground(DarkBG);
        botPanel.setBackground(DarkBG);

        email.setBackground(DarkFields);
        server.setBackground(DarkFields);
        port.setBackground(DarkFields);

        serverAddress.setBackground(DarkFields);
        serverPort.setBackground(DarkFields);
        userEmail.setBackground(DarkFields);

        Border bord = BorderFactory.createEmptyBorder();

        serverAddress.setBorder(bord);
        serverPort.setBorder(bord);
        userEmail.setBorder(bord);

        labelConection.setForeground(DarkMainText);
        labelEmail.setForeground(DarkMainText);
        labelPort.setForeground(DarkMainText);
        labelServer.setForeground(DarkMainText);
        labelUserSettings.setForeground(DarkMainText);
        serverAddress.setForeground(DarkSecondaryText);
        serverPort.setForeground(DarkSecondaryText);
        userEmail.setForeground(DarkSecondaryText);

        connectButton.setBackground(DarkButton);
        connectButton.setForeground(DarkMainText);
        connectButton.setBorder(BorderFactory.createLineBorder(DarkBG.darker()));
    }

}
