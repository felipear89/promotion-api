package com.company.promotionapi.rules;

import com.company.promotionapi.model.Campaign;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

public class UpdatedCampaignObserver implements Observer {

    private List<Campaign> updatedCampaign;

    public UpdatedCampaignObserver() {
        this.updatedCampaign = new ArrayList<>();
    }

    @Override
    public void update(Observable o, Object arg) {
        Campaign c = (Campaign) arg;
        updatedCampaign.add(c);
    }

    public List<Campaign> getUpdatedCampaigns() {
        return updatedCampaign;
    }
}
