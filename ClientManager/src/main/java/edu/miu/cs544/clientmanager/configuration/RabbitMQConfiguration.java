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
    @Value("guest")
    private String username;
    @Value("guest")
    private String password;
    @Value("localhost")
    private String host;

    @Value("user.routingkey")
    private String routingKey;
    @Value("user.exchange")
    private String exchange;

    @Bean
    public Queue getQueue(){
        return new Queue("CLIENT_CREATED", true);
    }

    @Bean
    public Exchange getExchange(){
        return ExchangeBuilder.directExchange(exchange).durable(true).build();
    }

    @Bean
    public Binding getBinding(){
        return BindingBuilder.bind(getQueue()).to(getExchange()).with(routingKey).noargs();
    }

    @Bean
    public CachingConnectionFactory connectionFactory() {
        CachingConnectionFactory factory = new CachingConnectionFactory(host);
        factory.setUsername(username);
        factory.setPassword(password);
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
