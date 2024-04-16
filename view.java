package com;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Collections;
import java.util.Set;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ConcurrentHashMap;

@ServerEndpoint("/view")
public class view {
    
    private static Set<Session> userSessions = Collections.newSetFromMap(new ConcurrentHashMap<Session, Boolean>());
    public static ArrayBlockingQueue<String> queue = new ArrayBlockingQueue<>(5);
    @OnOpen
    public void onOpen(Session session) {
        userSessions.add(session);
    }

    @OnMessage
    public void onMessage(String message, Session session) throws IOException, InterruptedException {
        for(Session ses : userSessions){
            String msg = queue.take();
            ses.getBasicRemote().sendText(msg);
        }
        
    }

    @OnClose
    public void onClose(Session session) {
        userSessions.remove(session);
    }

    @OnError
    public void onError(Throwable t) {
        t.printStackTrace();
    }
}
