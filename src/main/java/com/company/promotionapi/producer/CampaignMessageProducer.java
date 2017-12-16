package com.company.promotionapi.producer;

import com.company.promotionapi.model.Campaign;
import com.company.promotionapi.service.CampaignService;
import org.apache.log4j.Logger;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.List;

import static com.company.promotionapi.config.RabbitMQConfiguration.UPDATE_CAMPAIGN_MESSAGE_QUEUE;
import static java.util.stream.Collectors.toList;
import static org.springframework.util.CollectionUtils.isEmpty;

@Component
public class CampaignMessageProducer {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    private static final Logger log = Logger.getLogger(CampaignMessageProducer.class);

    public void sendCampaignUpdateMessage(List<Campaign> campaigns) {
        if (!isEmpty(campaigns)) {
            List<String> ids = campaigns.stream().map(c -> c.getId()).collect(toList());
            log.info("Sending updated campaigns: " + ids);
            rabbitTemplate.convertAndSend(UPDATE_CAMPAIGN_MESSAGE_QUEUE, campaigns);
        }
    }
}
