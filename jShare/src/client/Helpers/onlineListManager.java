package client.Helpers;

import client.Design.WrapLayout;
import client.Guis.UI2;
import javafx.scene.layout.Pane;
import org.jdesktop.swingx.HorizontalLayout;
import org.jdesktop.swingx.VerticalLayout;
import sun.awt.OrientableFlowLayout;
import sun.awt.VerticalBagLayout;
import sun.security.krb5.internal.PAData;

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

        setLayout();

        Emails = new ArrayList<String>();
        _AvatarLabels = new ArrayList<JLabel>();
    }

    public void setLayout(){
        //Panel.setLayout(new GridLayout(0, 5));
        Panel.setLayout(new WrapLayout());
        //Panel.setLayout(new FlowLayout(FlowLayout.LEFT));
    }

    public void genFullUserList(){
        resetPanel();
        //System.out.println("<------->");
            for (int i = 0; i < Emails.size(); i++) {
                if (Emails.get(i) == "" || Emails.get(i) == null || Emails.get(i).isEmpty()) {
                    Emails.remove(i);
                    continue;
                }
                //System.out.println(Emails.get(i));
                ImageIcon II = _UI.avatarManager(Emails.get(i));

                JLabel tempLabel = new JLabel(II);
                tempLabel.setToolTipText(Emails.get(i));
                _AvatarLabels.add(tempLabel);
                Panel.add(tempLabel);
            }
        //System.out.println("<------->");

            Panel.revalidate();
            Panel.repaint();

    }

    public void addUser(String Email){
        if(Emails.indexOf(Email) < 0) {
            Emails.add(Email);
        }
    }

    public void remUser(String Email){
        Emails.remove(Email);
    }

    private void resetPanel(){
        for (int i = 0; i < Panel.getComponentCount(); i++) {
            Panel.remove(i);
        }
        Panel.removeAll();
        _AvatarLabels.clear();



        //Panel = new JPanel();
        //setLayout();
    }
}
