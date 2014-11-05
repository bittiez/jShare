package client.Guis;

import client.Helpers.fileHandler;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
    public String[] connection = new String[2];
    public String[] userSettings = new String[2];
    public  JFrame frame = null;
    private ArrayList<String> config = new ArrayList<String>();

    public connectionUI() {
        frame = new JFrame("Connection Settings");
        frame.setContentPane(mainPanel);
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        //Get settings if there is a settings file
        String settings = fileHandler.readFile("config.jShare");
        if(!settings.isEmpty()){
            Matcher m = Pattern.compile("\\{([^}]+)\\}").matcher(settings);
            //"\\(([^)]+)\\)"
            int i = 0;
            while(m.find()) {
                config.add(m.group(1));
                i++;
            }

                serverAddress.setText(config.get(0));
                serverPort.setText(config.get(1));
                if(config.size() > 2)
                    userEmail.setText(config.get(2));

            }


        frame.pack();
        frame.setSize(400, 300);
        frame.setLocation(-1,-1);
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


                    frame.setVisible(false);
                    frame.dispose();
                }
            }
        });
    }

}
