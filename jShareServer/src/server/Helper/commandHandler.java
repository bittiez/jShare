package server.Helper;

import server.mainServer;

/**
 * Created by tad on 10/28/14.
 */
public class commandHandler {
    //Command format: {COMMAND}::{111}::{PARAMATER|PARAMATER}::{OTHER}
    public static commandReturn findCommand(String data){
        commandReturn RETURN = new commandReturn();
        if(data.indexOf("{COMMAND}") != -1){
            String[] commandSplit;
            String commandNum;
            String[] commandParams = new String[0];
            String commandOther;

            commandSplit = data.split("::");
            commandNum = removeBrackets(commandSplit[1]);
            if(commandSplit.length > 2){
                String params = removeBrackets(commandSplit[2]);
                if(commandSplit[2].contains("|")) {
                    commandParams = params.split("\\|");
                }
                else{
                    commandParams = new String[1];
                    commandParams[0] = params;
                }

                if(commandSplit.length > 3){
                    commandOther = commandSplit[3];
                }
            }

            //Switch commands here
            switch (Integer.parseInt(commandNum)){
                case 100:
                    RETURN.TYPE = commandType.LOGIN;
                    RETURN.message = commandParams[0];
                    break;
                default:
                    RETURN.TYPE = commandType.MESSAGE;
                    RETURN.message=commandParams[0];
                    break;
            }
        }
        return RETURN;

    }

    private static String removeBrackets(String data){
        return data.replace("{", "").replace("}", "");
    }
}
