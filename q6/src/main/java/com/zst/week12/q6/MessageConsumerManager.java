package com.zst.week12.q6;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.Session;

public class MessageConsumerManager extends AbstractMessageOperatorManager {
    public MessageConsumerManager(ActiveMQConnectionFactory connectionFactory) {
        super(connectionFactory);
    }

    public void addListener(Destination destination, MessageListener listener) throws JMSException {
        ActiveMQConnection conn = getConnection();
        // 这里session的关闭是一个问题，最好封装一下MessageConsumer，把Session放进里面，关闭时顺带关闭session
        Session session = conn.createSession(false, Session.AUTO_ACKNOWLEDGE);
        MessageConsumer consumer = session.createConsumer(destination);
        consumer.setMessageListener(listener);
    }
}
