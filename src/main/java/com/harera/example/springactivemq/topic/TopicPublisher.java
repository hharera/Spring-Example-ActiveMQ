package com.harera.example.springactivemq.topic;

import jakarta.annotation.PostConstruct;

import javax.jms.Connection;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.stereotype.Service;


@Service
public class TopicPublisher {

    private final TopicConsumer_1 topicConsumer_1;
    private final TopicConsumer_2 topicConsumer_2;

    public TopicPublisher(TopicConsumer_1 topicConsumer1, TopicConsumer_2 topicConsumer2) {
        topicConsumer_1 = topicConsumer1;
        topicConsumer_2 = topicConsumer2;
    }


    @PostConstruct
    public void publish() {
        try {
            // Create a connection factory
            ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory("tcp://localhost:61616");
            // Create a connection
            Connection connection = factory.createConnection();
            connection.start();
            // Create a session
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            // Create a destination (topic)
            Destination destination = session.createTopic("TestTopic");
            // Create a producer
            MessageProducer producer = session.createProducer(destination);
            // Create a message
            TextMessage message = session.createTextMessage("Hello, World!");
            // Send the message
            producer.send(message);
            // Close the connection
            connection.close();

            topicConsumer_1.consume();
            topicConsumer_2.consume();
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}
