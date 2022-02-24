package com.jovan.activityplanner.controller;

import com.jovan.activityplanner.model.Activity;
import com.jovan.activityplanner.model.ActivityModel;
import com.jovan.activityplanner.model.ApplicationModel;
import com.jovan.activityplanner.model.RootActivity;
import com.jovan.activityplanner.model.command.DeleteCommand;
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
            logger.info("Detected change on activity list");
            while (change.next()) {
                if (change.wasReplaced()) {
                    // undo
                    logger.info("Undo change was detected");
                } else if (change.wasRemoved()) {
                    // could be undo of first element or deleted activity
                    logger.info("Removal change was detected");
                    handleActivityModelDeletion(change.getFrom(), change.getTo());
                } else if (change.wasAdded()) {
                    // new activity, should add new ActivityContainer to timeline
                    logger.info("Was added change detected");
                    handleActivityModelAddition(change.getFrom());
                }
            }
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

    private void handleDeleteActivity(Activity activityToDelete) {
        DeleteCommand c = new DeleteCommand(appModel, model);
        c.setActivityToDelete(activityToDelete);
        this.appModel.executeCommand(c);
    }

    public void handleActivityModelAddition(int latestIndex) {
        addActivityToView(latestIndex, (RootActivity) model.getActivity(latestIndex));
    }

    public void handleActivityModelUndo(int undoFrom, int undoTo) {
        //undo needs to update everything
    }

    public void handleActivityModelDeletion(int deletedFrom, int deletedTo) {
        // Sublist throws ConcurrentModificationException when removeall is used directly after sublist:
        // List<ActivityContainer> containersToRemove = activityContainers.subList(deletedFrom, deletedTo + 1);
        //activityContainers.removeAll(containersToRemove);

        List<ActivityContainer> containersToRemove = new ArrayList<>();

        for (int i = 0; i < activityContainers.size(); i++) {
            if (i >= deletedFrom && i <= deletedTo)
                containersToRemove.add(activityContainers.get(i));
        }

        rootHBox.getChildren().removeAll(containersToRemove);
    }

    private ActivityContainer createNewActivityContainer(RootActivity activity) {
        ContextMenu contextMenu = new ContextMenu();
        MenuItem editMenuItem = new MenuItem("Edit");
        SeparatorMenuItem sep = new SeparatorMenuItem();
        MenuItem deleteMenuItem = new MenuItem("Delete");
        contextMenu.getItems().addAll(editMenuItem, sep, deleteMenuItem);

        editMenuItem.setOnAction(e -> {
            //handleEditActivityDialog(e, cell.getIndex());
        });

        deleteMenuItem.setOnAction(e -> {
            handleDeleteActivity(activity);
        });

        return new ActivityContainer(activity, new VBox(), contextMenu);
    }

    private void addActivityToView(int index, RootActivity activity) {
        ActivityContainer container = createNewActivityContainer(activity);
        rootHBox.getChildren().add(index, container);
        activityContainers.add(index, container);
        logger.info("Added activity container to timeline view; index = " + index + ", activity = " + activity);
    }
}
