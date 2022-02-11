module com.jovan.activityplanner {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.jovan.activityplanner to javafx.fxml;
    exports com.jovan.activityplanner;
    opens com.jovan.activityplanner.controller to javafx.fxml;
    exports com.jovan.activityplanner.controller;
}