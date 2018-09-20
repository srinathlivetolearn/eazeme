package com.yellp.services.impl;

import com.yellp.messaging.producers.RabbitmqMessageProducer;
import com.yellp.services.MessagePublisher;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.ExchangeBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RabbitmqMessagePublisher implements MessagePublisher {
    @Autowired
    private RabbitmqMessageProducer sender;

    @Override
    public boolean publishMessage(Object message,String senderId) {
        sender.send(message,buildExchange(senderId));
        return true;
    }

    private Exchange buildExchange(String senderId) {
        return ExchangeBuilder.directExchange(senderId).build();
    }

}
