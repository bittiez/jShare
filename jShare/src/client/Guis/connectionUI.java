package client.Guis;

import client.Helpers.fileHandler;

import javax.swing.*;
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
    private JPanel jp1;
    public String[] connection = new String[2];
    public  JFrame frame = null;

    public connectionUI() {
        frame = new JFrame("Connection Settings");
        frame.setContentPane(mainPanel);
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        frame.pack();
        frame.setSize(400, 500);
        frame.setLocation(-1,-1);
        frame.setVisible(true);
        connectButton.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON1) {
                    connection[0] = serverAddress.getText();
                    connection[1] = serverPort.getText();

                    StringBuilder sb = new StringBuilder();
                    sb.append("<address>");
                    sb.append(serverAddress.getText());
                    sb.append("</address>");
                    sb.append("<port>");
                    sb.append(serverPort.getText());
                    sb.append("</port>");

                    fileHandler.saveFile("config.xml", sb.toString());


                    frame.setVisible(false);
                    frame.dispose();
                }
            }
        });
    }

}
