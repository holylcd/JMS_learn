package consumer;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

public class receiver {

    public static void main(String[] args) {
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("" +
                "tcp://192.168.12.100:61616");

        Connection connection = null;
        Session session = null;
        try {
            connection = connectionFactory.createConnection();
            connection.start();
            session = connection.createSession(Boolean.TRUE
                    , Session.AUTO_ACKNOWLEDGE);

            Destination destination = session.createQueue("test-queue");

            MessageConsumer consumer = session.createConsumer(destination);

            TextMessage receive = (TextMessage) consumer.receive();

            System.out.println("消费："+receive.getText());

            session.commit();

        } catch (JMSException e) {
            e.printStackTrace();
        }finally {
            try {
                if (null != connection){
                    connection.close();
                }
                if(null != session){
                    session.close();
                }
            } catch (JMSException e) {
                e.printStackTrace();
            }
        }


    }
}
