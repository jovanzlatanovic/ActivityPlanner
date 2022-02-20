package com.jovan.activityplanner.model;

import com.jovan.activityplanner.model.command.Command;
import com.jovan.activityplanner.model.command.CommandHistory;

public class ApplicationModel {
    private static ApplicationModel instance = null;
    private final CommandHistory history;

    private ApplicationModel() {
        history = new CommandHistory();
    }

    public static ApplicationModel getInstance() {
        if (instance == null)
            instance = new ApplicationModel();

        return instance;
    }

    public void executeCommand(Command c) {
        if (c.execute()) {
            history.push(c);
        }
    }

    public void undo() {
        // LinkedList<T>.pop() throws an exception when the list is empty
        // The list empty check is performed inside of the CommandHistory.pop() definition
        Command command = history.pop();
        if (command != null) {
            command.undo();
        }
    }
}
