package com.company.promotionapi.model;

import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;

import static org.junit.Assert.*;

public class CampaignTest {

    private Campaign campaign;
    private LocalDate startDate;
    private LocalDate endDate;

    @Before
    public void setUp() {
        campaign = new Campaign();

        startDate = LocalDate.of(2017, 12, 01);
        endDate = LocalDate.of(2017, 12, 10);

        campaign.setStart(startDate);
        campaign.setEnd(endDate);
    }

    @Test
    public void sameDate() {
        assertTrue(campaign.isSamePeriod(startDate, endDate));
    }

    @Test
    public void notEqualsEndDate() {
        assertFalse(campaign.isSamePeriod(startDate, endDate.plusDays(1)));
    }

    @Test
    public void notEqualsStartDate() {
        assertFalse(campaign.isSamePeriod(startDate.plusDays(1), endDate));
    }
}