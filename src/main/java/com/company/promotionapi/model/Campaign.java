package com.company.promotionapi.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Observable;

public class Campaign extends Observable implements Serializable {

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
        setChanged();
        notifyObservers();
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

    public boolean notEquals(Campaign campaign) {
        return !this.equals(campaign);
    }

    public boolean isSameEndDate(LocalDate end) {
        return this.end.isEqual(end);
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

}
