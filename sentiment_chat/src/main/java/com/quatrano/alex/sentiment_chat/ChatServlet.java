package com.quatrano.alex.sentiment_chat;

import org.eclipse.jetty.websocket.servlet.WebSocketServlet;
import org.eclipse.jetty.websocket.servlet.WebSocketServletFactory;

import javax.servlet.annotation.WebServlet;

@WebServlet(name = "chat", urlPatterns = {"/chat"})
public class ChatServlet extends WebSocketServlet
{

    @Override
    public void configure(WebSocketServletFactory webSocketServletFactory) {
        webSocketServletFactory.register(ChatSocket.class);
    }
}