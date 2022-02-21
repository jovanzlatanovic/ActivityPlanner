package com.jovan.activityplanner.controller;

import com.jovan.activityplanner.model.Activity;
import com.jovan.activityplanner.model.ActivityModel;
import com.jovan.activityplanner.model.ApplicationModel;
import com.jovan.activityplanner.model.command.CreateCommand;
import com.jovan.activityplanner.model.command.UndoCommand;
import com.jovan.activityplanner.view.CreateActivityDialog;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ListView;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;

import java.io.IOException;

public class MainController {

    @FXML private MenuBar menuBar;

    //TODO: Switch activity for root activity, check activity model comment
    @FXML private ListView<Activity> activityListView;
    private ApplicationModel appModel;
    private ActivityModel model;
    private UndoCommand undoCommand;

    public void initialize() {
        // Get activity and app models
        this.appModel = ApplicationModel.getInstance();
        this.model = ActivityModel.getInstance();
        activityListView.setItems(model.getActivityList());

        // Setup commands
        undoCommand = new UndoCommand(appModel, model);

        // Menubar initialization
        Menu fileMenu = new Menu("File");
        Menu editMenu = new Menu("Edit");

        MenuItem exitMenuItem = new MenuItem("Exit");
        exitMenuItem.setOnAction(e -> {
            Platform.exit();
        });

        MenuItem undoMenuItem = new MenuItem("Undo");
        exitMenuItem.setOnAction(e -> {
            undoCommand.execute();
        });

        fileMenu.getItems().add(exitMenuItem);
        editMenu.getItems().add(undoMenuItem);
        menuBar.getMenus().addAll(fileMenu, editMenu);
    }

    @FXML
    public void onUndoButtonClick(ActionEvent event) {
        undoCommand.execute();
    }

    @FXML
    public void onNewActivityButtonClick(ActionEvent event) throws IOException {
        Node parentNode = (Node) event.getSource();
        CreateActivityDialog dialog = new CreateActivityDialog(parentNode);
        dialog.show();
    }
}
