package com.jovan.activityplanner.model.listener;

import com.jovan.activityplanner.model.command.Command;

public interface CommandHistoryListener {
    void onUndoExecute(Command command, int historySize, int redoSize);
    void onRedoExecute(Command command, int historySize, int redoSize);
}
