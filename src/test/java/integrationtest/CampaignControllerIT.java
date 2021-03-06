package integrationtest;

import com.company.promotionapi.PromotionApiApplication;
import com.company.promotionapi.model.Campaign;
import com.company.promotionapi.repository.CampaignRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;

import static com.company.promotionapi.CampaignsBuilder.*;
import static com.company.promotionapi.utils.DateUtils.dateToString;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = RANDOM_PORT, classes = PromotionApiApplication.class)
@TestPropertySource(locations = "classpath:/application-integrationtest.properties")
@AutoConfigureMockMvc
public class CampaignControllerIT {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private CampaignRepository campaignRepository;

    @Before
    public void setUp() {
        campaignRepository.deleteAll();
    }

    @Test
    public void createCampaign() throws Exception {

        mvc.perform(post("/campaigns")
                .content(mapper.writeValueAsString(campaignBlackFriday()))
                .contentType(APPLICATION_JSON))
                .andExpect(status().isCreated());

        List<Campaign> campaigns = campaignRepository.list();

        assertEquals(1, campaigns.size());
        assertEquals("Campaign BlackFriday", campaigns.get(0).getName());
    }

    @Test
    public void getCampaign() throws Exception {

        Campaign campaign = campaignRepository.insert(campaignBlackFriday());
        campaignRepository.insert(campaignChristmas());

        mvc.perform(get(campaignUrl(campaign))
                .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(campaign.getName())));
    }

    @Test
    public void listActiveCampaigns() throws Exception {

        campaignRepository.insert(campaignBlackFriday());
        campaignRepository.insert(campaignChristmas());
        campaignRepository.insert(campaignThisWeek());

        LocalDate localDate = LocalDate.now();
        String nowAsString = dateToString(localDate);

        mvc.perform(get("/campaigns")
            .contentType(APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].name", is("Campaign This Week")))
            .andExpect(jsonPath("$[0].start", is(nowAsString)));
    }

    @Test
    public void deleteCampaign() throws Exception {
        Campaign campaignBlackFriday = campaignRepository.insert(campaignBlackFriday());

        mvc.perform(delete(campaignUrl(campaignBlackFriday))
                .contentType(APPLICATION_JSON))
                .andExpect(status().isOk());

        assertEquals(0, campaignRepository.list().size());
    }

    @Test
    public void updateCampaign() throws Exception {

        Campaign campaignBlackFriday = campaignRepository.insert(campaignBlackFriday());

        String newCampaignName = "BLACK FRIDAY CRAZY MANAGER";
        campaignBlackFriday.setName(newCampaignName);

        mvc.perform(put(campaignUrl(campaignBlackFriday))
                .content(mapper.writeValueAsString(campaignBlackFriday))
                .contentType(APPLICATION_JSON))
                .andExpect(status().isOk());

        Campaign updatedCampaign = campaignRepository.findById(campaignBlackFriday.getId()).get();
        assertEquals(newCampaignName, updatedCampaign.getName());

    }

    @Test
    public void findByTeamId() throws Exception {

        Campaign campaignChapeco = genericCampaignTeamId("chapeco");

        campaignRepository.insert(genericCampaignTeamId("sao paulo"));
        campaignRepository.insert(genericCampaignTeamId("palmeiras"));
        campaignRepository.insert(genericCampaignTeamId("santos"));
        campaignChapeco = campaignRepository.insert(campaignChapeco);

        mvc.perform(get("/campaigns/team/" + campaignChapeco.getTeamId())
                .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name", is(campaignChapeco.getName())))
                .andExpect(jsonPath("$", hasSize(1)));
    }

    private String campaignUrl(Campaign campaign) {
        return String.format("/campaigns/%s", campaign.getId());
    }

}