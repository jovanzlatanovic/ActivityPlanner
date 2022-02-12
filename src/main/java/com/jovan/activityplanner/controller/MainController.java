package com.jovan.activityplanner.controller;

import com.jovan.activityplanner.Main;
import com.jovan.activityplanner.model.Activity;
import com.jovan.activityplanner.model.RootActivity;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.time.LocalDateTime;

public class MainController {

    @FXML
    public void onNewActivityButtonClick(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(Main.class.getResource("fxml/create_activity.fxml"));
        Scene createActivityScene = new Scene(loader.load(), 300, 300);

        Node parentNode = (Node) event.getSource();

        Stage stage = new Stage();

        // TODO: When CSS and unified design is implemented, undecorate the stage:
        // stage.initStyle(StageStyle.UNDECORATED);

        stage.initOwner(parentNode.getScene().getWindow());
        stage.setScene(createActivityScene);
        stage.setTitle("Create new activity");
        stage.initModality(Modality.WINDOW_MODAL);
        stage.show();
    }
}
