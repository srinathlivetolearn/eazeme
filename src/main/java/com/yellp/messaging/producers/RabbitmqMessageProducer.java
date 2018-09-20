package com.yellp.messaging.producers;

import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RabbitmqMessageProducer {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void send(Object message,Exchange exchange) {
        rabbitTemplate.convertAndSend(exchange.getName(),"",message);
    }
}
