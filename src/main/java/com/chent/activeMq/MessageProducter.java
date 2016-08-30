package com.chent.activeMq;

import org.apache.activemq.*;
import javax.jms.*;
import java.sql.Timestamp;

/**
 * Created by wangqingbin on 2016/8/30.
 * 消息生产者
 */
public class MessageProducter {

    public static void main (String ... args) throws Exception{
        //ConnectionFactory ：连接工厂，JMS 用它创建连接
        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(
                ActiveMQConnection.DEFAULT_USER,
                ActiveMQConnection.DEFAULT_PASSWORD,
                "tcp://localhost:61616");

        connectionFactory.setTrustAllPackages(true);
        //JMS 客户端到JMS Provider 的连接
        Connection connection = connectionFactory.createConnection();
        connection.start();
        // Session： 一个发送或接收消息的线程
        Session session = connection.createSession(Boolean.TRUE, Session.AUTO_ACKNOWLEDGE);
        // Destination ：消息的目的地;消息发送给谁.
        // 获取session注意参数值my-queue是Query的名字
        Destination destination = session.createQueue("my-queue-2");
        // MessageProducer：消息生产者
        MessageProducer producer = session.createProducer(destination);
        //设置不持久化
        producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
        //发送一条消息
        sendMsg(session, producer);
        sendMsg1(session, producer);
        session.commit();
        connection.close();
    }

    public static void sendMsg(Session session, MessageProducer producer) throws JMSException {
        //创建一条文本消息
        //ObjectMessage message = session.createObjectMessage(new Timestamp(System.currentTimeMillis()));
        for(int i=0;i<10;i++){
            TextMessage textMessage = session.createTextMessage("message");
            //通过消息生产者发出消息
            System.out.println("发送的消息="+textMessage.getText());
            producer.send(textMessage);
        }

    }
    public static void sendMsg1(Session session, MessageProducer producer) throws JMSException {
        UserInfo userInfo;

        for(int i=0;i<10;i++){
            userInfo = new UserInfo();
            userInfo.setJid(100+i);
            userInfo.setUserName("sige"+i);
            ObjectMessage message = session.createObjectMessage(userInfo);

            //通过消息生产者发出消息
            UserInfo userInfo1 = (UserInfo)message.getObject();
            System.out.println("发送的object消息"+userInfo1.getJid()+"&&"+userInfo1.getUserName());
            producer.send(message);
        }
    }
}
