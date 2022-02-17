package com.jovan.activityplanner.model;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

import java.time.LocalDateTime;

public class Activity {
    private ObjectProperty<LocalDateTime> startTime;
    private ObjectProperty<LocalDateTime> endTime;

    private SimpleStringProperty title;
    private SimpleStringProperty description;

    public Activity(LocalDateTime startTime, LocalDateTime endTime, String title, String description) {
        if (startTime.isAfter(endTime)) {
            throw new IllegalArgumentException("Cannot create activity. Start time must be before end time.");
        }

        this.startTime = new SimpleObjectProperty<>();
        this.startTime.set(startTime);

        this.endTime = new SimpleObjectProperty<>();
        this.endTime.set(endTime);

        this.title.set(title.isBlank() ? "(No title)" : title);
        this.description.set(description);
    }

    public ObjectProperty<LocalDateTime> startTimeProperty() {
        return startTime;
    }

    public ObjectProperty<LocalDateTime> endTimeProperty() {
        return endTime;
    }

    public SimpleStringProperty titleProperty() {
        return title;
    }

    public SimpleStringProperty descriptionProperty() {
        return description;
    }

    public LocalDateTime getStartTime() {
        return startTime.get();
    }

    public boolean setStartTime(LocalDateTime startTime) {
        if (startTime.isBefore(this.endTime.get())) {
            this.startTime.set(startTime);
            return true;
        }

        return false;
    }

    public LocalDateTime getEndTime() {
        return endTime.get();
    }

    public boolean setEndTime(LocalDateTime endTime) {
        if (endTime.isAfter(this.startTime.get())) {
            this.endTime.set(endTime);
            return true;
        }

        return false;
    }

    public boolean setTime(LocalDateTime startTime, LocalDateTime endTime) {
        if (startTime.isBefore(endTime)) {
            this.startTime.set(startTime);
            this.endTime.set(endTime);
            return true;
        }

        return false;
    }

    public String getTitle() {
        return title.get();
    }

    public void setTitle(String title) {
        this.title.set(title);
    }

    public String getDescription() {
        return description.get();
    }

    public void setDescription(String description) {
        this.description.set(description);
    }

    @Override
    public String toString() {
        return "Activity{" +
                "startTime=" + startTime.get() +
                ", endTime=" + endTime.get() +
                ", title='" + title.get() + '\'' +
                ", description='" + description.get() + '\'' +
                '}';
    }
}
