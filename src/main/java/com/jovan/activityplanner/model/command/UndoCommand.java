package com.jovan.activityplanner.model.command;

import com.jovan.activityplanner.model.ActivityModel;
import com.jovan.activityplanner.model.ApplicationModel;

public class UndoCommand extends Command{
    public UndoCommand(ApplicationModel app, ActivityModel model) {
        super(app, model);
    }

    @Override
    public boolean execute() {
        app.undo();
        return false;
    }
}
