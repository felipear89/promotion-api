package com.company.promotionapi.controller;

import com.company.promotionapi.model.Campaign;
import com.company.promotionapi.repository.CampaignRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(path = "/campaigns", produces = APPLICATION_JSON_VALUE)
public class CampaignController {

    @Autowired
    private CampaignRepository campaignRepository;

    @GetMapping
    public void list() {

    }

    @PostMapping("")
    @ResponseStatus(CREATED)
    public void create(@RequestBody Campaign campaign) {
        campaignRepository.insert(campaign);
    }

    @GetMapping("/{id}")
    public void findById() {

    }

    @PutMapping("/{id}")
    public void update() {

    }

    @DeleteMapping("/{id}")
    public void delete() {

    }

}
