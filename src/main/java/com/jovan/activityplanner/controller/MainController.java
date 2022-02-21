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
import com.jovan.activityplanner.view.CreateActivityDialog;
import com.jovan.activityplanner.view.MainMenuBar;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Properties;

public class MainController {

    @FXML private BorderPane rootBorderPane;
    @FXML private MenuBar menuBar;

    //TODO: Switch activity for root activity, check activity model comment
    @FXML private ListView<Activity> activityListView;
    private ApplicationModel appModel;
    private ActivityModel model;

    private UndoCommand undoCommand;
    private RedoCommand redoCommand;

    public void initialize() {
        // Get activity and app models
        this.appModel = ApplicationModel.getInstance();
        this.model = ActivityModel.getInstance();
        activityListView.setItems(model.getActivityList());

        // Setup commands
        undoCommand = new UndoCommand(appModel, model);
        redoCommand = new RedoCommand(appModel, model);

        // Menu bar initialization
        menuBar = new MainMenuBar(this);
        appModel.addHistoryListener((CommandHistoryListener) menuBar);
        rootBorderPane.setTop(menuBar);

        // Context menu initialization
        initializeContextMenu();
    }

    private void initializeContextMenu() {
        // The context menu currently is a temporary solution
        // since there is no timeline implemented at this time.
        // In the future this code should be replaced by code for each activity ui cell.
        // It doesn't really matter it's showing ugly nulls at the moment.

        activityListView.setCellFactory(listView -> {
            ListCell<Activity> cell = new ListCell<>();

            ContextMenu contextMenu = new ContextMenu();
            MenuItem editMenuItem = new MenuItem("Edit");
            SeparatorMenuItem sep = new SeparatorMenuItem();
            MenuItem deleteMenuItem = new MenuItem("Delete");
            contextMenu.getItems().addAll(editMenuItem, sep, deleteMenuItem);

            editMenuItem.setOnAction(e -> {
                handleEditActivityDialog(e, cell.getIndex());
            });

            deleteMenuItem.setOnAction(e -> {
                handleDeleteActivity(cell.getIndex());
            });

            cell.textProperty().bind(cell.itemProperty().asString());

            cell.emptyProperty().addListener((obs, wasEmpty, isNowEmpty) -> {
                if (isNowEmpty) {
                    cell.setContextMenu(null);
                } else {
                    cell.setContextMenu(contextMenu);
                }
            });

            return cell;
        });
    }

    @FXML
    public void onNewActivityButtonClick(ActionEvent event) {
        handleNewActivityDialog(event);
    }

    @FXML
    public void onKeyPressedActivityListView(KeyEvent key) {
        if (key.getCode().equals(KeyCode.DELETE)) {
            int index = activityListView.getSelectionModel().getSelectedIndex();
            if (index > -1) {
                handleDeleteActivity(index);
            }
        }
    }

    public void executeUndo() {
        undoCommand.execute();
    }

    public void executeRedo() {
        redoCommand.execute();
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

    private void handleDeleteActivity(int indexToDelete) {
        DeleteCommand c = new DeleteCommand(appModel, model);
        c.setActivityIndxed(indexToDelete);
        this.appModel.executeCommand(c);
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

    private void handleEditActivityDialog(ActionEvent event, int activityIndex) {
        //Node parentNode = (Node) event.getSource();
        CreateActivityDialog dialog = null;
        try {
            dialog = new CreateActivityDialog(this.model, activityIndex);
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error opening the activity edit dialog");
            alert.setHeaderText("An error occured while trying to a open the activity.");
            alert.setContentText(e.toString());
        }
        dialog.show();
    }
}
