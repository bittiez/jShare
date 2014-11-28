package client.Helpers;

import client.jgravatar.Gravatar;
import client.jgravatar.GravatarDefaultImage;
import client.jgravatar.GravatarRating;

import javax.swing.*;

/**
 * Created by tad on 11/6/14.
 */
public class avatar {
    public static int Size = 25;
    public String _email = null;
    public ImageIcon _image = null;
    public avatar(String email){
        _email = email;
        Gravatar gravatar = new Gravatar();
        gravatar.setSize(Size);
        gravatar.setRating(GravatarRating.GENERAL_AUDIENCES);
        gravatar.setDefaultImage(GravatarDefaultImage.IDENTICON);
        byte[] jpg = gravatar.download(_email);
        _image = new ImageIcon(jpg);
    }


}
