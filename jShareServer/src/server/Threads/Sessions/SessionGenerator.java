package server.Threads.Sessions;

import java.util.UUID;

/**
 * Created by tad on 12/21/2014.
 */
public class SessionGenerator {
    public static String getSession(SessionListener sessionListener, String email){
        if(sessionListener.sessions.containsValue(email))
            return null;
        String sessionID = generateNewSession();
        while(sessionListener.sessions.containsKey(sessionID))
            sessionID = generateNewSession();
        return sessionID;
    }

    private static String generateNewSession(){
        return UUID.randomUUID().toString();
    }
}
