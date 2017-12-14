package com.company.promotionapi.model;

import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;

import static org.junit.Assert.*;

public class CampaignTest {

    private Campaign campaign;

    @Before
    public void setUp() {
        campaign = new Campaign();

        LocalDate startDate = LocalDate.of(2017, 05, 01);
        LocalDate endDate = LocalDate.of(2017, 05, 31);

        campaign.setStart(startDate);
        campaign.setEnd(endDate);
    }

    @Test
    public void periodBeforeCampaign() {

        LocalDate start = LocalDate.of(2017, 04, 01);
        LocalDate end = LocalDate.of(2017, 04, 10);

        boolean inPeriod = campaign.isInPeriod(start, end);

        assertFalse(inPeriod);
    }

    @Test
    public void periodAfterCampaign() {

        LocalDate start = LocalDate.of(2017, 06, 01);
        LocalDate end = LocalDate.of(2017, 06, 10);

        boolean inPeriod = campaign.isInPeriod(start, end);

        assertFalse(inPeriod);
    }

    @Test
    public void periodEndBetweenCampaign() {

        LocalDate start = LocalDate.of(2017, 04, 01);
        LocalDate end = LocalDate.of(2017, 05, 10);

        boolean inPeriod = campaign.isInPeriod(start, end);

        assertTrue(inPeriod);
    }

    @Test
    public void periodStartBetweenCampaign() {

        LocalDate start = LocalDate.of(2017, 05, 10);
        LocalDate end = LocalDate.of(2017, 10, 01);

        boolean inPeriod = campaign.isInPeriod(start, end);

        assertTrue(inPeriod);
    }

    @Test
    public void periodInsideCampaign() {

        LocalDate start = LocalDate.of(2017, 05, 10);
        LocalDate end = LocalDate.of(2017, 05, 15);

        boolean inPeriod = campaign.isInPeriod(start, end);

        assertTrue(inPeriod);
    }

    @Test
    public void periodCampaignInsideOtherDate() {

        LocalDate start = LocalDate.of(2015, 05, 10);
        LocalDate end = LocalDate.of(2020, 05, 15);

        boolean inPeriod = campaign.isInPeriod(start, end);

        assertTrue(inPeriod);
    }

}