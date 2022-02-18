package com.jovan.activityplanner.controller;

import com.jovan.activityplanner.model.ActivityModel;
import com.jovan.activityplanner.model.RootActivity;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;

public class CreateActivityController {

    @FXML private Button createButton;
    @FXML private Button cancelButton;

    @FXML private DatePicker dateStart;
    @FXML private DatePicker dateEnd;
    @FXML private TextField timeStartText;
    @FXML private TextField timeEndText;
    @FXML private TextField titleText;
    @FXML private TextArea descriptionText;

    private ActivityModel model;

    public void initialize() {
        this.model = ActivityModel.getInstance();
    }

    private void handleCreation() {
        try {
            LocalDateTime newTimeStart = LocalDateTime.of(dateStart.getValue(), LocalTime.parse(timeStartText.getText()));
            LocalDateTime newTimeEnd = LocalDateTime.of(dateEnd.getValue(), LocalTime.parse(timeEndText.getText()));

            RootActivity newActivity = new RootActivity(newTimeStart, newTimeEnd, titleText.getText(), descriptionText.getText());
            this.model.addActivity(newActivity);
        } catch (DateTimeParseException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Time entered is invalid");
            alert.setContentText(String.format("The entered time is not in the correct time format (HH:mm / 09:35). Exception: %s", e.getLocalizedMessage()));
            alert.show();
        } catch (IllegalArgumentException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error creating new activity");
            alert.setContentText(String.format(e.getLocalizedMessage()));
            alert.show();
        }
    }

    @FXML
    public void onCreateButtonClick(Event event) {
        handleCreation();
        event.consume();
        closeDialog();
    }

    @FXML
    public void onCancelButtonClick() {
        closeDialog();
    }

    private void closeDialog() {
       Stage stage = (Stage) cancelButton.getScene().getWindow();
       stage.close();
    }

}
