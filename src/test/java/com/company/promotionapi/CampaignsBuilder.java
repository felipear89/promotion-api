package com.company.promotionapi;

import com.company.promotionapi.model.Campaign;

import java.time.LocalDate;

public class CampaignsBuilder {

    public static Campaign campaignBlackFriday() {
        Campaign c = new Campaign();
        c.setId("1");
        c.setName("Campaign BlackFriday");
        c.setStart(LocalDate.of(2017,11,24));
        c.setEnd(LocalDate.of(2017,11,30));
        return c;
    }

    public static Campaign campaignCompanyBirthday() {
        Campaign c = new Campaign();
        c.setId("2");
        c.setName("Campaign Birthday");
        c.setStart(LocalDate.of(2017,02,01));
        c.setEnd(LocalDate.of(2017,02,10));
        return c;
    }

    public static Campaign campaignChristmas() {
        Campaign c = new Campaign();
        c.setId("3");
        c.setName("Campaign Christmas");
        c.setStart(LocalDate.of(2017,12,25));
        c.setEnd(LocalDate.of(2017,12,27));
        return c;
    }
}
