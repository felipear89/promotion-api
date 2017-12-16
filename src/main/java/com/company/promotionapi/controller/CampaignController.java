package com.company.promotionapi.controller;

import com.company.promotionapi.model.Campaign;
import com.company.promotionapi.repository.CampaignRepository;
import com.company.promotionapi.service.CampaignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

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

    @PostMapping
    @ResponseStatus(CREATED)
    public void create(@RequestBody Campaign campaign) {
        campaignService.create(campaign);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Campaign> show(@PathVariable(value="id") String id) {
        return campaignRepository.findById(id)
                .map(c -> new ResponseEntity<>(c, OK))
                .orElse(new ResponseEntity<>(NO_CONTENT));
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable(value="id") String id) {
        campaignRepository.deleteById(id);
    }

    @PutMapping("/{id}")
    public void update(@RequestBody Campaign campaign) {
        campaignService.update(campaign);
    }

}
