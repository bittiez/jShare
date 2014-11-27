package client.Helpers;

import client.Guis.UI2;
import org.jdesktop.swingx.VerticalLayout;
import sun.awt.VerticalBagLayout;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * Created by tad on 11/24/2014.
 */
public class onlineListManager {
    public ArrayList<String> Emails;

    public JPanel Panel;
    private ArrayList<JLabel> _AvatarLabels;
    private UI2 _UI;
    public onlineListManager(UI2 UI, JPanel parentpanel){
        _UI = UI;
        Panel = parentpanel;

        Panel.setLayout(new VerticalLayout());
        //Panel.setLayout(new GridLayout(0, 3));


        Emails = new ArrayList<String>();
        _AvatarLabels = new ArrayList<JLabel>();
    }

    public void genFullUserList(){
        resetPanel();
            for (int i = 0; i < Emails.size(); i++) {
                ImageIcon II = _UI.avatarManager(Emails.get(i));

                JLabel tempLabel = new JLabel(II);
                    _AvatarLabels.add(tempLabel);
                Panel.add(tempLabel);
            }
        Panel.updateUI();
    }

    public void addUser(String Email){
        Emails.add(Email);
    }

    public void remUser(String Email){
        Emails.remove(Email);
    }

    private void resetPanel(){
        Panel.removeAll();
    }
}
