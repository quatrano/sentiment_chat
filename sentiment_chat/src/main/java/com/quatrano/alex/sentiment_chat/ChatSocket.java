package com.quatrano.alex.sentiment_chat;

import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.WebSocketListener;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;

@WebSocket()
public class ChatSocket implements WebSocketListener {

    private Chat chat = Chat.getInstance();
    private Session session;
    private String username;

    @Override
    public void onWebSocketText(String message) {
        chat.userSays(username, message);
    }

    @Override
    public void onWebSocketClose(int i, String s) {
        chat.disconnectUser(username);
    }

    @Override
    public void onWebSocketConnect(Session session) {
        this.session = session;
        username = chat.connectSession(this);
    }

    @Override
    public void onWebSocketBinary(byte[] bytes, int i, int i1) {}

    @Override
    public void onWebSocketError(Throwable throwable) {}

    boolean sessionIsOpen() {
        return session.isOpen();
    }

    // only one string can be sent on a socket at a time
    synchronized void sendString(String s) {
        try {
            session.getRemote().sendString(s);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}