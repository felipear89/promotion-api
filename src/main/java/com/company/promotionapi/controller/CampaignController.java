package com.company.promotionapi.controller;

import com.company.promotionapi.model.Campaign;
import com.company.promotionapi.repository.CampaignRepository;
import com.company.promotionapi.service.CampaignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.http.ResponseEntity.*;

@RestController
@RequestMapping(path = "/campaigns", produces = APPLICATION_JSON_VALUE)
public class CampaignController {

    @Autowired
    private CampaignRepository campaignRepository;

    @Autowired
    private CampaignService campaignService;

    @GetMapping
    public List<Campaign> list() {
        return campaignRepository.getNotExpiredCampaign();
    }

    @GetMapping("/team/{teamId}")
    public List<Campaign> findByTeam(@PathVariable(value="teamId") String teamId) {
        return campaignRepository.findByTeamId(teamId);
    }

    @PostMapping
    @ResponseStatus(CREATED)
    public Campaign create(@RequestBody Campaign campaign) {
        return campaignService.create(campaign);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Campaign> show(@PathVariable(value="id") String id) {
        return campaignRepository.findById(id)
                .map(c -> ok(c))
                .orElse(noContent().build());
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable(value="id") String id) {
        campaignRepository.deleteById(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity update(@PathVariable(value="id") String id, @RequestBody Campaign campaign) {
        campaign.setId(id);
        boolean updated = campaignService.update(campaign);
        if (!updated) {
            return notFound().build();
        }
        return ok().build();
    }

}
