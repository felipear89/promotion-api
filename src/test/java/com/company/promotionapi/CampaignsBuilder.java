package com.company.promotionapi;

import com.company.promotionapi.model.Campaign;

import java.time.LocalDate;
import java.util.UUID;

public class CampaignsBuilder {

    public static Campaign campaignBlackFriday() {
        Campaign c = new Campaign();
        c.setId("1");
        c.setName("Campaign BlackFriday");
        c.setTeamId("spfc");
        c.setStart(LocalDate.of(2016,11,24));
        c.setEnd(LocalDate.of(2016,11,30));
        return c;
    }

    public static Campaign campaignCompanyBirthday() {
        Campaign c = new Campaign();
        c.setId("2");
        c.setName("Campaign Birthday");
        c.setTeamId("spfc");
        c.setStart(LocalDate.of(2016,02,01));
        c.setEnd(LocalDate.of(2016,02,10));
        return c;
    }

    public static Campaign campaignChristmas() {
        Campaign c = new Campaign();
        c.setId("3");
        c.setName("Campaign Christmas");
        c.setTeamId("spfc");
        c.setStart(LocalDate.of(2016,12,25));
        c.setEnd(LocalDate.of(2016,12,27));
        return c;
    }

    public static Campaign campaignThisWeek() {
        Campaign c = new Campaign();
        c.setId("4");
        c.setName("Campaign This Week");
        c.setTeamId("spfc");
        c.setStart(LocalDate.now());
        c.setEnd(LocalDate.now().plusWeeks(1));
        return c;
    }

    public static Campaign genericCampaign(String name, LocalDate start, LocalDate end) {
        Campaign c = new Campaign();
        c.setId(UUID.randomUUID().toString());
        c.setName(name);
        c.setStart(start);
        c.setEnd(end);
        return c;
    }

    public static Campaign genericCampaignTeamId(String teamId) {
        Campaign c = new Campaign();
        c.setId(UUID.randomUUID().toString());
        c.setName("Campaign By Team " + teamId);
        c.setTeamId(teamId);
        c.setStart(LocalDate.of(2016,12,25));
        c.setEnd(LocalDate.of(2016,12,27));
        return c;
    }
}
