package com.jovan.activityplanner.view;

import com.jovan.activityplanner.Main;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class CreateActivityDialog extends Stage {

    public CreateActivityDialog (Node parentNode) throws IOException {
        super();

        FXMLLoader loader = new FXMLLoader(Main.class.getResource("fxml/createActivityView.fxml"));
        Scene createActivityScene = new Scene(loader.load(), 300, 300);

        this.initOwner(parentNode.getScene().getWindow());
        this.setScene(createActivityScene);
        this.setTitle("Create new activity");
        this.initModality(Modality.WINDOW_MODAL);
    }
}
