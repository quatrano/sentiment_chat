package com.quatrano.alex.sentiment_chat;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Chat {

    private final SentimentService sentimentService;
    private final Thread sentimentServiceThread;
    private final Map<String, ChatSocket> usernameSocketMap = new ConcurrentHashMap<>();
    private int nextUserNumber = 1; // for generating human readable user names

    private Chat() {
        sentimentService = new SentimentService(this);
        sentimentServiceThread = new Thread(sentimentService);
        sentimentServiceThread.start();
    }

    String connectSession(ChatSocket socket) {
        String username = ("User" + nextUserNumber++);
        usernameSocketMap.put(username, socket);
        broadcastMessageSync(new SystemMessage(username + " joined the chat"));
        return username;
    }

    void disconnectUser(String username) {
        usernameSocketMap.remove(username);
        broadcastMessageSync(new SystemMessage(username + " left the chat"));
    }

    void userSays(String username, String message) {
        UserMessage um = new UserMessage(message, username);
        try {
            sentimentService.submitUserMessage(um);
        } catch (Exception e) {
            e.printStackTrace();
        }

        broadcastMessageSync(um);
    }

    // only one message can be sent at a time via "broadcastMessageSync"
    // prevent messages from arriving at different clients in a different order
    synchronized void broadcastMessageSync(Message m) {
        String stringMessage = String.valueOf(m.toJson().put("userList", usernameSocketMap.keySet()));
        usernameSocketMap.values().stream()
                .filter(ChatSocket::sessionIsOpen)
                .forEach(s -> s.sendString(stringMessage));
    }

    void broadcastMessage(Message m) {
        String stringMessage = String.valueOf(m.toJson().put("userList", usernameSocketMap.keySet()));
        usernameSocketMap.values().stream()
                .filter(ChatSocket::sessionIsOpen)
                .forEach(s -> s.sendString(stringMessage));
    }

    private static Chat instance;
    static Chat getInstance() {
        if (instance == null) {
            instance = new Chat();
        }
        return instance;
    }
    public static void reset() {
        if (instance != null && instance.sentimentServiceThread.isAlive()) {
            instance.sentimentServiceThread.interrupt();
        }
        instance = null;
    }
}
