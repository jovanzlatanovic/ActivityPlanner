package com.jovan.activityplanner;

import com.jovan.activityplanner.model.Activity;
import org.junit.jupiter.api.*;

import java.time.LocalDateTime;

public class ActivityTest {
    private static Activity activity;

    @BeforeAll
    static void setUp() {
        activity = new Activity("1", LocalDateTime.now(), LocalDateTime.now(), "Title", "Description");
    }

    @Test
    @DisplayName("Create activity without an exception")
    void CreateActivityWithoutAnException() {
        Assertions.assertEquals("activity-1", activity.getId());
    }

    @Test
    @DisplayName("Get numeric id")
    void TestNumericId() {
        Assertions.assertEquals(1, activity.getNumericId());
    }

    @Test
    @DisplayName("Check if exception is thrown when start time is greater than end time")
    void TestStartTimeGreaterThanEndTime() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            new Activity("1", LocalDateTime.now().plusHours(1), LocalDateTime.now(), "Title", "Description");
        });
    }

    @Test
    @DisplayName("Check if exception is thrown when start time is greater than end time in setTime()")
    void TestSetTimeThrowsException() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            activity.setTime(LocalDateTime.now().plusHours(1), LocalDateTime.now());
        });
    }
}
