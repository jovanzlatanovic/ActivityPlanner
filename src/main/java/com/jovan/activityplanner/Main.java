package com.jovan.activityplanner;

import com.jovan.activityplanner.util.LoggerSingleton;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;

import java.io.IOException;
import java.util.logging.Logger;

public class Main extends Application {
    private Logger logger = LoggerSingleton.getInstance();

    @Override
    public void start(Stage primaryStage) throws IOException {
        // Initialize logger
        logger.info("FXML Loader loading resource.");

        // Remove window decoration
        //primaryStage.initStyle(StageStyle.UNDECORATED);

        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("fxml/mainView.fxml"));
        Scene mainScene = new Scene(fxmlLoader.load(), 1000, 600);

        // Css loading
        mainScene.getStylesheets().add(Main.class.getResource("/style.css").toExternalForm());

        logger.info("Main scene loaded.");

        // Initialize primary stage
        primaryStage.setTitle("Activity Planner");
        primaryStage.setScene(mainScene);
        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent windowEvent) {
                logger.info("Closing primary stage");
            }
        });

        // Application start
        primaryStage.show();
        logger.info("Showing primary stage.");
    }

    @Override
    public void stop() {
        logger.info("Application stopped. Goodbye <3");
    }

    public static void main(String[] args) {
        Application.launch(args);
    }
}