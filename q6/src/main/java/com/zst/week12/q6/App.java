package com.zst.week12.q6;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQQueue;
import org.apache.activemq.command.ActiveMQTopic;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.TextMessage;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;
import java.util.stream.IntStream;

public class App {
    public static void main(String[] args) throws JMSException {
        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://127.0.0.1:61616");
        MessageProducerManager messageProducerManager = new MessageProducerManager(connectionFactory);
        MessageConsumerManager messageConsumerManager = new MessageConsumerManager(connectionFactory);

        Destination topicDest = new ActiveMQTopic("topic.topic");
        Destination queueDest = new ActiveMQQueue("queue.queue");

        SessionMessageProducer queueProducer = messageProducerManager.createMessageProducer(queueDest);
        SessionMessageProducer topicProducer = messageProducerManager.createMessageProducer(topicDest);
        messageConsumerManager.addListener(topicDest, (msg) -> {
            try {
                TextMessage textMsg = (TextMessage) msg;
                System.out.println("Topic message listener 1 receive = " + textMsg.getText());
            } catch (JMSException e) {
                throw new RuntimeException(e);
            }
        });
        messageConsumerManager.addListener(topicDest, (msg) -> {
            try {
                TextMessage textMsg = (TextMessage) msg;
                System.out.println("Topic message listener 2 receive = " + textMsg.getText());
            } catch (JMSException e) {
                throw new RuntimeException(e);
            }
        });
        messageConsumerManager.addListener(queueDest, (msg) -> {
            try {
                TextMessage textMsg = (TextMessage) msg;
                System.out.println("Queue message listener receive = " + textMsg.getText());
            } catch (JMSException e) {
                throw new RuntimeException(e);
            }
        });

        IntStream.range(0, 100).forEach(i -> {
            try {
                queueProducer.sendTextMessage("Queue message No." + i);
                topicProducer.sendTextMessage("Topic message No." + i);
            } catch (JMSException e) {
                e.printStackTrace();
            }
        });

        LockSupport.parkNanos(TimeUnit.SECONDS.toNanos(5));
        messageConsumerManager.closeConnection();
        messageProducerManager.closeConnection();
    }
}
