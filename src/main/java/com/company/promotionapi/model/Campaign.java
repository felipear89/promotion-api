package com.company.promotionapi.model;

import org.apache.tomcat.jni.Local;

import java.time.LocalDate;

public class Campaign {

    private String id;

    private String name;

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

    private boolean isOutPeriod(LocalDate otherStartDate, LocalDate otherEndDate) {
        return (otherStartDate.isBefore(start) && otherEndDate.isBefore(start)) ||
                (otherStartDate.isAfter(end) && otherEndDate.isAfter(end));
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
}
