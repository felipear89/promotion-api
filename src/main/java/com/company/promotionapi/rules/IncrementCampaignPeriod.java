package com.company.promotionapi.rules;

import com.company.promotionapi.model.Campaign;

import java.time.LocalDate;
import java.util.List;
import java.util.function.Predicate;

public class IncrementCampaignPeriod {

    public void incrementDate(List<Campaign> campaigns) {
        campaigns.stream().map(c -> {
            LocalDate endDatePlusOneDay = c.getEnd().plusDays(1);
            c.setEnd(endDatePlusOneDay);
            incrementCampaignWithSameEndDate(campaigns, c);
            return c;
        });
    }

    public void incrementCampaignWithSameEndDate(List<Campaign> campaigns, Campaign campaign) {
        campaigns.stream()
                .filter(shouldIncrement(campaign))
                .map(c -> {
                    LocalDate endDatePlusOneDay = c.getEnd().plusDays(1);
                    c.setEnd(endDatePlusOneDay);
                    incrementCampaignWithSameEndDate(campaigns, c);
                    return c;
                });
    }

    private Predicate<Campaign> shouldIncrement(Campaign campaign) {
        return c -> c.notEquals(campaign) && c.isSameEndDate(campaign.getEnd());
    }

}
