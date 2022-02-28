package com.jovan.activityplanner.model.filemanager;

public abstract class AbstractFileLoader {
    protected final String filepath;
    protected final FileSystemInterface filesystem;

    public AbstractFileLoader(FileSystemInterface filesystem, String filepath) {
        this.filepath = filepath;
        this.filesystem = filesystem;
    }

    abstract public void save();
    abstract public void load();
}
