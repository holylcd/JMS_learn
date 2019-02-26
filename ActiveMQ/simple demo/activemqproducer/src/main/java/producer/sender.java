package producer;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

public class sender {

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

            MessageProducer producer = session.createProducer(destination);

            TextMessage text = session.createTextMessage("text");
            System.out.println("生产："+text.getText());

            producer.send(text);

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
