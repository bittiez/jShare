package client.Helpers;

/**
 * Created by tad on 11/27/2014.
 */

public class inputHandler {
    public String message;
    public String email;
    public inputType inputtype;
    public inputHandler(String DATA){
        if(DATA.indexOf("{") == 0){
            int command = Integer.parseInt(DATA.substring(1, 4));
            String data = DATA.substring(5);

            switch (command){
                case 100:
                    inputtype = inputType.MESSAGE;
                    message = data;
                    break;
                case 101:
                    inputtype = inputType.CONNECTED;
                    email = data;
                    break;
                case 102:
                    inputtype = inputType.DISCONNECTED;
                    email = data;
                    break;
                case 103:
                    inputtype = inputType.ONLINELIST;
                    message = data;
                    break;
                default:
                    inputtype = inputType.MESSAGE;
                    message = data;
                    break;
            }
        } else
        {
            inputtype = inputType.MESSAGE;
            message = DATA;
        }
        //System.out.println(message);
    }
}
