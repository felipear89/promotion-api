package com.company.promotionapi.listener;

import com.company.promotionapi.model.Campaign;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Component
public class CampaignMessageListener {

    public final static String UPDATE_CAMPAIGN_MESSAGE_QUEUE = "UPDATE_CAMPAIGN_QUEUE";
    public final static String UPDATE_CAMPAIGN_MESSAGE_EXCHANGE = "UPDATE_CAMPAIGN_MESSAGE_EXCHANGE";

    private static final Logger log = LoggerFactory.getLogger(CampaignMessageListener.class);

    @Autowired
    private ObjectMapper objectMapper;

    @RabbitListener(
            bindings = @QueueBinding(value = @Queue(exclusive = "false", durable = "true", value = UPDATE_CAMPAIGN_MESSAGE_QUEUE),
            exchange = @Exchange(value = UPDATE_CAMPAIGN_MESSAGE_EXCHANGE, type = "fanout", durable = "true"))
    )
    public void receiveMessage(Message message) {
        log.info("Message from {} received", UPDATE_CAMPAIGN_MESSAGE_QUEUE);
        try {
            List<Campaign> campaigns = objectMapper.readValue(message.getBody(), new TypeReference<List<Campaign>>(){});
            List<String> ids = campaigns.stream().map(c -> c.getId()).collect(toList());
            log.info("Message processed... {}", ids);
        } catch (IOException e) {
            log.error("Fail to read message", e);
        }
    }

}
