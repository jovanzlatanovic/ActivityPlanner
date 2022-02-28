package com.jovan.activityplanner.model.filemanager;

import com.jovan.activityplanner.model.Activity;
import com.jovan.activityplanner.model.ActivityModel;

import java.util.ArrayList;

public class ActivityLoader extends AbstractFileLoader {

    private final ActivityModel model;

    public ActivityLoader(ActivityModel model, FileSystemInterface<?> filesystem, String filepath) {
        super(filesystem, filepath);
        this.model = model;
    }

    @Override
    public void save() {
        filesystem.write(filepath, model.getActivityList());
    }

    @Override
    public void load() {
        model.updateList((ArrayList<Activity>) filesystem.read(filepath));
    }
}
