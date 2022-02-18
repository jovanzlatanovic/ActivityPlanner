package com.jovan.activityplanner.model;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

import java.time.LocalDateTime;

public class Activity {
    private LocalDateTime startTime;
    private LocalDateTime endTime;

    private String title;
    private String description;

    public Activity(LocalDateTime startTime, LocalDateTime endTime, String title, String description) {
        if (startTime.isAfter(endTime)) {
            throw new IllegalArgumentException("Cannot create activity. Start time must be before end time.");
        }

        this.startTime = startTime;
        this.endTime = endTime;

        this.title = title.isBlank() ? "(No title)" : title;
        this.description = description;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public boolean setStartTime(LocalDateTime startTime) {
        if (startTime.isBefore(this.endTime)) {
            this.startTime = startTime;
            return true;
        }

        return false;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public boolean setEndTime(LocalDateTime endTime) {
        if (endTime.isAfter(this.startTime)) {
            this.endTime = endTime;
            return true;
        }

        return false;
    }

    //todo srediti ovo da ne koristi properties nego POJO plain old java opjects

    public boolean setTime(LocalDateTime startTime, LocalDateTime endTime) {
        if (startTime.isBefore(endTime)) {
            this.startTime = startTime;
            this.endTime = endTime;
            return true;
        }

        return false;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Activity{" +
                "startTime=" + startTime +
                ", endTime=" + endTime +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
