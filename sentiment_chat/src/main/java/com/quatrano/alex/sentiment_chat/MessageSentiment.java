package com.quatrano.alex.sentiment_chat;

import org.json.JSONObject;

import java.util.UUID;

class MessageSentiment extends Message {

    private static final String MESSAGE_TYPE = "sentiment";

    // number between 0 and 10 inclusive
    // 0 = negative, 10 = positive sentiment
    private final int sentiment;

    MessageSentiment(UserMessage um, int sentiment) {
        super(sentiment, um.getMessageId());
        this.sentiment = sentiment;
    }

    @Override
    JSONObject toJson() {
        JSONObject obj = super.toJson();
        obj.put("sentiment", sentiment)
                .put("messageType", MESSAGE_TYPE);
        return obj;
    }
}
