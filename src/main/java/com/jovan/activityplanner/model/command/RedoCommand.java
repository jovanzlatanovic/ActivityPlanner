package com.jovan.activityplanner.model.command;

import com.jovan.activityplanner.model.ActivityModel;
import com.jovan.activityplanner.model.ApplicationModel;

public class RedoCommand extends Command {
    public RedoCommand(ApplicationModel app, ActivityModel model) {
        super(app, model);
    }

    @Override
    public boolean execute() {
        app.redo();
        return false;
    }
}