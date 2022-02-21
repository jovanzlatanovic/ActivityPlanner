package com.jovan.activityplanner;

import com.jovan.activityplanner.util.LoggerSingleton;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.IOException;
import java.util.logging.Logger;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws IOException {
        // Initialize logger
        Logger logger = LoggerSingleton.getInstance();

        logger.info("FXML Loader loading resource.");
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("fxml/mainView.fxml"));
        Scene mainScene = new Scene(fxmlLoader.load(), 1000, 600);
        logger.info("Main scene loaded.");

        // Initialize primary stage
        primaryStage.setTitle("Activity Planner");
        primaryStage.setScene(mainScene);
        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent windowEvent) {
                logger.info("Goodbye <3");
            }
        });

        // Application start
        primaryStage.show();
        logger.info("Showing primary stage.");
    }

    public static void main(String[] args) {
        Application.launch(args);
    }
}