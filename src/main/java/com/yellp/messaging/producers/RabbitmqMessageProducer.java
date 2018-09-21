package com.yellp.messaging.producers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import static org.springframework.amqp.core.MessageProperties.CONTENT_TYPE_JSON;

@Component
public class RabbitmqMessageProducer {
    private static final Logger LOGGER = LoggerFactory.getLogger(RabbitmqMessageProducer.class);

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    @Qualifier("topicExchange")
    private Exchange exchange;

    public void send(Object message,String routingKey) {
        try {
            rabbitTemplate.convertAndSend(exchange.getName(), routingKey, getAsJson(message),
                    msg ->
                    {
                        msg.getMessageProperties().setContentType(CONTENT_TYPE_JSON);
                        return msg;
                    });
        } catch (JsonProcessingException e) {
            LOGGER.error("Error converting message to json: ",e);
            throw new RuntimeException(e);
        }
    }

    private String getAsJson(Object object) throws JsonProcessingException {
        return new ObjectMapper().writeValueAsString(object);
    }
}
