package edu.miu.cs544.clientmanager.configuration;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import static edu.miu.cs544.clientmanager.configuration.RabbitMQConfiguration.DLX_QUEUE_MESSAGES;

@Slf4j
@Component
public class DlxContainer {


    @RabbitListener(queues = DLX_QUEUE_MESSAGES)
    public void processFailedMessages(Message message) {
        log.info("Received failed message: {}", message.toString());
    }
}
