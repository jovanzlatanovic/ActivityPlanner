package com.jovan.activityplanner.view;

import com.jovan.activityplanner.Main;
import com.jovan.activityplanner.controller.MainController;
import com.jovan.activityplanner.model.ActivityModel;
import com.jovan.activityplanner.model.ApplicationModel;
import com.jovan.activityplanner.model.RootActivity;
import com.jovan.activityplanner.model.command.Command;
import com.jovan.activityplanner.model.command.CreateCommand;
import com.jovan.activityplanner.model.listener.CommandHistoryListener;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Properties;

public class MainMenuBar extends MenuBar implements CommandHistoryListener {
    private MainController controller;
    private ApplicationModel appModel;
    private ActivityModel model_forDebug; // here only for debug options

    private Menu fileMenu;
    private Menu editMenu;
    private Menu helpMenu;
    private Menu debugMenu;

    private MenuItem newMenuItem;
    private MenuItem saveMenuItem;
    private MenuItem loadMenuItem;
    private MenuItem exitMenuItem;
    private MenuItem undoMenuItem;
    private MenuItem redoMenuItem;
    private MenuItem helpMenuItem;
    private MenuItem aboutMenuItem;

    private MenuItem debugAddItemsMenuItem;
    private MenuItem debugRemoveItemsMenuItem;

    public MainMenuBar(MainController controller, ApplicationModel appModel) {
        super();

        this.controller = controller;
        this.appModel = appModel;

        this.model_forDebug = ActivityModel.getInstance();

        // Initialize menus
        fileMenu = new Menu("File");
        editMenu = new Menu("Edit");
        helpMenu = new Menu("Help");
        debugMenu = new Menu("Debug");

        // Initialize separators
        SeparatorMenuItem separator1 = new SeparatorMenuItem();
        SeparatorMenuItem separator2 = new SeparatorMenuItem();

        // Initialize menu items, their shortcuts and their actions
        newMenuItem = new MenuItem("New activity");
        newMenuItem.setOnAction(e -> {
            controller.handleNewActivityDialog(e);
        });
        newMenuItem.setAccelerator(new KeyCodeCombination(KeyCode.N, KeyCombination.CONTROL_DOWN));

        saveMenuItem = new MenuItem("Save");
        saveMenuItem.setOnAction(e -> {
            appModel.save();
        });
        saveMenuItem.setAccelerator(new KeyCodeCombination(KeyCode.S, KeyCombination.CONTROL_DOWN));

        loadMenuItem = new MenuItem("Load");
        loadMenuItem.setOnAction(e -> {
            appModel.load();
        });

        exitMenuItem = new MenuItem("Exit");
        exitMenuItem.setOnAction(e -> {
            Platform.exit();
        });

        undoMenuItem = new MenuItem("Undo");
        undoMenuItem.setAccelerator(new KeyCodeCombination(KeyCode.Z, KeyCombination.CONTROL_DOWN));
        undoMenuItem.setOnAction(e -> {
            this.appModel.executeUndo();
        });

        redoMenuItem = new MenuItem("Redo");
        redoMenuItem.setDisable(true);
        redoMenuItem.setAccelerator(new KeyCodeCombination(KeyCode.Y, KeyCombination.CONTROL_DOWN));
        redoMenuItem.setOnAction(e -> {
            this.appModel.executeRedo();
        });

        helpMenuItem = new MenuItem("Help");
        helpMenuItem.setAccelerator(new KeyCodeCombination(KeyCode.F1));
        helpMenuItem.setOnAction(e -> {
            controller.handleOpenBrowser("https://github.com/jovanzlatanovic/ActivityPlanner/wiki");
        });

        aboutMenuItem = new MenuItem("About");
        aboutMenuItem.setOnAction(e -> {
            Alert aboutDialog = new AboutDialog(this.getScene().getWindow());
            aboutDialog.show();
        });

        debugAddItemsMenuItem = new MenuItem("Create dummy activities");
        debugAddItemsMenuItem.setOnAction(e -> {
            LocalDate date = LocalDate.now();
            LocalTime time = LocalTime.of(3, 0);
            for (int i = 0; i < 5; i++) {
                RootActivity newActivity = new RootActivity(this.model_forDebug.getUniqueId(), LocalDateTime.of(date, time), LocalDateTime.of(date, time).plusHours(2), "Debug " + String.valueOf(i), "This is a text for debugging and spamming activities.");

                time = time.plusHours(3);

                CreateCommand c = new CreateCommand(appModel, model_forDebug);
                c.setActivityToCreate(newActivity);
                this.appModel.executeCommand(c);
            }
        });

        // Add all menu items to their menus and menus to the menu bar
        fileMenu.getItems().addAll(newMenuItem, separator1, saveMenuItem, loadMenuItem, separator2, exitMenuItem);
        editMenu.getItems().addAll(undoMenuItem, redoMenuItem);
        helpMenu.getItems().addAll(helpMenuItem, aboutMenuItem);
        debugMenu.getItems().addAll(debugAddItemsMenuItem);

        this.getMenus().addAll(fileMenu, editMenu, helpMenu, debugMenu);
    }

    private void updateRedoMenuItemVisibility(int redoSize) {
        if (redoSize > 0) {
            redoMenuItem.setDisable(false);
        } else {
            redoMenuItem.setDisable(true);
        }
    }

    @Override
    public void onUndoExecute(Command command, int historySize, int redoSize) {
        updateRedoMenuItemVisibility(redoSize);
    }

    @Override
    public void onRedoExecute(Command command, int historySize, int redoSize) {
        updateRedoMenuItemVisibility(redoSize);
    }
}
