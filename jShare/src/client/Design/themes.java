package client.Design;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

/**
 * Created by tad on 11/9/14.
 */
public class themes {
    public themeNames currentTheme = null;

    public Color backGround = null;
    public Color mainText = null;
    public Color inputFieldBackGround = null;
    public Color secondaryText = null;
    public Color button = null;
    public Color userName = null;
    public Border border = null;



    public themes(themeNames name){
        switch (name){
            case DarkTheme:
                setDarkTheme();
                break;
            case uOrangeTheme:
                setuOrangeTheme();
                break;
        }
        currentTheme = name;
    }

    public void setDarkTheme(){
        backGround = Color.decode("#27262A");
        mainText = Color.decode("#646357");
        inputFieldBackGround = Color.decode("#3F3C44");
        secondaryText = Color.decode("#4E4D45").brighter();
        button = backGround.darker();
        userName = Color.decode("#2BB1E0");
        border = BorderFactory.createEmptyBorder();
    }

    public void setuOrangeTheme(){
        backGround = Color.decode("#DD4814");
        mainText = Color.white;
        inputFieldBackGround = Color.decode("#E05A2B");
        secondaryText = Color.decode("#EEA389");
        button = Color.decode("#DD4814").darker();
        userName = Color.decode("#2BB1E0");
        border = BorderFactory.createEmptyBorder();
    }
}
