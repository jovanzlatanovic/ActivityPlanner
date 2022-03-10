package com.jovan.activityplanner.view;

import com.jovan.activityplanner.model.RootActivity;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.math.BigDecimal;

public class ActivityContainer extends TitledPane {
    private RootActivity activity;

    public ActivityContainer(RootActivity activity, VBox infoContainer, ContextMenu contextMenu) {
        super(activity.getTitle(), infoContainer);
        this.setMinWidth(0);
        this.setMinHeight(USE_PREF_SIZE);

        this.setPrefWidth(180);

        double d_endTime = (double)activity.getEndTime().getHour() + ((double)activity.getEndTime().getMinute() / 60.0);
        double d_startTime = (double)activity.getStartTime().getHour() + ((double)activity.getStartTime().getMinute() / 60.0);
        double decimalHourDifference = new BigDecimal(d_endTime).subtract(new BigDecimal(d_startTime)).doubleValue();
        this.setPrefHeight(decimalHourDifference < 1 ? 50 : decimalHourDifference * 50);

        // Setting activity and context menu
        this.activity = activity;
        this.setContextMenu(contextMenu);

        // Filling nodes in view
        Label startTime = new Label(activity.getStartTime().toString());
        Label endTime = new Label(activity.getEndTime().toString());
        Label description = new Label(activity.getDescription());

        description.setWrapText(true);

        infoContainer.getChildren().addAll(description);
    }

    public void setOnClick() {

    }

    public RootActivity getActivity() {
        return activity;
    }
}
