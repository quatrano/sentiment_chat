package com.quatrano.alex.sentiment_chat;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class SentimentService implements Runnable {

    private Chat chat;
    private ExecutorService executorService;

    private Lock workQueueLock = new ReentrantLock();
    private Queue<Future<MessageSentiment>> workQueue = new LinkedList<>();

    SentimentService(Chat c) {
        chat = c;

        // max 6 concurrent requests with the third-party NLP service
        int maxConcurrentRequests = 6;
        executorService = Executors.newFixedThreadPool(maxConcurrentRequests);
    }

    @Override
    public void run() {
        boolean toContinue = true;
        while (toContinue) {
            long startTime = System.nanoTime();
            Future<MessageSentiment> work;

            workQueueLock.lock();
            work = workQueue.poll();
            workQueueLock.unlock();

            if (work != null) {
                if (work.isDone()) {
                    try {
                        MessageSentiment ms = work.get();
                        chat.broadcastMessage(ms);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    workQueueLock.lock();
                    workQueue.offer(work);
                    workQueueLock.unlock();
                }
            }
            try {

                // check a future for completion every 1s = 1,000 ms
                // or slower if it takes longer
                //     and in that case wait at least 100 ms between checks
                Thread.sleep(Math.max(100, 1000 - (System.nanoTime() - startTime) / 1000000));
            } catch (Exception e) {
                toContinue = false;
            }
        }
    }

    void submitUserMessage(UserMessage um) throws Exception {
        SentimentWorker w = new SentimentWorker(um);
        Future<MessageSentiment> work = executorService.submit(w);

        workQueueLock.lock();
        workQueue.add(work);
        workQueueLock.unlock();
    }
}
