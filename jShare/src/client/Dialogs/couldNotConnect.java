package client.Dialogs;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class couldNotConnect extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;

    public couldNotConnect() {
        start();
    }
    public couldNotConnect(Exception e){
        System.out.println(e.getMessage());
        start();
    }

    private void start(){
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);
        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK();
            }
        });

        this.setTitle("Could not connect");
        this.pack();
        this.setVisible(true);
    }


    private void onOK() {
// add your code here
        dispose();
        setVisible(false);
        System.exit(0);
    }
}
