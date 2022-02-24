package com.jovan.activityplanner.model.command;

import com.jovan.activityplanner.model.Activity;
import com.jovan.activityplanner.model.ActivityModel;
import com.jovan.activityplanner.model.ApplicationModel;

public class DeleteCommand extends Command {
    private Activity activityToDelete;

    public DeleteCommand(ApplicationModel app, ActivityModel model) {
        super(app, model);
    }

    public void setActivityToDelete(Activity a) {
        this.activityToDelete = a;
    }

    @Override
    public boolean execute() {
        saveBackup();
        this.model.deleteActivity(activityToDelete);
        return true;
    }
}
