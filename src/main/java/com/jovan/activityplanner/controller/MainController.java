package com.jovan.activityplanner.controller;

import com.jovan.activityplanner.model.Activity;
import com.jovan.activityplanner.model.ActivityModel;
import com.jovan.activityplanner.model.ApplicationModel;
import com.jovan.activityplanner.model.command.CreateCommand;
import com.jovan.activityplanner.model.command.UndoCommand;
import com.jovan.activityplanner.view.CreateActivityDialog;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ListView;

import java.io.IOException;

public class MainController {

    //TODO: Switch activity for root activity, check activity model comment
    @FXML private ListView<Activity> activityListView;
    private ApplicationModel appModel;
    private ActivityModel model;

    public void initialize() {
        // Get activity and app models
        this.appModel = ApplicationModel.getInstance();
        this.model = ActivityModel.getInstance();
        activityListView.setItems(model.getActivityList());

        // Setup commands
        //CreateCommand createCommand = new CreateCommand(appModel, model);
        //UndoCommand undoCommand = new UndoCommand(appModel, model);
        //todo: link commands to actions
    }

    @FXML
    public void onUndoButtonClick(ActionEvent event) {
        UndoCommand u = new UndoCommand(appModel, model);
        u.execute();
    }

    @FXML
    public void onNewActivityButtonClick(ActionEvent event) throws IOException {
        Node parentNode = (Node) event.getSource();
        CreateActivityDialog dialog = new CreateActivityDialog(parentNode);
        dialog.show();
    }
}
