package com.company.promotionapi.rules;

import com.company.promotionapi.model.Campaign;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

import static com.company.promotionapi.CampaignsBuilder.genericCampaign;
import static com.company.promotionapi.utils.DateUtils.dateToString;
import static java.time.LocalDate.of;
import static java.util.Arrays.asList;

public class IncrementCampaignPeriodTest {

    @Test
    public void incrementEndDate() {

        Campaign c1 = genericCampaign("C1",
                of(2017, 10, 01),
                of(2017, 10, 03));

        Campaign c2 = genericCampaign("C2",
                of(2017, 10, 01),
                of(2017, 10, 02));

        Campaign c3 = genericCampaign("C3",
                of(2017, 10, 01),
                of(2017, 10, 03));

        List<Campaign> campaigns = asList(c1, c2, c3);

        IncrementCampaignPeriod icp = new IncrementCampaignPeriod();
        icp.incrementEndDate(campaigns, c3);

        Assert.assertEquals("2017-10-04", dateToString(c2.getEnd()));
        Assert.assertEquals("2017-10-05", dateToString(c1.getEnd()));
        Assert.assertEquals("2017-10-03", dateToString(c3.getEnd()));
    }
}