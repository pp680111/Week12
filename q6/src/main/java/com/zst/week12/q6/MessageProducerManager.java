package com.zst.week12.q6;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.Destination;
import javax.jms.MessageProducer;
import javax.jms.Session;

public class MessageProducerManager extends AbstractMessageOperatorManager{
    public MessageProducerManager(ActiveMQConnectionFactory connectionFactory) {
        super(connectionFactory);
    }

    public SessionMessageProducer createMessageProducer(Destination destination) {
        try {
            ActiveMQConnection conn = getConnection();
            // 这里session的关闭是一个问题，最好封装一下MessageProducer，把Session放进里面，关闭时顺带关闭session
            Session session = conn.createSession(false, Session.AUTO_ACKNOWLEDGE);
            MessageProducer producer = session.createProducer(destination);
            return new SessionMessageProducer(producer, session);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
