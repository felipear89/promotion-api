package com.company.promotionapi.producer;

import com.company.promotionapi.model.Campaign;
import com.company.promotionapi.service.CampaignService;
import org.apache.log4j.Logger;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.company.promotionapi.config.RabbitMQConfiguration.UPDATE_CAMPAIGN_MESSAGE_QUEUE;

@Component
public class CampaignMessageProducer {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    private static final Logger log = Logger.getLogger(CampaignMessageProducer.class);

    public void sendCampaignUpdateMessage(List<Campaign> campaigns) {
        log.info("Sending updated campaigns");
        rabbitTemplate.convertAndSend(UPDATE_CAMPAIGN_MESSAGE_QUEUE, campaigns);
    }
}
