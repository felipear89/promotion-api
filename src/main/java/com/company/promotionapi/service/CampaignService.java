package com.company.promotionapi.service;

import com.company.promotionapi.model.Campaign;
import com.company.promotionapi.producer.CampaignMessageProducer;
import com.company.promotionapi.repository.CampaignRepository;
import com.company.promotionapi.rules.IncrementCampaignPeriod;
import com.company.promotionapi.rules.UpdatedCampaignObserver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.Arrays.asList;

@Service
public class CampaignService {

    private static final Logger log = LoggerFactory.getLogger(CampaignService.class);

    @Autowired
    private CampaignRepository campaignRepository;

    @Autowired
    private CampaignMessageProducer campaignMessageProducer;

    public Campaign create(Campaign campaign) {

        List<Campaign> activeCampaignInPeriod = campaignRepository.getActiveCampaignInPeriod(campaign.getStart(), campaign.getEnd());

        UpdatedCampaignObserver observer = new UpdatedCampaignObserver();
        activeCampaignInPeriod.forEach(c -> c.addObserver(observer));
        activeCampaignInPeriod.add(campaign);
        new IncrementCampaignPeriod().incrementEndDate(activeCampaignInPeriod, campaign);

        List<Campaign> updatedCampaigns = observer.getUpdatedCampaigns();

        campaign = campaignRepository.insert(campaign);

        log.info("Campaign "+ campaign.getName() +" created");

        campaignRepository.updateBatch(updatedCampaigns);
        campaignMessageProducer.sendCampaignUpdateMessage(updatedCampaigns);

        return campaign;
    }

    public boolean update(Campaign campaign) {
        int updated = campaignRepository.update(campaign);
        campaignMessageProducer.sendCampaignUpdateMessage(asList(campaign));
        return updated > 0 ? true : false;
    }
}
