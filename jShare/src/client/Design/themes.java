package client.Design;

import java.awt.*;

import static client.Design.themeNames.*;

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


    public themes(themeNames name){
        switch (name){
            case DarkTheme:
                setDarkTheme();
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
    }
}
