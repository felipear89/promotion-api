package com.company.promotionapi.repository;

import com.company.promotionapi.model.Campaign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Repository
public class CampaignRepository {

    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    public Campaign insert(Campaign campaign) {

        campaign.setId(UUID.randomUUID().toString());

        Map<String, Object> parameters = mapColumns(campaign);

        jdbcTemplate.update("INSERT INTO CAMPAIGN (ID, NAME, START, END) VALUES (:id, :name, :start, :end)", parameters);

        return campaign;
    }

    public Campaign update(Campaign campaign) {

        Map<String, Object> parameters = mapColumns(campaign);

        jdbcTemplate.update("UPDATE CAMPAIGN SET ID = :id, NAME = :name, START = :start, END = :end WHERE ID = :id", parameters);

        return campaign;
    }

    public List<Campaign> list() {
        return jdbcTemplate.query("SELECT ID, NAME, START, END FROM CAMPAIGN",
                new BeanPropertyRowMapper(Campaign.class));
    }

    public Campaign findById(String id) {
        Map<String, Object> params = new HashMap<>();
        params.put("id", id);

        return (Campaign) jdbcTemplate.query("SELECT ID, NAME, START, END FROM CAMPAIGN WHERE ID = :id", params,
                new BeanPropertyRowMapper(Campaign.class)).get(0);
    }

    public void deleteById(String id) {
        Map<String, Object> params = new HashMap<>();
        params.put("id", id);
        jdbcTemplate.update("DELETE FROM CAMPAIGN WHERE ID = :id", params);
    }

    public void deleteAll() {
        jdbcTemplate.update("DELETE FROM CAMPAIGN", new HashMap<>());
    }

    private Map<String, Object> mapColumns(Campaign campaign) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("id", campaign.getId());
        parameters.put("name", campaign.getName());
        parameters.put("start", campaign.getStart());
        parameters.put("end", campaign.getEnd());
        return parameters;
    }

}
