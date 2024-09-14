package com.example.task_manegement_web_application.controller;

import com.example.task_manegement_web_application.entity.Task;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;

public class Functions {
    public static String remainingDaysFunc(Task task) {
        LocalDateTime start = LocalDateTime.now();
        long remainDays = ChronoUnit.DAYS.between(start,task.getDueDate());
        if (remainDays == 0) {
            long remainHours = ChronoUnit.HOURS.between(start, task.getDueDate());
            if (remainHours == 0) {
                long remainMinutes = ChronoUnit.MINUTES.between(start, task.getDueDate());
                if (remainMinutes == 0) {
                    long remainMillis = ChronoUnit.MILLIS.between(start, task.getDueDate());
                    if (remainMillis < 0) {
                        return "Expired";
                    } else {
                        return "A few seconds remaining";
                    }
                } else {
                    return remainMinutes + " minutes remaining";
                }
            } else {
                if (remainHours < 0) {
                    return "Expired";
                } else {
                    return remainHours + " hours remaining";
                }
            }
        } else {
            if (remainDays < 0) {
                return "Expired";
            } else {
                return remainDays + " days remaining";
            }
        }

    }

    public static void updateRemainingDaysFunc(List<Task> tasks) {
        for (Task task : tasks) {
            String daysRemaining = Functions.remainingDaysFunc(task);
            task.setDaysRemaining(daysRemaining);
        }
    }
    public static void updateRemainingDaysFunc(Task task) {
            String daysRemaining = Functions.remainingDaysFunc(task);
            task.setDaysRemaining(daysRemaining);
    }
}
