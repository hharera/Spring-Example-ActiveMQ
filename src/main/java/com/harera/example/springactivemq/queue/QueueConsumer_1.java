package com.harera.example.springactivemq.queue;

import jakarta.annotation.PostConstruct;

import javax.jms.Connection;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.stereotype.Service;

@Service
public class QueueConsumer_1 {

    @PostConstruct
    public void consume() {
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
            // Create a consumer
            MessageConsumer consumer = session.createConsumer(destination);
            // Start receiving messages
            Message message = consumer.receive();
            while (message != null) {
                // Do something with the message
                System.out.println("Received: " + ((TextMessage) message).getText());
                message = consumer.receive();
            }
            // Close the connection
            connection.close();
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}
