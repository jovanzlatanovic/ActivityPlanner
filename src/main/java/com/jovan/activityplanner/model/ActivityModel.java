package com.jovan.activityplanner.model;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ActivityModel {
    //TODO: replace plain activity with root activity to implement sub activity functionality

    private final ObservableList<Activity> activityList = FXCollections.observableArrayList();
    private final ObjectProperty<Activity> currentActivity = new SimpleObjectProperty<>(null);

    public ObjectProperty<Activity> currentActivityProperty() {
        return currentActivity;
    }

    public final Activity getCurrentActivity() {
        return currentActivity.get();
    }

    public final void setCurrentActivity(Activity activity) {
        currentActivity.set(activity);
    }

    public ObservableList<Activity> getActivityList() {
        return activityList;
    }
}
