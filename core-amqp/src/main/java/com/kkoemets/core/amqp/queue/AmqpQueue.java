package com.kkoemets.core.amqp.queue;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kkoemets.core.amqp.message.AmqpMessage;
import com.kkoemets.core.amqp.service.AddQueueMessageDto;
import com.kkoemets.core.amqp.service.QueueMessageService;
import org.slf4j.Logger;
import org.slf4j.MDC;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import static java.time.LocalDateTime.now;
import static org.slf4j.LoggerFactory.getLogger;

public abstract class AmqpQueue<C extends AmqpMessage> {
    private static final Logger log = getLogger(AmqpQueue.class);

    @Autowired
    private RabbitTemplate template;
    @Autowired
    private QueueMessageService queueMessages;
    @Autowired
    private ObjectMapper objectMapper;

    abstract String getQueueName();

    @Transactional
    public void send(C message) {
        String requestId = MDC.get("requestId");
        String messageId = "%s-%s".formatted(getQueueName(), requestId);

        AddQueueMessageDto dto = new AddQueueMessageDto();
        dto.setQueueName(getQueueName());
        dto.setMessage(stringify(message));
        dto.setExpiration(now().plusHours(3));
        dto.setMessageId(messageId);
        queueMessages.add(dto);

        template.convertAndSend(getQueueName(), message, m -> {
            m.getMessageProperties().setCorrelationId(messageId);
            return m;
        });

        log.info("Sent message to queue-{}", getQueueName());
    }

    private String stringify(C message) {
        try {
            return objectMapper.writeValueAsString(message);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

}
