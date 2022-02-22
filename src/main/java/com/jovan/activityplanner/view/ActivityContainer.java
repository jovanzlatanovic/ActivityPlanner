package com.jovan.activityplanner.view;

import com.jovan.activityplanner.model.RootActivity;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class ActivityContainer extends TitledPane {
    private RootActivity activity;

    public ActivityContainer(RootActivity activity, VBox infoContainer, ContextMenu contextMenu) {
        super(activity.getTitle(), infoContainer);

        // Setting activity and context menu
        this.activity = activity;
        this.setContextMenu(contextMenu);

        // Filling nodes in view
        Label startTime = new Label(activity.getStartTime().toString());
        Label endTime = new Label(activity.getEndTime().toString());
        Label description = new Label(activity.getDescription());

        infoContainer.getChildren().addAll(startTime, endTime, description);;
    }

    public RootActivity getActivity() {
        return activity;
    }
}
