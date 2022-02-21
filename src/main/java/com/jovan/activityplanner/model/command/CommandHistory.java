package com.jovan.activityplanner.model.command;

import com.jovan.activityplanner.model.listener.CommandHistoryListener;

import java.util.ArrayList;
import java.util.LinkedList;

public class CommandHistory {
    private LinkedList<Command> history = new LinkedList<>();
    private LinkedList<Command> redoHistory = new LinkedList<>();

    private ArrayList<CommandHistoryListener> listeners = new ArrayList<>();

    public void push(Command c) {
        history.push(c);
        redoHistory.clear();
    }

    public Command undo() {
        Command popped = popTo(history, redoHistory);
        listeners.forEach(l -> l.onUndoExecute(popped, history.size(), redoHistory.size()));
        return popped;
    }

    public Command redo() {
        Command popped = popTo(redoHistory, history);
        listeners.forEach(l -> l.onRedoExecute(popped, history.size(), redoHistory.size()));
        return popped;
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

    public void addListener(CommandHistoryListener listener) {
        listeners.add(listener);
    }
}
