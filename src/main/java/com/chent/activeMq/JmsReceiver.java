package com.chent.activeMq;
import org.apache.activemq.*;
import javax.jms.*;
import javax.jms.Message;
import java.sql.Timestamp;

/**
 * Created by wangqingbin on 2016/8/30.
 */
public class JmsReceiver {
    public static void main(String ... args) throws Exception{

        // ConnectionFactory ：连接工厂，JMS 用它创建连接
        ActiveMQConnectionFactory connectionFactory =
                new ActiveMQConnectionFactory(ActiveMQConnection.DEFAULT_USER, ActiveMQConnection.DEFAULT_PASSWORD,
                        "tcp://localhost:61616");

        connectionFactory.setTrustAllPackages(true);
        //JMS 客户端到JMS Provider 的连接
        Connection connection = connectionFactory.createConnection();
        connection.start();
        // Session： 一个发送或接收消息的线程
        Session session = connection.createSession(Boolean.TRUE, Session.AUTO_ACKNOWLEDGE);
        // Destination ：消息的目的地;消息发送给谁.
        Destination destination = session.createQueue("my-queue-1");
        // 消费者，消息接收者
        MessageConsumer consumer = session.createConsumer(destination);
        while (true)
        {
            Message message = consumer.receive(100000);
            if (null != message){
                if(message instanceof ObjectMessage){
                    ObjectMessage objectMessage = ((ObjectMessage) message);
                    UserInfo userInfo = (UserInfo)objectMessage.getObject();
                    System.out.println("收到object消息：" + userInfo.getJid()+"&&"+userInfo.getUserName());
                }else if(message instanceof TextMessage){
                    TextMessage textMessage = (TextMessage) message;
                    System.out.println("收到的文本消息："+textMessage.getText());
                }

            }
            else
                break;
        }
        session.close();
        connection.close();

    }
}
