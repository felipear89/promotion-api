package com.company.promotionapi.service;

import com.company.promotionapi.model.Campaign;
import com.company.promotionapi.producer.CampaignMessageProducer;
import com.company.promotionapi.repository.CampaignRepository;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static com.company.promotionapi.CampaignsBuilder.genericCampaign;
import static java.time.LocalDate.of;
import static org.assertj.core.util.Lists.newArrayList;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.argThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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
        when(campaignRepository.insert(any())).thenReturn(c3);

        campaignService.create(c3);

        verify(campaignRepository).updateBatch((List<Campaign>)argThat(Matchers.containsInAnyOrder(c1,c2)));
        verify(campaignMessageProducer).sendCampaignUpdateMessage((List<Campaign>)argThat(Matchers.containsInAnyOrder(c1,c2)));

    }

}