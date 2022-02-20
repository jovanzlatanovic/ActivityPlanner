package com.jovan.activityplanner.model.command;

import com.jovan.activityplanner.model.Activity;
import com.jovan.activityplanner.model.ActivityModel;
import com.jovan.activityplanner.model.ApplicationModel;

import java.lang.reflect.Array;
import java.util.ArrayList;

public abstract class Command {
    protected ApplicationModel app;
    protected ActivityModel model;
    protected ArrayList<Activity> backup;

    public Command (ApplicationModel app, ActivityModel model) {
        this.app = app;
        this.model = model;
        //this.backup = model.getActivityList();
    }

    public void saveBackup() {
        backup = new ArrayList<>(model.getActivityList());
    }

    public void undo() {
        this.model.updateList(this.backup);
    }

    // Return true if command should be added to history, false otherwise
    public abstract boolean execute();
}
