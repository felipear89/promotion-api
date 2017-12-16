package com.company.promotionapi.repository;

import com.company.promotionapi.model.Campaign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSourceUtils;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.*;

import static org.springframework.util.CollectionUtils.isEmpty;

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

    public int update(Campaign campaign) {

        Map<String, Object> params = mapColumns(campaign);

        int updated = jdbcTemplate.update("UPDATE CAMPAIGN SET ID = :id, NAME = :name, TEAM_ID = :teamId, START = :start, END = :end WHERE ID = :id", params);

        return updated;
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

    public List<Campaign> getActiveCampaignInPeriod(LocalDate start, LocalDate end) {
        Map<String, Object> params = new HashMap<>();
        params.put("start", start);
        params.put("end", end);

        return jdbcTemplate.query("SELECT ID, NAME, TEAM_ID, START, END FROM CAMPAIGN WHERE END >= :start AND START <= :end ", params,
                new BeanPropertyRowMapper(Campaign.class));
    }

    public Optional<Campaign> findById(String id) {
        Map<String, Object> params = new HashMap<>();
        params.put("id", id);

        List<Campaign> campaigns = jdbcTemplate.query("SELECT ID, NAME, TEAM_ID, START, END FROM CAMPAIGN WHERE ID = :id", params,
                new BeanPropertyRowMapper(Campaign.class));
        if (isEmpty(campaigns)) {
            return Optional.empty();
        }
        return Optional.of(campaigns.get(0));
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

    public void updateBatch(List<Campaign> campaigns) {
        SqlParameterSource[] params = SqlParameterSourceUtils.createBatch(campaigns.toArray());
        jdbcTemplate.batchUpdate("UPDATE CAMPAIGN SET NAME = :name, TEAM_ID = :teamId, START = :start, END = :end WHERE ID = :id", params);

    }

    public List<Campaign> findByTeamId(String teamId) {
        Map<String, Object> params = new HashMap<>();
        params.put("teamId", teamId);

        return jdbcTemplate.query("SELECT ID, NAME, TEAM_ID, START, END FROM CAMPAIGN WHERE ID = :teamId", params,
                new BeanPropertyRowMapper(Campaign.class));
    }

}
