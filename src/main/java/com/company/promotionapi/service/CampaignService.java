package com.company.promotionapi.service;

import com.company.promotionapi.listener.CampaignMessageListener;
import com.company.promotionapi.model.Campaign;
import com.company.promotionapi.producer.CampaignMessageProducer;
import com.company.promotionapi.repository.CampaignRepository;
import com.company.promotionapi.rules.IncrementCampaignPeriod;
import com.company.promotionapi.rules.UpdatedCampaignObserver;
import org.apache.log4j.Logger;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

import static com.company.promotionapi.config.RabbitMQConfiguration.UPDATE_CAMPAIGN_MESSAGE_QUEUE;

@Service
public class CampaignService {

    private static final Logger log = Logger.getLogger(CampaignService.class);

    @Autowired
    private CampaignRepository campaignRepository;

    @Autowired
    private CampaignMessageProducer campaignMessageProducer;

    public void create(Campaign campaign) {

        List<Campaign> activeCampaignInPeriod = campaignRepository.getActiveCampaignInPeriod(campaign.getStart(), campaign.getEnd());

        UpdatedCampaignObserver observer = new UpdatedCampaignObserver();
        activeCampaignInPeriod.forEach(c -> c.addObserver(observer));
        activeCampaignInPeriod.add(campaign);
        new IncrementCampaignPeriod().incrementEndDate(activeCampaignInPeriod, campaign);

        List<Campaign> updatedCampaigns = observer.getUpdatedCampaigns();

        campaignRepository.insert(campaign);

        log.info("Campaign "+ campaign.getName() +" created");

        campaignRepository.updateBatch(updatedCampaigns);
        campaignMessageProducer.sendCampaignUpdateMessage(updatedCampaigns);

    }
}
