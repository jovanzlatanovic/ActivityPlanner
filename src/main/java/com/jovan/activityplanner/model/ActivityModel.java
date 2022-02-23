package com.jovan.activityplanner.model;

import com.jovan.activityplanner.util.LoggerSingleton;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ActivityModel {
    //TODO: replace plain activity with root activity to implement sub activity functionality
    private Logger logger = LoggerSingleton.getInstance();

    private static ActivityModel singleton_instance = null;
    private final ObservableList<Activity> activityList;

    private ActivityModel() {
        activityList = FXCollections.observableArrayList();
        logger.info("ActivityModel singleton created");
    }

    public static ActivityModel getInstance() {
        if (singleton_instance == null) {
            singleton_instance = new ActivityModel();
        }

        return singleton_instance;
    }

    public ObservableList<Activity> getActivityList() {
        return activityList;
    }

    public String getUniqueId() {
        if (activityList.size() < 1) {
            return "0";
        }

        int highestId = 0;
        for(Activity activity : activityList) {
            int currentId = activity.getNumericId();
            if (currentId >= highestId) {
                highestId = currentId + 1;
            }
        }

        return String.valueOf(highestId);
    }

    public Activity getLatest() {
        return activityList.size() < 1 ? null : activityList.get(activityList.size()-1);
    }

    public Activity getActivity(int index) {
        return activityList.get(index);
    }

    public void updateActivity(int index, Activity newActivity) {
        activityList.set(index, newActivity);
    }

    // Returns at which index the activity was inserted at
    public int addActivity(Activity a) {
        int index = findNextDateIndex(a);
        activityList.add(index, a);
        return index;
    }

    public void deleteActivity(int index) {
        activityList.remove(index);
    }

    public void updateList(ArrayList<Activity> a) {
        // This may look ugly but is necessary to preserve the finality of activityList, might refactor later
        activityList.clear();
        activityList.addAll(a);
    }

    private int findNextDateIndex(Activity passed) {
        // todo: implemented as linear search, use binary search later

        //todo: index is not returned properly, it need to step through this algorythm
        int index = 0;
        for (int i = 0; i < activityList.size(); i++) {
            if (passed.getStartTime().compareTo(activityList.get(i).getStartTime()) >= 0) {
                // passed date greater than activityList date
                index = i + 1;
            } else if (passed.getStartTime().compareTo(activityList.get(i).getStartTime()) < 0) {
                // passed date lesser than activityList date
                return index; // at this location the passed activity should be placed, and all elements moved to the right
            } else {
                logger.severe("Could not find next index in which to insert the passed activity: " + passed);
                throw new IndexOutOfBoundsException("Could not find next index in which to insert the passed activity");
            }
        }
        return index;
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
