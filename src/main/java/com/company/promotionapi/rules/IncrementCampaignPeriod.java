package com.company.promotionapi.rules;

import com.company.promotionapi.model.Campaign;

import java.time.LocalDate;
import java.util.List;
import java.util.function.Predicate;

import static java.util.Comparator.comparing;

public class IncrementCampaignPeriod {

    public void incrementEndDate(List<Campaign> campaigns, Campaign campaign) {
        campaigns.stream()
                .filter(c -> c.notEquals(campaign))
                .sorted(comparing(Campaign::getEnd))
                .forEach(c -> {
                    LocalDate endDatePlusOneDay = c.getEnd().plusDays(1);
                    c.setEnd(endDatePlusOneDay);
                    avoidConflictEndDate(campaigns, c);
                });
    }

    private void avoidConflictEndDate(List<Campaign> campaigns, Campaign campaign) {
        boolean conflict = campaigns.stream().anyMatch(shouldIncrement(campaign));
        if (conflict) {
            LocalDate endDatePlusOneDay = campaign.getEnd().plusDays(1);
            campaign.setEnd(endDatePlusOneDay);
            avoidConflictEndDate(campaigns, campaign);
        }
    }

    private Predicate<Campaign> shouldIncrement(Campaign campaign) {
        return c -> c.notEquals(campaign) && c.isSameEndDate(campaign.getEnd());
    }

}
