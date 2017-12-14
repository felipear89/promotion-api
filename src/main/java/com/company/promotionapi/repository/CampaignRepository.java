package com.company.promotionapi.repository;

import com.company.promotionapi.model.Campaign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.time.LocalDate;
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

        Map<String, Object> params = mapColumns(campaign);

        jdbcTemplate.update("INSERT INTO CAMPAIGN (ID, NAME, TEAM_ID, START, END) VALUES (:id, :name, :teamId, :start, :end)", params);

        return campaign;
    }

    public Campaign update(Campaign campaign) {

        Map<String, Object> params = mapColumns(campaign);

        jdbcTemplate.update("UPDATE CAMPAIGN SET ID = :id, NAME = :name, TEAM_ID = :teamId, START = :start, END = :end WHERE ID = :id", params);

        return campaign;
    }

    public List<Campaign> list() {
        return jdbcTemplate.query("SELECT ID, NAME, TEAM_ID, START, END FROM CAMPAIGN",
                new BeanPropertyRowMapper(Campaign.class));
    }

    public List<Campaign> getNotExpiredCampaign() {
        Map<String, Object> params = new HashMap<>();
        params.put("now", LocalDate.now());

        return jdbcTemplate.query("SELECT ID, NAME, TEAM_ID, START, END FROM CAMPAIGN WHERE END >= :now", params,
                new BeanPropertyRowMapper(Campaign.class));
    }

    public Campaign findById(String id) {
        Map<String, Object> params = new HashMap<>();
        params.put("id", id);

        return (Campaign) jdbcTemplate.query("SELECT ID, NAME, TEAM_ID, START, END FROM CAMPAIGN WHERE ID = :id", params,
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
        parameters.put("teamId", campaign.getTeamId());
        parameters.put("start", campaign.getStart());
        parameters.put("end", campaign.getEnd());
        return parameters;
    }

}
