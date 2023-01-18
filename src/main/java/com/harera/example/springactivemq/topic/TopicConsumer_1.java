package com.harera.example.springactivemq.topic;

import jakarta.annotation.PostConstruct;

import javax.jms.Connection;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Service;


@Service
public class TopicConsumer_1 {


    @Autowired
    private ActiveMQConnectionFactory factory;

    public void consume() {
        try {
            Connection connection = factory.createConnection();
            connection.start();
            // Create a session
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            // Create a destination (topic)
            Destination destination = session.createTopic("TestTopic");
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


    @JmsListener( destination = "${activemq.topic-name}", subscription = "assinatura", selector = "test=false OR test is null")
    public void listen(String message) {
        System.out.println("Received" + message);
    }

}
