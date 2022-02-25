package com.jovan.activityplanner.model.filemanager;

import com.jovan.activityplanner.model.RootActivity;

import java.util.ArrayList;
import java.util.List;

public class LocalFileSystem implements FileSystemInterface<RootActivity> {

    @Override
    public List<RootActivity> read(String path) {
        return new ArrayList<RootActivity>();
    }

    @Override
    public void write(String path, List<RootActivity> contents) {

    }
}
