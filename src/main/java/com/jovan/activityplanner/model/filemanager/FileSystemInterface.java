package com.jovan.activityplanner.model.filemanager;

import java.util.List;

public interface FileSystemInterface<T> {
    List<T> read(String path);
    void write(String path, List<T> contents);
}
