package com.company.promotionapi.model;

import java.time.LocalDate;

public class Campaign {

    private String id;

    private String name;

    private String teamId;

    private LocalDate start;

    private LocalDate end;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getStart() {
        return start;
    }

    public void setStart(LocalDate start) {
        this.start = start;
    }

    public LocalDate getEnd() {
        return end;
    }

    public void setEnd(LocalDate end) {
        this.end = end;
    }

    public String getTeamId() {
        return teamId;
    }

    public void setTeamId(String teamId) {
        this.teamId = teamId;
    }

    private boolean isOutPeriod(LocalDate otherStartDate, LocalDate otherEndDate) {
        return otherEndDate.isBefore(start) || otherStartDate.isAfter(end);
    }

    public boolean isInPeriod(LocalDate otherStartDate, LocalDate otherEndDate) {
        return !isOutPeriod(otherStartDate, otherEndDate);
    }

    @Override
    public String toString() {
        return "Campaign{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", start=" + start +
                ", end=" + end +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Campaign campaign = (Campaign) o;

        return id != null ? id.equals(campaign.id) : campaign.id == null;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    public boolean notEquals(Campaign campaign) {
        return !this.equals(campaign);
    }

    public boolean isSameEndDate(LocalDate end) {
        return this.end.isEqual(end);
    }
}
