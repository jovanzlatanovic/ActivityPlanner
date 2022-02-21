package com.jovan.activityplanner.controller;

import com.jovan.activityplanner.Main;
import com.jovan.activityplanner.model.Activity;
import com.jovan.activityplanner.model.ActivityModel;
import com.jovan.activityplanner.model.ApplicationModel;
import com.jovan.activityplanner.model.command.DeleteCommand;
import com.jovan.activityplanner.model.command.UndoCommand;
import com.jovan.activityplanner.view.CreateActivityDialog;
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

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Properties;

public class MainController {

    @FXML private MenuBar menuBar;

    //TODO: Switch activity for root activity, check activity model comment
    @FXML private ListView<Activity> activityListView;
    private ApplicationModel appModel;
    private ActivityModel model;

    private UndoCommand undoCommand;
    //todo private RedoCommand redoCommand;

    public void initialize() {
        // Get activity and app models
        this.appModel = ApplicationModel.getInstance();
        this.model = ActivityModel.getInstance();
        activityListView.setItems(model.getActivityList());

        // Setup commands
        undoCommand = new UndoCommand(appModel, model);

        // Menubar initialization
        initializeMenubar();
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

    private void initializeMenubar() {
        Menu fileMenu = new Menu("File");
        Menu editMenu = new Menu("Edit");
        Menu helpMenu = new Menu("Help");

        MenuItem newMenuItem = new MenuItem("New activity");
        newMenuItem.setOnAction(e -> {
            handleNewActivityDialog(e);
        });
        newMenuItem.setAccelerator(new KeyCodeCombination(KeyCode.N, KeyCombination.CONTROL_DOWN));

        SeparatorMenuItem separator = new SeparatorMenuItem();

        MenuItem exitMenuItem = new MenuItem("Exit");
        exitMenuItem.setOnAction(e -> {
            Platform.exit();
        });

        MenuItem undoMenuItem = new MenuItem("Undo");
        undoMenuItem.setAccelerator(new KeyCodeCombination(KeyCode.Z, KeyCombination.CONTROL_DOWN));
        undoMenuItem.setOnAction(e -> {
            undoCommand.execute();
        });

        MenuItem redoMenuItem = new MenuItem("Redo");
        redoMenuItem.setAccelerator(new KeyCodeCombination(KeyCode.Y, KeyCombination.CONTROL_DOWN));
        redoMenuItem.setOnAction(e -> {
            //todo redoCommand.execute();
        });
        redoMenuItem.setDisable(true);

        MenuItem helpMenuItem = new MenuItem("Help");
        helpMenuItem.setAccelerator(new KeyCodeCombination(KeyCode.F1));
        helpMenuItem.setOnAction(e -> {
            openBrowser("https://github.com/jovanzlatanovic/ActivityPlanner/wiki");
        });

        MenuItem aboutMenuItem = new MenuItem("About");
        aboutMenuItem.setOnAction(e -> {
            Properties properties = new Properties();
            try {
                properties.load(Main.class.getResourceAsStream("/project.properties"));
            } catch (IOException ex) {
                ex.printStackTrace();
            }

            Alert aboutDialog = new Alert(Alert.AlertType.INFORMATION);
            aboutDialog.setTitle("About Activity Planner");
            aboutDialog.setHeaderText(String.format("Activity Planner %s", properties.getProperty("version")));
            aboutDialog.setContentText(String.format("Running on Java version: %s\nBuilt using JavaFX 17.0.1\n\nMade by Jovan Zlatanović", System.getProperty("java.version")));
            aboutDialog.showAndWait();
        });

        fileMenu.getItems().addAll(newMenuItem, separator, exitMenuItem);
        editMenu.getItems().addAll(undoMenuItem, redoMenuItem);
        helpMenu.getItems().addAll(helpMenuItem, aboutMenuItem);
        menuBar.getMenus().addAll(fileMenu, editMenu, helpMenu);
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

    private void openBrowser(String url) {
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

    private void handleNewActivityDialog(ActionEvent event) {
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
