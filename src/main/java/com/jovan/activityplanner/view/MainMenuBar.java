package com.jovan.activityplanner.view;

import com.jovan.activityplanner.Main;
import com.jovan.activityplanner.controller.MainController;
import com.jovan.activityplanner.model.command.Command;
import com.jovan.activityplanner.model.listener.CommandHistoryListener;
import javafx.application.Platform;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;

import java.io.IOException;
import java.util.Properties;

public class MainMenuBar extends MenuBar implements CommandHistoryListener {
    private MainController controller;

    private Menu fileMenu;
    private Menu editMenu;
    private Menu helpMenu;

    private MenuItem newMenuItem;
    private SeparatorMenuItem separator;
    private MenuItem exitMenuItem;
    private MenuItem undoMenuItem;
    private MenuItem redoMenuItem;
    private MenuItem helpMenuItem;
    private MenuItem aboutMenuItem;

    public MainMenuBar(MainController controller) {
        super();
        this.controller = controller;

        // Initialize menus
        fileMenu = new Menu("File");
        editMenu = new Menu("Edit");
        helpMenu = new Menu("Help");

        // Initialize menu items, their shortcuts and their actions
        newMenuItem = new MenuItem("New activity");
        newMenuItem.setOnAction(e -> {
            controller.handleNewActivityDialog(e);
        });
        newMenuItem.setAccelerator(new KeyCodeCombination(KeyCode.N, KeyCombination.CONTROL_DOWN));

        separator = new SeparatorMenuItem();

        exitMenuItem = new MenuItem("Exit");
        exitMenuItem.setOnAction(e -> {
            Platform.exit();
        });

        undoMenuItem = new MenuItem("Undo");
        undoMenuItem.setAccelerator(new KeyCodeCombination(KeyCode.Z, KeyCombination.CONTROL_DOWN));
        undoMenuItem.setOnAction(e -> {
            controller.executeUndo();
        });

        redoMenuItem = new MenuItem("Redo");
        redoMenuItem.setDisable(true);
        redoMenuItem.setAccelerator(new KeyCodeCombination(KeyCode.Y, KeyCombination.CONTROL_DOWN));
        redoMenuItem.setOnAction(e -> {
            controller.executeRedo();
        });

        helpMenuItem = new MenuItem("Help");
        helpMenuItem.setAccelerator(new KeyCodeCombination(KeyCode.F1));
        helpMenuItem.setOnAction(e -> {
            controller.handleOpenBrowser("https://github.com/jovanzlatanovic/ActivityPlanner/wiki");
        });

        aboutMenuItem = new MenuItem("About");
        aboutMenuItem.setOnAction(e -> {
            Properties properties = new Properties();
            try {
                properties.load(Main.class.getResourceAsStream("/project.properties"));
            } catch (IOException ex) {
                ex.printStackTrace();
            }

            Alert aboutDialog = new Alert(Alert.AlertType.INFORMATION);
            aboutDialog.setTitle("About Activity Planner");
            aboutDialog.setHeaderText(String.format("Activity Planner %s", properties.getProperty("version")));
            aboutDialog.setContentText(String.format("Running on Java version: %s\nBuilt using JavaFX 17.0.1\n\nMade by Jovan Zlatanović", System.getProperty("java.version")));
            aboutDialog.showAndWait();
        });

        // Add all menu items to their menus and menus to the menu bar
        fileMenu.getItems().addAll(newMenuItem, separator, exitMenuItem);
        editMenu.getItems().addAll(undoMenuItem, redoMenuItem);
        helpMenu.getItems().addAll(helpMenuItem, aboutMenuItem);
        this.getMenus().addAll(fileMenu, editMenu, helpMenu);
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
