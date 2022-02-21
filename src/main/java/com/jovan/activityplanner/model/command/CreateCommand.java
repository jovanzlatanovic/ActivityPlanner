package com.jovan.activityplanner.model.command;

import com.jovan.activityplanner.model.Activity;
import com.jovan.activityplanner.model.ActivityModel;
import com.jovan.activityplanner.model.ApplicationModel;

public class CreateCommand extends Command {
    private Activity activityToCreate;

    public CreateCommand(ApplicationModel app, ActivityModel model) {
        super(app, model);
    }

    public void setActivityToCreate(Activity activityToCreate) {
        this.activityToCreate = activityToCreate;
    }

    @Override
    public boolean execute() {
        saveBackup();
        this.model.addActivity(activityToCreate);
        return true;
    }
}
