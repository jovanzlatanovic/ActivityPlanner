package com.jovan.activityplanner.model;

import com.jovan.activityplanner.util.LoggerSingleton;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class ActivityModel {
    //TODO: replace plain activity with root activity to implement sub activity functionality
    private Logger logger = LoggerSingleton.getInstance();

    private static ActivityModel singleton_instance = null;
    private static int highestId = -1;
    private /*final*/ ObservableList<Activity> activityList;
    private int latestIndex = -1;

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
            int highestId = 0;
            for(Activity activity : activityList) {
                int currentId = activity.getNumericId();
                if (currentId >= highestId) {
                    highestId = currentId + 1;
                }
            }
        } else {
            highestId += 1;
        }

        return String.valueOf(highestId);
    }

    public Activity getLatest() {
        return latestIndex < 0 ? null : activityList.get(latestIndex);
    }

    public int getActivityIndex(Activity activity) {
        return activityList.indexOf(activity);
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
        latestIndex = index;
        activityList.add(index, a);
        return index;
    }

    public void deleteActivity(Activity activity) {
        activityList.remove(activity);
    }

    public void updateList(ArrayList<Activity> a) {
        // problem: update activitylist with passed arraylist
        // passed arraylist could be bigger
        // passed arraylist could be smaller
        // passed arraylist could have the same number of elements
        // any element could have a different value than before

        // expected result: activitylist should updated ANY changes made to it from the above criteria
        // activitylist.set() could be used to update existing elements

        logger.info("Updating list, current state: " + activityList.toString());
        activityList.setAll(a);
        logger.info("Updated list, current state: " + activityList.toString());
    }

    private int findNextDateIndex(Activity passed) {
        // todo: implemented as linear search, use binary search later

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
}
