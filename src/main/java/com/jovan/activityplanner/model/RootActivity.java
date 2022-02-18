package com.jovan.activityplanner.model;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class RootActivity extends Activity {
    private ArrayList<Activity> subActivities;

    public RootActivity(LocalDateTime start, LocalDateTime end, String title, String description) {
        super(start, end, title, description);
        this.subActivities = new ArrayList<Activity>();
    }

    public boolean addSubActivity(Activity activity) {
        // The sub activity must be constrained within the root activity
        if (this.getStartTime().compareTo(activity.getStartTime()) > 0 || this.getEndTime().compareTo(activity.getEndTime()) < 0) {
            return false;
        }

        subActivities.add(activity);
        return true;
    }

    @Override
    public String toString() {
        return "RootActivity{" +
                "numberOfSubActivities=" + subActivities.size() +
                ", startTime=" + this.getStartTime() +
                ", endTime=" + this.getEndTime() +
                ", title='" + this.getTitle() + '\'' +
                ", description='" + this.getDescription() + '\'' +
                '}';
    }
}