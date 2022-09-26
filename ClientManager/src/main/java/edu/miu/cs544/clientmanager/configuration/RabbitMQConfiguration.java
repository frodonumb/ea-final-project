package edu.miu.cs544.clientmanager.configuration;

import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Declarables;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
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
@EnableRabbit
public class RabbitMQConfiguration {

    public static final String CLIENT_CREATED_QUEUE = "CLIENT_CREATED";
    public static final String CLIENT_TRANSACTION_QUEUE = "CLIENT_TRANSACTION";

    public static final String DLX_QUEUE_MESSAGES = "QUEUE_MESSAGES_DLQ";
    public static final String DLX_EXCHANGE = "DLX_EXCHANGE_MESSAGES" + ".dlx";
    public static final String DLX_ROUTE = "DLX_ROUTE";

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
    public Declarables dlxAndDlqConfig() {
        DirectExchange clientExchange = new DirectExchange(exchange, true, false);

        Queue clientCreatedQueue = new Queue(CLIENT_CREATED_QUEUE, true, false, false,
                Map.of(
                        "x-dead-letter-exchange", DLX_EXCHANGE,
                        "x-dead-letter-routing-key", DLX_ROUTE
                ));

        Queue deadLetterQueue = new Queue(DLX_QUEUE_MESSAGES, true);
        DirectExchange deadLetterExchange = new DirectExchange(DLX_EXCHANGE, true, false);

        Queue transactionSuccessQueue = QueueBuilder.durable("CLIENT_TRANSACTION_SUCCESS").build();
        Queue transactionFailedQueue = QueueBuilder.durable("CLIENT_TRANSACTION_FAILED").build();

        return new Declarables(
                clientExchange,
                clientCreatedQueue,
                BindingBuilder.bind(clientCreatedQueue).to(clientExchange).with(CLIENT_CREATED_QUEUE),
                deadLetterExchange,
                deadLetterQueue,
                BindingBuilder.bind(deadLetterQueue).to(deadLetterExchange).with(DLX_ROUTE),
                transactionSuccessQueue,
                BindingBuilder.bind(transactionSuccessQueue).to(clientExchange).with("CLIENT_TRANSACTION_SUCCESS"),
                transactionFailedQueue,
                BindingBuilder.bind(transactionFailedQueue).to(clientExchange).with("CLIENT_TRANSACTION_FAILED")
        );
    }

    @Bean
    public CachingConnectionFactory connectionFactory() {
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
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(jsonMessageConverter());
        return rabbitTemplate;
    }
}
