package com.jovan.activityplanner.model;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ActivityModel {
    //TODO: replace plain activity with root activity to implement sub activity functionality

    private static ActivityModel singleton_instance = null;
    private final ObservableList<Activity> activityList;

    private ActivityModel() {
        activityList = FXCollections.observableArrayList();
    }

    public static ActivityModel getInstance() {
        if (singleton_instance == null)
            singleton_instance = new ActivityModel();

        return singleton_instance;
    }

    public ObservableList<Activity> getActivityList() {
        return activityList;
    }

    public void addActivity(Activity a) {
        activityList.add(a);
    }

    public void deleteActivity(int index) {
        activityList.remove(index);
    }

    //private final ObjectProperty<Activity> currentActivity = new SimpleObjectProperty<>(null);

    /*public ObjectProperty<Activity> currentActivityProperty() {
        return currentActivity;
    }

    public final Activity getCurrentActivity() {
        return currentActivity.get();
    }

    public final void setCurrentActivity(Activity activity) {
        currentActivity.set(activity);
    }*/
}
