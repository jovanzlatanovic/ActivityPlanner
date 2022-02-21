package com.jovan.activityplanner.model.command;

import java.util.LinkedList;

public class CommandHistory {
    private LinkedList<Command> history = new LinkedList<>();
    private LinkedList<Command> redoHistory = new LinkedList<>();

    public void push(Command c) {
        history.push(c);
        redoHistory.clear();
    }

    public Command undo() {
        return popTo(history, redoHistory);
    }

    public Command redo() {
        return popTo(redoHistory, history);
    }

    // Pop from first list, and push to second list, return popped command
    public Command popTo(LinkedList<Command> from, LinkedList<Command> to) {
        if (from.size() < 1) {
            return null;
        } else {
            Command c = from.pop();
            to.push(c);
            return c;
        }
    }
}
