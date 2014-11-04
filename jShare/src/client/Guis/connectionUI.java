package client.Guis;

import javax.swing.*;

/**
 * Created by tad on 11/3/14.
 */
public class connectionUI {
    private JPanel mainPanel;
    private JTextField serverAddress;
    private JTextField serverPort;
    private JButton connectButton;
    private JPanel jp1;

    public connectionUI() {
        JFrame frame = new JFrame("Connection Settings");
        frame.setContentPane(mainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 500);

        frame.pack();
        frame.setVisible(true);
    }
}
