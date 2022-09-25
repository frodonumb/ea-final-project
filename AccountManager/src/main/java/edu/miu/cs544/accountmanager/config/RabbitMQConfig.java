package edu.miu.cs544.accountmanager.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Declarables;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.ExchangeBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Configuration
public class RabbitMQConfig {

    public static final String CLIENT_CREATED_QUEUE = "CLIENT_CREATED";
    public static final String CLIENT_TRANSACTION_QUEUE = "CLIENT_TRANSACTION";

    public static final String DLX_QUEUE_MESSAGES = "QUEUE_MESSAGES_DLQ";
    public static final String DLX_EXCHANGE = "DLX_EXCHANGE_MESSAGES" + ".dlx";
    public static final String DLX_ROUTE = "DLX_ROUTE";

    @Value("${spring.rabbitmq.template.exchange}")
    private String exchange;

    @Value("${spring.rabbitmq.template.routing-key}")
    private String routingKey;

    @Value("${spring.rabbitmq.username}")
    private String username;

    @Value("${spring.rabbitmq.password}")
    private String password;

    @Value("${spring.rabbitmq.host}")
    private String host;

    @Bean
    public Declarables dlxAndDlqConfig() {
        DirectExchange clientExchange = new DirectExchange(exchange, true, false);

        Queue clientCreatedQueue = new Queue(CLIENT_CREATED_QUEUE, true, false, false,
                Map.of(
                        "x-dead-letter-exchange", DLX_EXCHANGE,
                        "x-dead-letter-routing-key", DLX_ROUTE
                ));

        Queue clientTransactionQueue = new Queue(CLIENT_TRANSACTION_QUEUE, true, false, false,
                Map.of(
                        "x-dead-letter-exchange", DLX_EXCHANGE,
                        "x-dead-letter-routing-key", DLX_ROUTE
                ));

        Queue deadLetterQueue = new Queue(DLX_QUEUE_MESSAGES, true);
        DirectExchange deadLetterExchange = new DirectExchange(DLX_EXCHANGE, true, false);

        return new Declarables(
                clientExchange,
                clientCreatedQueue,
                BindingBuilder.bind(clientCreatedQueue).to(clientExchange).with(CLIENT_CREATED_QUEUE),
                clientTransactionQueue,
                BindingBuilder.bind(clientTransactionQueue).to(clientExchange).with(CLIENT_TRANSACTION_QUEUE),
                deadLetterExchange,
                deadLetterQueue,
                BindingBuilder.bind(deadLetterQueue).to(deadLetterExchange).with(DLX_ROUTE)
        );
    }

    @Bean
    CachingConnectionFactory connectionFactory() {
        CachingConnectionFactory factory = new CachingConnectionFactory(host);
        factory.setUsername(username);
        factory.setPassword(password);
        return factory;
    }

    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory factory) {
        final RabbitTemplate rabbitTemplate = new RabbitTemplate(factory);
        rabbitTemplate.setMessageConverter(jsonMessageConverter());
        return rabbitTemplate;
    }
}
