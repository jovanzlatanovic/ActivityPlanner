package com.jovan.activityplanner.model.command;

import com.jovan.activityplanner.model.ActivityModel;
import com.jovan.activityplanner.model.ApplicationModel;

public class DeleteCommand extends Command {
    private int deletionIndex;

    public DeleteCommand(ApplicationModel app, ActivityModel model) {
        super(app, model);
    }

    public void setActivityIndxed(int index) {
        this.deletionIndex = index;
    }

    @Override
    public boolean execute() {
        saveBackup();
        this.model.deleteActivity(deletionIndex);
        return true;
    }
}
