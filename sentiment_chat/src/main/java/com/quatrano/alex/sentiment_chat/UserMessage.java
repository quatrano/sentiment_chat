package com.quatrano.alex.sentiment_chat;

import org.json.JSONObject;

class UserMessage extends Message {

    private final String sender;
    private static final String MESSAGE_TYPE = "user";

    UserMessage(String body, String sender) {
        super(body);
        this.sender = sender;
    }

    String getMessage() {
        return String.valueOf(this.body);
    }

    @Override
    JSONObject toJson() {
        JSONObject obj = super.toJson();
        obj.put("sender", sender)
            .put("messageType", MESSAGE_TYPE);
        return obj;
    }
}
