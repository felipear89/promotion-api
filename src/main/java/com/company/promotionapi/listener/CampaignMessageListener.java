package com.company.promotionapi.listener;

import com.company.promotionapi.model.Campaign;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.logging.Logger;

@Component
public class CampaignMessageListener {

    public final static String UPDATE_CAMPAIGN_MESSAGE_QUEUE = "UPDATE_CAMPAIGN_QUEUE";

    private static final Logger log = Logger.getLogger(CampaignMessageListener.class.getName());

    @RabbitListener(bindings = @QueueBinding(value = @Queue(exclusive = "true", durable = "false"),
            exchange = @Exchange(value = UPDATE_CAMPAIGN_MESSAGE_QUEUE, type = "fanout", durable = "true"))
    )
    public void receiveMessage(List<Campaign> campaigns) {
        log.info("Received");
        log.info("Message processed..."+ campaigns);
    }

}
