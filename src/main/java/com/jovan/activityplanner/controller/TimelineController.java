package com.jovan.activityplanner.controller;

import com.jovan.activityplanner.model.Activity;
import com.jovan.activityplanner.model.ActivityModel;
import com.jovan.activityplanner.model.ApplicationModel;
import com.jovan.activityplanner.model.RootActivity;
import com.jovan.activityplanner.model.command.DeleteCommand;
import com.jovan.activityplanner.util.LoggerSingleton;
import com.jovan.activityplanner.view.ActivityContainer;
import com.jovan.activityplanner.view.CreateActivityDialog;
import javafx.collections.ListChangeListener;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.logging.Logger;

public class TimelineController {
    private Logger logger = LoggerSingleton.getInstance();
    private ApplicationModel appModel;
    private ActivityModel model;

    private LocalDate selectedWeek;

    @FXML
    private GridPane rootGrid;
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private Label label_dateRange;

    public void initialize() {
        logger.info("Initializing timeline controller");

        // Get models
        appModel = ApplicationModel.getInstance();
        model = ActivityModel.getInstance();

        // Setup the week date range
        selectedWeek = getMondayOfCurrentWeek();
        refreshTimeline();

        // Setup activity model listener
        model.getActivityList().addListener((ListChangeListener<? super Activity>) change -> {
            logger.info("Detected change on activity list");
            while (change.next()) {
                if (change.wasReplaced()) {
                    // undo and edit
                    logger.info("Undo or editchange was detected");
                    handleActivityModelUndo(change.getFrom(), change.getTo()); // temporary solution for edit, works for now
                } else if (change.wasRemoved()) {
                    // could be undo of first element or deleted activity
                    logger.info("Removal change was detected");
                    handleActivityModelDeletion(change.getFrom(), change.getTo());
                } else if (change.wasAdded()) {
                    // new activity, should add new ActivityContainer to timeline
                    logger.info("Was added change detected");
                    handleActivityModelAddition(change.getFrom(), change.getTo());
                }
            }
        });

        logger.info("Timeline controller initialized");
    }

    private void refreshTimeline() {
        clearTimeline();
        loadWeekToTimeline(selectedWeek);
        loadCurrentWeekActivities();
    }

    private void clearTimeline() {
        rootGrid.getChildren().clear();
        rootGrid.getRowConstraints().clear();
    }

    private void loadWeekToTimeline(LocalDate mondayDate) {
        DateTimeFormatter weekDatePattern = DateTimeFormatter.ofPattern("dd/MM/YYYY");
        DateTimeFormatter weekDayPattern = DateTimeFormatter.ofPattern("dd / MM");

        label_dateRange.setText(weekDatePattern.format(mondayDate));

        // Setup day name labels
        Label mondayNameText = new Label("Monday");
        Label tuesdayNameText = new Label("Tuesday");
        Label wednesdayNameText = new Label("Wednesday");
        Label thursdayNameText = new Label("Thursday");
        Label fridayNameText = new Label("Friday");
        Label saturdayNameText = new Label("Saturday");
        Label sundayNameText = new Label("Sunday");

        // Setup date labels
        Label mondayText = new Label(weekDayPattern.format(mondayDate));
        mondayDate = mondayDate.plus(1, ChronoUnit.DAYS);
        Label tuesdayText = new Label(weekDayPattern.format(mondayDate));
        mondayDate = mondayDate.plus(1, ChronoUnit.DAYS);
        Label wednesdayText = new Label(weekDayPattern.format(mondayDate));
        mondayDate = mondayDate.plus(1, ChronoUnit.DAYS);
        Label thursdayText = new Label(weekDayPattern.format(mondayDate));
        mondayDate = mondayDate.plus(1, ChronoUnit.DAYS);
        Label fridayText = new Label(weekDayPattern.format(mondayDate));
        mondayDate = mondayDate.plus(1, ChronoUnit.DAYS);
        Label saturdayText = new Label(weekDayPattern.format(mondayDate));
        mondayDate = mondayDate.plus(1, ChronoUnit.DAYS);
        Label sundayText = new Label(weekDayPattern.format(mondayDate));

        label_dateRange.setText(label_dateRange.getText() + " - " + weekDatePattern.format(mondayDate));

        // Setup gridview date of week
        RowConstraints dateRow = new RowConstraints(25);
        dateRow.setValignment(VPos.CENTER);
        rootGrid.getRowConstraints().add(dateRow);
        rootGrid.getRowConstraints().add(dateRow);

        rootGrid.add(mondayNameText, 1, 0);
        rootGrid.add(tuesdayNameText, 2, 0);
        rootGrid.add(wednesdayNameText, 3, 0);
        rootGrid.add(thursdayNameText, 4, 0);
        rootGrid.add(fridayNameText, 5, 0);
        rootGrid.add(saturdayNameText, 6, 0);
        rootGrid.add(sundayNameText, 7, 0);

        GridPane.setHalignment(mondayNameText, HPos.CENTER);
        GridPane.setHalignment(tuesdayNameText, HPos.CENTER);
        GridPane.setHalignment(wednesdayNameText, HPos.CENTER);
        GridPane.setHalignment(thursdayNameText, HPos.CENTER);
        GridPane.setHalignment(fridayNameText, HPos.CENTER);
        GridPane.setHalignment(saturdayNameText, HPos.CENTER);
        GridPane.setHalignment(sundayNameText, HPos.CENTER);

        rootGrid.add(mondayText, 1, 1);
        rootGrid.add(tuesdayText, 2, 1);
        rootGrid.add(wednesdayText, 3, 1);
        rootGrid.add(thursdayText, 4, 1);
        rootGrid.add(fridayText, 5, 1);
        rootGrid.add(saturdayText, 6, 1);
        rootGrid.add(sundayText, 7, 1);

        GridPane.setHalignment(mondayText, HPos.CENTER);
        GridPane.setHalignment(tuesdayText, HPos.CENTER);
        GridPane.setHalignment(wednesdayText, HPos.CENTER);
        GridPane.setHalignment(thursdayText, HPos.CENTER);
        GridPane.setHalignment(fridayText, HPos.CENTER);
        GridPane.setHalignment(saturdayText, HPos.CENTER);
        GridPane.setHalignment(sundayText, HPos.CENTER);

        // Setup times from midnight in gridview
        LocalTime time = LocalTime.of(0, 0);
        DateTimeFormatter pattern = DateTimeFormatter.ofPattern("HH:mm");
        for (int i = 2; i <= 25; i++) {
            Label label = new Label(pattern.format(time));

            RowConstraints row = new RowConstraints(50);
            row.setValignment(VPos.TOP);

            rootGrid.getRowConstraints().add(row);
            rootGrid.add(label, 0, i);

            time = time.plus(1, ChronoUnit.HOURS);
        }
    }

