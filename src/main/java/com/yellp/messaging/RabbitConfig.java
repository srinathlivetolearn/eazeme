package com.yellp.messaging;


import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
public class RabbitConfig {
    private static final String EXCHANGE_NAME_DIRECT = "com.yellp.amq.direct";

    private static final String EXCHANGE_NAME_TOPIC = "com.yellp.amq.topic";

    @Bean
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public Exchange directExchange() {
        return new DirectExchange(EXCHANGE_NAME_DIRECT);
    }

    @Bean
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public Exchange topicExchange() {
        return new TopicExchange(EXCHANGE_NAME_TOPIC);
    }
}
