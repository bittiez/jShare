package client.Guis;

import org.jdesktop.swingx.JXLabel;
import org.jdesktop.swingx.VerticalLayout;

import javax.swing.*;
import javax.swing.text.StyledDocument;
import java.awt.*;
import java.util.ArrayList;

/**
 * Created by tad on 11/4/14.
 */
public class UI2 {
    private JPanel botPanel;
    private JPanel mainFrame;
    private JPanel chatPane;
    private JScrollPane scrollPane;
    JFrame frame = null;

    private ArrayList<JPanel> chatPanels = null;
    private ArrayList<JXLabel> chatMessages = null;


    public UI2() {
        chatPanels = new ArrayList<JPanel>();
        chatMessages = new ArrayList<JXLabel>();
        frame = new JFrame("UI2");
        frame.setContentPane(mainFrame);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        chatPane.setLayout(new VerticalLayout());
        chatPane.setSize(scrollPane.getWidth(), scrollPane.getHeight());
        chatPane.setAlignmentY(JPanel.TOP_ALIGNMENT);
        chatPane.setAlignmentX(JPanel.LEFT_ALIGNMENT);

        //scrollPane.remove(chatPane);
        frame.pack();
        frame.setSize(400, 500);
        frame.setVisible(true);
    }

    public void addMessage(String message){
        JXLabel tl = new JXLabel(message);
        tl.setAlignmentX(JPanel.LEFT_ALIGNMENT);

        chatPane.add(tl);
        //scrollPane.add(tl);
        chatMessages.add(tl);
        chatPane.repaint();
    }
}
