package com.jovan.activityplanner.model.command;

import java.util.LinkedList;

public class CommandHistory {
    private LinkedList<Command> history = new LinkedList<>();

    public void push(Command c) {
        history.push(c);
    }

    public Command pop() {
        return history.size() < 1 ? null : history.pop();
    }
}
