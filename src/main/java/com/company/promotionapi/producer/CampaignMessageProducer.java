package com.company.promotionapi.producer;

import com.company.promotionapi.model.Campaign;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.company.promotionapi.listener.CampaignMessageListener.UPDATE_CAMPAIGN_MESSAGE_EXCHANGE;
import static java.util.stream.Collectors.toList;
import static org.springframework.util.CollectionUtils.isEmpty;

@Component
public class CampaignMessageProducer {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    private static final org.slf4j.Logger log = LoggerFactory.getLogger(CampaignMessageProducer.class);

    public void sendCampaignUpdateMessage(List<Campaign> campaigns) {
        if (!isEmpty(campaigns)) {

            List<String> ids = campaigns.stream().map(Campaign::getId).collect(toList());

            log.info("Sending updated campaigns: {}", ids);

            try {
                String json = objectMapper.writeValueAsString(campaigns);
                rabbitTemplate.convertAndSend(UPDATE_CAMPAIGN_MESSAGE_EXCHANGE, null, json);

                log.info("Message has been sent");

            } catch (java.io.IOException e) {
                log.error("Fail :( ", e);
            }

        }
    }
}
