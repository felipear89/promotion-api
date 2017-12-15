package com.company.promotionapi.service;

import com.company.promotionapi.model.Campaign;
import com.company.promotionapi.repository.CampaignRepository;
import com.company.promotionapi.rules.IncrementCampaignPeriod;
import com.company.promotionapi.rules.UpdatedCampaignObserver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CampaignService {

    @Autowired
    private CampaignRepository campaignRepository;

    public void create(Campaign campaign) {

        List<Campaign> campaigns = campaignRepository.list();

        UpdatedCampaignObserver observer = new UpdatedCampaignObserver();

        campaigns.stream().forEach(c -> c.addObserver(observer));

        new IncrementCampaignPeriod().incrementEndDate(campaigns, campaign);

        List<Campaign> updatedCampaigns = observer.getUpdatedCampaigns();

        campaignRepository.insert(campaign);

        campaignRepository.updateBatch(updatedCampaigns);

    }
}
