module com.jovan.activityplanner {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires java.logging;
    requires com.google.gson;

    exports com.jovan.activityplanner;
    opens com.jovan.activityplanner.controller to javafx.fxml;
    exports com.jovan.activityplanner.controller;
}