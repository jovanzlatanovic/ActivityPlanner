package com.jovan.activityplanner.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class CreateActivityController {

    @FXML private Button createButton;
    @FXML private Button cancelButton;

    @FXML
    public void onCreateButtonClick() {
        System.out.println("works");
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
