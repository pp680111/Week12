package com.zst.week12.q6;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.JMSException;

public abstract class AbstractMessageOperatorManager {
    private ActiveMQConnectionFactory connectionFactory;
    private ActiveMQConnection conn;

    public AbstractMessageOperatorManager(ActiveMQConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    protected ActiveMQConnection getConnection() throws JMSException {
        // 此处如果考虑并发使用的情况，可以添加一些并发控制代码
        if (conn == null || conn.isClosed()) {
            conn = (ActiveMQConnection) connectionFactory.createConnection();
            conn.start();
        }
        return this.conn;
    }

    protected void closeConnection() throws JMSException {
        if (conn != null) {
            conn.close();
        }
    }
}
