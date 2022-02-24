package com.jovan.activityplanner.controller;

import com.jovan.activityplanner.model.Activity;
import com.jovan.activityplanner.model.ActivityModel;
import com.jovan.activityplanner.model.ApplicationModel;
import com.jovan.activityplanner.model.RootActivity;
import com.jovan.activityplanner.util.LoggerSingleton;
import com.jovan.activityplanner.view.ActivityContainer;
import javafx.animation.RotateTransition;
import javafx.animation.TranslateTransition;
import javafx.collections.ListChangeListener;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TimelineController {
    private Logger logger = LoggerSingleton.getInstance();
    private ApplicationModel appModel;
    private ActivityModel model;
    private ArrayList<ActivityContainer> activityContainers = new ArrayList<>();

    @FXML
    private HBox rootHBox;

    public void initialize() {
        logger.info("Initializing timeline controller");

        // Get models
        appModel = ApplicationModel.getInstance();
        model = ActivityModel.getInstance();

        // Setup activity model listener
        model.getActivityList().addListener((ListChangeListener<? super Activity>) change -> {
            change.next();
            if (change.wasAdded())
                handleActivityModelAddition(change.getFrom());
            if (change.wasRemoved())
                HandleActivityModelDeletion(change.getFrom(), change.getTo());
        });

        /*TranslateTransition translate = new TranslateTransition();
        translate.setNode(rectangleBlue);
        translate.setDuration(Duration.millis(1000));
        translate.setCycleCount(2);
        translate.setByX(250);
        translate.setByY(250);
        translate.setAutoReverse(true);
        translate.play();

        RotateTransition rotate = new RotateTransition();
        rotate.setNode(rectangleBlue);
        rotate.setDuration(Duration.millis(2000));
        rotate.setCycleCount(3);
        rotate.setByAngle(360);
        rotate.play();*/

        logger.info("Timeline controller initialized");
    }

    private ContextMenu getContextMenu() {
        ContextMenu contextMenu = new ContextMenu();
        MenuItem editMenuItem = new MenuItem("Edit");
        SeparatorMenuItem sep = new SeparatorMenuItem();
        MenuItem deleteMenuItem = new MenuItem("Delete");
        contextMenu.getItems().addAll(editMenuItem, sep, deleteMenuItem);

        editMenuItem.setOnAction(e -> {
            //handleEditActivityDialog(e, cell.getIndex());
        });

        deleteMenuItem.setOnAction(e -> {
            //handleDeleteActivity(cell.getIndex());
        });

        // must bind properties later on for auto updating view after editing activity
        // cell.textProperty().bind(cell.itemProperty().asString());

        return contextMenu;
    }

    public void handleActivityModelAddition(int latestIndex) {
        addActivityToView(latestIndex, (RootActivity) model.getActivity(latestIndex));
    }

    public void HandleActivityModelDeletion(int deletedFrom, int deletedTo) {
        activityContainers.removeAll(activityContainers.subList(deletedFrom, deletedTo+1));
        rootHBox.getChildren().remove(deletedFrom, deletedTo+1);
    }

    private void addActivityToView(int index, RootActivity activity) {
        ActivityContainer container = new ActivityContainer(activity, new VBox(), getContextMenu());
        rootHBox.getChildren().add(index, container);
        activityContainers.add(index, container);
        logger.info("Added activity container to timeline view; index = " + index + ", activity = " + activity);
    }
}
