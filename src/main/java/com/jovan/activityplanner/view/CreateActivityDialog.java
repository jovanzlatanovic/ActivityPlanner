package com.jovan.activityplanner.view;

import com.jovan.activityplanner.Main;
import com.jovan.activityplanner.controller.CreateActivityController;
import com.jovan.activityplanner.model.Activity;
import com.jovan.activityplanner.model.ActivityModel;
import com.jovan.activityplanner.model.RootActivity;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class CreateActivityDialog extends Stage {

    int activityIndex = -1;

    public CreateActivityDialog () throws IOException {
        super();
        load();
        this.setTitle("Create new activity");
    }

    public CreateActivityDialog (ActivityModel model, int activityIndex) throws IOException {
        super();

        this.activityIndex = activityIndex;
        load();
        this.setTitle(String.format("%s", model.getActivity(activityIndex).getTitle()));
    }

    private void load() throws IOException {
        FXMLLoader loader = new FXMLLoader(Main.class.getResource("fxml/createActivityView.fxml"));
        Scene createActivityScene = new Scene(loader.load());
        createActivityScene.getStylesheets().add(Main.class.getResource("/style.css").toExternalForm());

        if (activityIndex > -1) {
            CreateActivityController controller = loader.getController();
            controller.setActivityToEditIndex(activityIndex);
        }

        this.setScene(createActivityScene);
        this.initModality(Modality.WINDOW_MODAL);
    }
}
