package com.jovan.activityplanner.model.command;

import com.jovan.activityplanner.model.Activity;
import com.jovan.activityplanner.model.ActivityModel;
import com.jovan.activityplanner.model.ApplicationModel;

public class UpdateCommand extends Command{
    private Activity activityToUpdate;
    private int activityToUpdateIndex;

    public UpdateCommand(ApplicationModel app, ActivityModel model) {
        super(app, model);
    }

    public void setParameters(int index, Activity newActivity) {
        this.activityToUpdate = newActivity;
        this.activityToUpdateIndex = index;
    }

    @Override
    public boolean execute() {
        saveBackup();
        this.model.updateActivity(this.activityToUpdateIndex, this.activityToUpdate);
        return true;
    }
}
