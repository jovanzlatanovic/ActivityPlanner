package com.jovan.activityplanner.model;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class RootActivity extends Activity {
    private ArrayList<Activity> subActivities;

    public RootActivity(String id, LocalDateTime start, LocalDateTime end, String title, String description) {
        super(id, start, end, title, description);
        this.id = "root_activity-" + id;
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
        // Todo: bugs out when subactivities is null so subactivities are not shown
        return "RootActivity{" +
                "id=" + id +
                "numberOfSubActivities=no information" +
                ", startTime=" + this.getStartTime() +
                ", endTime=" + this.getEndTime() +
                ", title='" + this.getTitle() + '\'' +
                ", description='" + this.getDescription() + '\'' +
                '}';
    }
}
