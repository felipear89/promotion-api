package com.company.promotionapi.service;

import com.company.promotionapi.model.Campaign;
import com.company.promotionapi.producer.CampaignMessageProducer;
import com.company.promotionapi.repository.CampaignRepository;
import org.assertj.core.util.Lists;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static java.time.LocalDate.of;
import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static org.assertj.core.util.Lists.newArrayList;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.argThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static com.company.promotionapi.CampaignsBuilder.*;
import static org.junit.Assert.*;
import static org.hamcrest.Matchers.contains;

@RunWith(SpringJUnit4ClassRunner.class)
public class CampaignServiceTest {

    @Mock
    private CampaignRepository campaignRepository;

    @Mock
    private CampaignMessageProducer campaignMessageProducer;

    @InjectMocks
    private CampaignService campaignService;

    @Test
    public void shouldNotifyUpdatedCampaign() {

        Campaign c1 = genericCampaign("C1",
                of(2017, 10, 01),
                of(2017, 10, 03));

        Campaign c2 = genericCampaign("C2",
                of(2017, 10, 01),
                of(2017, 10, 02));

        Campaign c3 = genericCampaign("C3",
                of(2017, 10, 01),
                of(2017, 10, 03));

        List<Campaign> campaigns = newArrayList(c1, c2);

        when(campaignRepository.getActiveCampaignInPeriod(any(), any())).thenReturn(campaigns);

        campaignService.create(c3);

        verify(campaignRepository).updateBatch((List<Campaign>)argThat(Matchers.containsInAnyOrder(c1,c2)));
        verify(campaignMessageProducer).sendCampaignUpdateMessage((List<Campaign>)argThat(Matchers.containsInAnyOrder(c1,c2)));

    }

}