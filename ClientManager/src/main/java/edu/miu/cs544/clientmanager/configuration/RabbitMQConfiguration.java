package edu.miu.cs544.clientmanager.configuration;


import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class RabbitMQConfiguration {

    private final String HOST = "localhost";
    private final String USERNAME = "guest";
    private final String PASSWORD = "guest";

    public static final String QUEUE = "CLIENT_CREATED";
    private static final String EXCHANGE = "user.exchange";
    private static final String ROUTING_KEY = "user.routingKey";
    @Bean
    public Queue getClientQueue(){
        return new Queue(QUEUE, true);
    }

    @Bean
    public Exchange getExchange(){
        return ExchangeBuilder
                .directExchange(EXCHANGE)
                .durable(true)
                .build();
    }

    @Bean
    public Binding getBinding(){
        return BindingBuilder
                .bind(getClientQueue())
                .to(getExchange())
                .with(ROUTING_KEY)
                .noargs();
    }

    @Bean
    public CachingConnectionFactory connectionFactory() {
        CachingConnectionFactory factory = new CachingConnectionFactory(HOST);
        factory.setUsername(USERNAME);
        factory.setPassword(PASSWORD);
        return factory;
    }
    @Bean
    public MessageConverter jsonMessageConverter(){
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory){
        final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(jsonMessageConverter());
        return rabbitTemplate;
    }
}
