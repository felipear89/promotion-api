package com.company.promotionapi.service;

import com.company.promotionapi.repository.CampaignRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Collections;

import static java.util.Collections.emptyList;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static com.company.promotionapi.CampaignsBuilder.*;
import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
public class CampaignServiceTest {

    @Mock
    private CampaignRepository campaignRepository;

    @InjectMocks
    private CampaignService campaignService;

    @Test
    public void test() {
        when(campaignRepository.list()).thenReturn(emptyList());
        campaignService.create(campaignBlackFriday());
        verify(campaignRepository).updateBatch(emptyList());

    }

}