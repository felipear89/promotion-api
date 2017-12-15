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

        List<Campaign> campaigns = campaignRepository.list();

        UpdatedCampaignObserver observer = new UpdatedCampaignObserver();

        campaigns.stream().forEach(c -> c.addObserver(observer));

        new IncrementCampaignPeriod().incrementEndDate(campaigns, campaign);

        List<Campaign> updatedCampaigns = observer.getUpdatedCampaigns();

        campaignRepository.insert(campaign);

        

        campaignRepository.updateBatch(updatedCampaigns);
        campaignMessageProducer.sendCampaignUpdateMessage(updatedCampaigns);

    }
}
