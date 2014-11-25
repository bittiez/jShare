package client.Helpers;

import client.Guis.UI2;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * Created by tad on 11/24/2014.
 */
public class onlineListManager {
    public ArrayList<String> Emails;

    private ArrayList<avatar> _AvatarImages;
    public JPanel Panel;
    private ArrayList<JLabel> _AvatarLabels;
    private UI2 _UI;
    public onlineListManager(UI2 UI, JPanel parentpanel){
        _UI = UI;
        Panel = parentpanel;

        Emails = new ArrayList<String>();
        Emails.add(UI.email);
        _AvatarLabels = new ArrayList<JLabel>();
        _AvatarImages = new ArrayList<avatar>();
        //genFullUserList();
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
        Panel = new JPanel();
        Panel.setLayout(new GridLayout(0, 3));
    }
}
