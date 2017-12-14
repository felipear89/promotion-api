package com.company.promotionapi.repository;

import com.company.promotionapi.model.Campaign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.time.ZoneId;
import java.util.List;
import java.util.UUID;

@Repository
public class CampaignRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void insert(Campaign campaign) {
        jdbcTemplate.update("INSERT INTO CAMPAIGN (ID, NAME, START, END) VALUES (?,?,?,?)", (PreparedStatement ps) -> {
            ps.setString(1, UUID.randomUUID().toString());
            ps.setString(2, campaign.getName());
            ps.setDate(3, Date.valueOf(campaign.getStart()));
            ps.setDate(4, Date.valueOf(campaign.getEnd()));
        });
    }

    public List<Campaign> list() {
        return null;
    }
}
