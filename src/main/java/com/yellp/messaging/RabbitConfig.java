package com.yellp.messaging;


import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Exchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {
    private static final String EXCHANGE_NAME = "com.yellp.amq.direct";

    @Bean
    public Exchange directExchange() {
        return new DirectExchange(EXCHANGE_NAME);
    }
}
