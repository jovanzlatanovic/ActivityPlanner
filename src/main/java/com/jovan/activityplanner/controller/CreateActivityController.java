package com.jovan.activityplanner.controller;

import com.jovan.activityplanner.model.Activity;
import com.jovan.activityplanner.model.ActivityModel;
import com.jovan.activityplanner.model.ApplicationModel;
import com.jovan.activityplanner.model.RootActivity;
import com.jovan.activityplanner.model.command.CreateCommand;
import com.jovan.activityplanner.model.command.UpdateCommand;
import com.jovan.activityplanner.util.LoggerSingleton;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import java.util.logging.Logger;

public class CreateActivityController {

    @FXML private Button undoButton;
    @FXML private Button createButton;
    @FXML private Button cancelButton;

    @FXML private DatePicker dateStart;
    @FXML private DatePicker dateEnd;
    @FXML private TextField timeStartText;
    @FXML private TextField timeEndText;
    @FXML private TextField titleText;
    @FXML private TextArea descriptionText;

    private Logger logger = LoggerSingleton.getInstance();

    private ActivityModel model;
    private ApplicationModel appModel;

    private int activityToEditIndex = -1;
    private Activity activityToEdit;

    public void initialize() {
        this.model = ActivityModel.getInstance();
        this.appModel = ApplicationModel.getInstance();

        // Set defaults
        this.dateStart.setValue(LocalDate.now());
        this.dateEnd.setValue(LocalDate.now());

        this.timeStartText.setText(LocalTime.now().truncatedTo(ChronoUnit.MINUTES).toString());
        this.timeEndText.setText(LocalTime.now().plus(1, ChronoUnit.HOURS).truncatedTo(ChronoUnit.MINUTES).toString());

        logger.info("Create activity scene initialized");
    }

    private void handleCreation() {
        try {
            LocalDateTime newTimeStart = LocalDateTime.of(dateStart.getValue(), LocalTime.parse(timeStartText.getText()));
            LocalDateTime newTimeEnd = LocalDateTime.of(dateEnd.getValue(), LocalTime.parse(timeEndText.getText()));

            String new_id = model.getUniqueId();
            RootActivity newActivity = new RootActivity(new_id, newTimeStart, newTimeEnd, titleText.getText(), descriptionText.getText());

            if (activityToEditIndex < 0) {
                CreateCommand c = new CreateCommand(appModel, model);
                c.setActivityToCreate(newActivity);
                this.appModel.executeCommand(c);
            } else {
                UpdateCommand c = new UpdateCommand(appModel, model);
                c.setParameters(activityToEditIndex, newActivity);
                this.appModel.executeCommand(c);
            }
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

    public void setActivityToEditIndex(int index) {
        this.activityToEditIndex = index;
        updateViewDataFromActivity();
    }

    private void updateViewDataFromActivity() {
        this.createButton.setText("Save");
        this.cancelButton.setText("Close");

        this.activityToEdit = model.getActivity(this.activityToEditIndex);

        this.dateStart.setValue(activityToEdit.getStartTime().toLocalDate());
        this.dateEnd.setValue(activityToEdit.getEndTime().toLocalDate());

        this.timeStartText.setText(activityToEdit.getStartTime().toLocalTime().toString());
        this.timeEndText.setText(activityToEdit.getEndTime().toLocalTime().toString());

        this.titleText.setText(activityToEdit.getTitle());
        this.descriptionText.setText(activityToEdit.getDescription());
    }

    private void closeDialog() {
       Stage stage = (Stage) cancelButton.getScene().getWindow();
       stage.close();
    }

}
