package com.zst.week12.q6;

import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;

public class SessionMessageProducer {
    private MessageProducer producer;
    private Session session;

    public SessionMessageProducer (MessageProducer producer, Session session) {
        this.producer = producer;
        this.session = session;
    }

    /**
     * 由于JMS的API设计，消息的创建是与Session绑定的，因此在创建消息时必须通过session对象创建，所以创建了此类用于持有session对象
     * 而其他的一些MQ中间件，如RabbitMQ和RocketMQ都是可以独立的创建消息的，所以为什么JMS要这么设计？
     * 网上搜了一下，基本上没有什么文章说明，只在StackOverFlow上看到了这篇问答
     * {@link https://stackoverflow.com/questions/8475840/why-is-createmessage-method-on-the-session-interface-in-jms}
     * 问题的回答者的给出的解释大致如下：
     * 是Message对象需要通过Session发送，如果那么自然Message中就需要携带一些session的metadata，
     * 通过session接口创建的对象直接携带这些信息，与独立创建Message对象不持有Session的metadata并通过Session发送是一样的。
     * 如果一个Message对象可以独立创建又不发送出去，那么自然没有必要持有Session的metadata，但是不发送的消息又有什么意义？
     *
     * 这算是一个比较具有说服力的思路吧
     * @param text
     * @throws JMSException
     */
    public void sendTextMessage(String text) throws JMSException {
        producer.send(session.createTextMessage(text));
    }
}
