package com.jovan.activityplanner.view;

import com.jovan.activityplanner.Main;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Window;

import java.io.IOException;

public class AboutDialog extends Alert {
    public AboutDialog(Window owner) {
        super(AlertType.INFORMATION);

        String version = "1.0"; // for some reason fetching properties from pom using properties-maven-plugin simply doesnt work

        this.initOwner(owner);
        this.getOwner().getScene().getStylesheets().add(Main.class.getResource("/style.css").toExternalForm());

        Image image = new Image("icon_about.png");
        this.setGraphic(new ImageView(image));

        this.setTitle("About Activity Planner");
        this.setHeaderText(String.format("Activity Planner %s", version));
        this.setContentText(String.format("Running on Java version: %s\n\nBuilt using JavaFX 17.0.1\nIconography from icons8.com\n\nMade by Jovan Zlatanović", System.getProperty("java.version")));
    }
}
