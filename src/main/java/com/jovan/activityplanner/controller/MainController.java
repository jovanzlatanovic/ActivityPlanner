package com.jovan.activityplanner.controller;

import com.jovan.activityplanner.model.Activity;
import com.jovan.activityplanner.model.ActivityModel;
import com.jovan.activityplanner.model.RootActivity;
import com.jovan.activityplanner.view.CreateActivityDialog;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ListView;

import java.io.IOException;

public class MainController {

    //TODO: Switch activity for root activity, check activity model comment
    @FXML private ListView<Activity> activityListView;
    private ActivityModel model;

    public void initModel(ActivityModel model) {
        if (this.model != null) {
            // Ensure model is only initialized once
            throw new IllegalStateException("Model can only be initialized once");
        }

        this.model = model;
        activityListView.setItems(model.getActivityList());
        activityListView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            model.setCurrentActivity(newSelection);
        });

        model.currentActivityProperty().addListener((obs, oldActivity, newActivity) -> {
            if (newActivity == null) {
                activityListView.getSelectionModel().clearSelection();
            } else {
                activityListView.getSelectionModel().select(newActivity);
            }
        });
    }

    @FXML
    public void onNewActivityButtonClick(ActionEvent event) throws IOException {
        Node parentNode = (Node) event.getSource();
        CreateActivityDialog dialog = new CreateActivityDialog(parentNode);
        dialog.show();
    }
}
