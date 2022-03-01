package com.jovan.activityplanner.controller;

import com.jovan.activityplanner.Main;
import com.jovan.activityplanner.model.Activity;
import com.jovan.activityplanner.model.ActivityModel;
import com.jovan.activityplanner.model.ApplicationModel;
import com.jovan.activityplanner.model.command.Command;
import com.jovan.activityplanner.model.command.DeleteCommand;
import com.jovan.activityplanner.model.command.RedoCommand;
import com.jovan.activityplanner.model.command.UndoCommand;
import com.jovan.activityplanner.model.listener.CommandHistoryListener;
import com.jovan.activityplanner.util.LoggerSingleton;
import com.jovan.activityplanner.view.CreateActivityDialog;
import com.jovan.activityplanner.view.MainMenuBar;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MainController {

    @FXML private BorderPane rootBorderPane;
    @FXML private AnchorPane timelineAnchor;
    @FXML private MenuBar menuBar;

    private Logger logger = LoggerSingleton.getInstance();

    //TODO: Switch activity for root activity, check activity model comment
    //@FXML private ListView<Activity> activityListView;
    private ApplicationModel appModel;
    private ActivityModel model;

    private UndoCommand undoCommand;
    private RedoCommand redoCommand;

    public void initialize() {
        logger.info("Initializing main controller");
        // Get activity and app models
        this.model = ActivityModel.getInstance();
        this.appModel = ApplicationModel.getInstance();

        // Set click and drag for window
//        rootBorderPane.setOnMousePressed(pressEvent -> {
//            rootBorderPane.setOnMouseDragged(dragEvent -> {
//                rootBorderPane.getScene().getWindow().setX(dragEvent.getScreenX() - pressEvent.getSceneX());
//                rootBorderPane.getScene().getWindow().setY(dragEvent.getScreenY() - pressEvent.getSceneY());
//            });
//        });

        //activityListView.setItems(model.getActivityList());

        // Setup commands
        undoCommand = new UndoCommand(appModel, model);
        redoCommand = new RedoCommand(appModel, model);

        // Menu bar initialization
        MenuBar newMenuBar = new MainMenuBar(this, appModel);
        newMenuBar.setId(menuBar.getId());
        menuBar = newMenuBar;
        appModel.addHistoryListener((CommandHistoryListener) menuBar);
        rootBorderPane.setTop(menuBar);

        // Timeline initialization
        logger.info("Loading timeline view");
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("fxml/timelineView.fxml"));
        try {
            ScrollPane timelineView = fxmlLoader.load();

            timelineAnchor.getChildren().add(timelineView);

            AnchorPane.setTopAnchor(timelineView, 0.0);
            AnchorPane.setBottomAnchor(timelineView, 0.0);
            AnchorPane.setLeftAnchor(timelineView, 0.0);
            AnchorPane.setRightAnchor(timelineView, 0.0);

            rootBorderPane.setCenter(timelineAnchor);

            logger.info("Timeline view loaded");
        } catch (IOException e) {
            logger.severe("Exception occured while loading timeline view: " + e.toString());
            Platform.exit();
        }
        logger.info("Main controller initialized");
    }

    @FXML
    public void onNewActivityButtonClick(ActionEvent event) {
        handleNewActivityDialog(event);
    }

    @FXML
    public void onKeyPressedActivityListView(KeyEvent key) {
        /*if (key.getCode().equals(KeyCode.DELETE)) {
            int index = activityListView.getSelectionModel().getSelectedIndex();
            if (index > -1) {
                handleDeleteActivity(index);
            }
        }*/
    }

    public void handleOpenBrowser(String url) {
        Desktop desktop = java.awt.Desktop.getDesktop();
        try {
            //specify the protocol along with the URL
            URI oURL = new URI(url);
            desktop.browse(oURL);
        } catch (URISyntaxException | IOException e) {
            e.printStackTrace();
        }
    }

    public void handleNewActivityDialog(ActionEvent event) {
        //Node parentNode = (Node) event.getSource();
        CreateActivityDialog dialog = null;
        try {
            dialog = new CreateActivityDialog();
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error creating new activity");
            alert.setHeaderText("An error occured while trying to create a new activity dialog.");
            alert.setContentText(e.toString());
        }
        dialog.show();
    }
}
