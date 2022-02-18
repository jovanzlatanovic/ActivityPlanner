package com.jovan.activityplanner.controller;

import com.jovan.activityplanner.model.Activity;
import com.jovan.activityplanner.model.ActivityModel;
import com.jovan.activityplanner.model.RootActivity;
import com.jovan.activityplanner.view.CreateActivityDialog;
import javafx.collections.ListChangeListener;
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

    public void initialize() {
        // get model
        this.model = ActivityModel.getInstance();
        activityListView.setItems(model.getActivityList());

        // for later maybe, observable is linked inside of initmodel method
        model.getActivityList().addListener((ListChangeListener<Activity>) change -> {

        });
    }

    @FXML
    public void onNewActivityButtonClick(ActionEvent event) throws IOException {
        Node parentNode = (Node) event.getSource();
        CreateActivityDialog dialog = new CreateActivityDialog(parentNode);
        dialog.show();
    }
}
