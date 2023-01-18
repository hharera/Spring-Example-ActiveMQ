package com.harera.example.springactivemq.queue;

import jakarta.annotation.PostConstruct;

import javax.jms.*;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.stereotype.Service;


@Service
public class QueuePublisher {


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
            // Create a destination (queue)
            Destination destination = session.createQueue("TestQueue");
            // Create a producer
            MessageProducer producer = session.createProducer(destination);
            // Create a message
            TextMessage message = session.createTextMessage("Hello, World!");
            // Send the message
            producer.send(message);
            // Close the connection
            connection.close();
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}
