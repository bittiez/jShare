package client.Helpers;

import java.awt.*;
import java.util.Random;

/**
 * Created by tad on 11/30/2014.
 */
public class randomColor {
    public static Color randomColor(){
        Random rand = new Random();
        float r = rand.nextFloat();
        float g = rand.nextFloat();
        float b = rand.nextFloat();
        Color randomColor = new Color(r, g, b);
        return randomColor;
    }
}
