package client.Dialogs;

import javax.swing.*;
import java.awt.event.*;

public class login extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JTextField textField1;
    public String email = "";

    public login() {
        this.setTitle("Please enter your email");
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK();
            }
        });

// call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

// call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }

    private void onOK() {
// add your code here
        this.email = textField1.getText();
        if(this.email.isEmpty())
        {

        }
        else
            dispose();
    }

    private void onCancel() {
// add your code here if necessary
        //dispose();
    }

    public static void main(String[] args) {
        login dialog = new login();
        dialog.pack();
        dialog.setVisible(true);
        System.exit(0);
    }
}
