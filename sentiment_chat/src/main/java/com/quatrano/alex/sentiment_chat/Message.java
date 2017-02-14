package com.quatrano.alex.sentiment_chat;

import org.json.JSONObject;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

abstract class Message {

    final Object body;
    private final LocalDateTime timestamp;
    private final UUID messageId;

    Message(Object body) {
        this(body,  UUID.randomUUID());
    }

    Message(Object body, UUID messageId) {
        this.body = body;
        this.timestamp = LocalDateTime.now();
        this.messageId = messageId;
    }

    Object getMessage() {
        return body;
    }

    UUID getMessageId() {
        return messageId;
    }

    JSONObject toJson() {
        return new JSONObject()
                .put("body", String.valueOf(body))
                .put("timestamp", timestamp.format(DateTimeFormatter.ofPattern("hh:mm:ss a")))
                .put("messageId", messageId);
    }
}
