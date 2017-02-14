package com.quatrano.alex.sentiment_chat;

import org.json.JSONObject;

class SystemMessage extends Message {
    private static final String MESSAGE_TYPE = "system";

    SystemMessage(String body) {
        super(body);
    }

    @Override
    JSONObject toJson() {
        JSONObject obj = super.toJson();
        obj.put("messageType", MESSAGE_TYPE);
        return obj;
    }
}
