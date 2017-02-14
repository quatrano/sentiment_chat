package com.quatrano.alex.sentiment_chat;

import java.util.concurrent.Callable;
import io.indico.Indico;

class SentimentWorker implements Callable<MessageSentiment> {

    private final UserMessage userMessage;
    private final Indico indico = IndicoProvider.getIndico();
    SentimentWorker(UserMessage um) throws Exception{
        this.userMessage = um;
    }

    @Override
    public MessageSentiment call() throws Exception {
        int sentimentInt;
        if (indico != null) {
            Double sentiment = indico.sentiment.predict(userMessage.getMessage()).getSentiment();
            sentimentInt = (int) (sentiment * 10);
        } else {
            sentimentInt = -1;
        }
        return new MessageSentiment(userMessage, sentimentInt);
    }
}