    private void loadCurrentWeekActivities() {
        for (Activity activity : model.getActivitiesFromWeek(selectedWeek)) {
            addActivityToView((RootActivity) activity);
        }
    }

    private LocalDate getMondayOfCurrentWeek() {
        Calendar c = Calendar.getInstance();
        c.setFirstDayOfWeek(Calendar.MONDAY);
        c.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        return LocalDateTime.ofInstant(c.toInstant(), c.getTimeZone().toZoneId()).toLocalDate();
    }

    @FXML
    private void handleWeekLeftButton() {
        selectedWeek = selectedWeek.minus(1, ChronoUnit.WEEKS);
        refreshTimeline();
    }

    @FXML
    private void handleWeekRightButton() {
        selectedWeek = selectedWeek.plus(1, ChronoUnit.WEEKS);
        refreshTimeline();
    }

    @FXML
    private void handleCurrentWeekButton() {
        selectedWeek = getMondayOfCurrentWeek();
        refreshTimeline();
    }

    private void handleDeleteActivity(Activity activityToDelete) {
        DeleteCommand c = new DeleteCommand(appModel, model);
        c.setActivityToDelete(activityToDelete);
        this.appModel.executeCommand(c);
    }

    private void handleEditActivityDialog(ActionEvent event, int activityIndex) {
        //Node parentNode = (Node) event.getSource();
        CreateActivityDialog dialog = null;
        try {
            dialog = new CreateActivityDialog(this.model, activityIndex);
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error opening the activity edit dialog");
            alert.setHeaderText("An error occured while trying to a open the activity.");
            alert.setContentText(e.toString());
        }
        dialog.show();
    }

    public void handleActivityModelAddition(int fromIndex, int toIndex) {
        for (int i = fromIndex; i < toIndex; i++) {
            addActivityToView((RootActivity) model.getActivity(i));
        }
    }

    public void handleActivityModelUndo(int undoFrom, int undoTo) {
        refreshTimeline();
    }

    public void handleActivityModelDeletion(int deletedFrom, int deletedTo) {
        //todo: potentially refactor to not refresh the whole weekly timeline for a single change, same goes for undo handler
        refreshTimeline();

        //rootGrid.getChildren().re
        // Sublist throws ConcurrentModificationException when removeall is used directly after sublist:
        // List<ActivityContainer> containersToRemove = activityContainers.subList(deletedFrom, deletedTo + 1);
        //activityContainers.removeAll(containersToRemove);

        //activityContainers.remove(deletedFrom);
        //rootHBox.getChildren().remove(deletedFrom);
    }

    private ActivityContainer createNewActivityContainer(RootActivity activity) {
        ContextMenu contextMenu = new ContextMenu();
        MenuItem editMenuItem = new MenuItem("Edit");
        SeparatorMenuItem sep = new SeparatorMenuItem();
        MenuItem deleteMenuItem = new MenuItem("Delete");
        contextMenu.getItems().addAll(editMenuItem, sep, deleteMenuItem);

        editMenuItem.setOnAction(e -> {
            handleEditActivityDialog(e, model.getActivityIndex(activity));
        });

        deleteMenuItem.setOnAction(e -> {
            handleDeleteActivity(activity);
        });

        return new ActivityContainer(activity, new VBox(), contextMenu);
    }

    private void addActivityToView(RootActivity activity) {
        ActivityContainer container = createNewActivityContainer(activity);

        // Check at which day of the week the start date is
        int columnIndex = 1 + Period.between(selectedWeek, activity.getStartTime().toLocalDate()).getDays();

        // If time of activity is after timeBefore (inclusive), place the container in the index of timeBefore
        LocalTime timeBefore = LocalTime.of(0, 0);
        LocalTime timeAfter = LocalTime.of(1, 0);
        DateTimeFormatter pattern = DateTimeFormatter.ofPattern("HH:mm");
        for (int i = 2; i <= 25; i++) {
            if ((activity.getStartTime().toLocalTime().isBefore(timeAfter) && activity.getStartTime().toLocalTime().isAfter(timeBefore) ) || timeBefore.compareTo(activity.getStartTime().toLocalTime()) == 0) {
                rootGrid.add(container, columnIndex, i);
                break;
            }

            timeBefore = timeBefore.plus(1, ChronoUnit.HOURS);
            timeAfter = timeAfter.plus(1, ChronoUnit.HOURS);
        }

        logger.info("Added activity container to timeline view; activity = " + activity);
    }
}
