package com.jovan.activityplanner.model;

import com.jovan.activityplanner.model.command.Command;
import com.jovan.activityplanner.model.command.CommandHistory;
import com.jovan.activityplanner.model.command.RedoCommand;
import com.jovan.activityplanner.model.command.UndoCommand;
import com.jovan.activityplanner.model.filemanager.AbstractFileLoader;
import com.jovan.activityplanner.model.filemanager.ActivityLoader;
import com.jovan.activityplanner.model.filemanager.LocalFileSystem;
import com.jovan.activityplanner.model.listener.CommandHistoryListener;
import com.jovan.activityplanner.util.LoggerSingleton;

import java.util.logging.Logger;

public class ApplicationModel {
    private Logger logger = LoggerSingleton.getInstance();

    private static ApplicationModel instance = null;
    private final CommandHistory history;
    private final UndoCommand undoCommand;
    private final RedoCommand redoCommand;
    private final AbstractFileLoader fileLoader;

    private ApplicationModel() {
        ActivityModel model = ActivityModel.getInstance();

        history = new CommandHistory();
        undoCommand = new UndoCommand(this, model);
        redoCommand = new RedoCommand(this, model);

        fileLoader = new ActivityLoader(model, new LocalFileSystem(), "activities.json");

        logger.info("ApplicationModel singleton created");
    }

    public static ApplicationModel getInstance() {
        if (instance == null)
            instance = new ApplicationModel();

        return instance;
    }

    public void executeUndo() {
        this.executeCommand(undoCommand);
    }

    public void executeRedo() {
        this.executeCommand(redoCommand);
    }

    public void executeCommand(Command c) {
        if (c.execute()) {
            history.push(c);
        }
        logger.info("Executed command: " + c.getClass().toString());
    }

    public void undo() {
        // LinkedList<T>.pop() throws an exception when the list is empty
        // The list empty check is performed inside of the CommandHistory.pop() definition
        Command command = history.undo();
        if (command != null) {
            command.undo();
        }
    }

    public void redo() {
        Command command = history.redo();
        if (command != null) {
            command.redo();
        }
    }

    public void save() {
        logger.info("Saving to file");
        fileLoader.save();
        logger.info("File saved");
    }

    public void load() {
        logger.info("Loading file");
        fileLoader.load();
        logger.info("File loaded");
    }

    public void addHistoryListener(CommandHistoryListener listener) {
        history.addListener(listener);
    }
}
