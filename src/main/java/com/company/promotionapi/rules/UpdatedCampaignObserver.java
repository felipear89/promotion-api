package com.company.promotionapi.rules;

import com.company.promotionapi.model.Campaign;

import java.util.*;

public class UpdatedCampaignObserver implements Observer {

    private Set<Campaign> updatedCampaign;

    public UpdatedCampaignObserver() {
        this.updatedCampaign = new HashSet<>();
    }

    @Override
    public void update(Observable o, Object arg) {
        if (o != null && o instanceof Campaign) {
            Campaign c = (Campaign) o;
            updatedCampaign.add(c);
        }
    }

    public List<Campaign> getUpdatedCampaigns() {
        return new ArrayList<>(updatedCampaign);
    }
}
