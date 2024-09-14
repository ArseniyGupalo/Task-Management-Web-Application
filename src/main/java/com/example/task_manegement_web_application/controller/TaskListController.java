package com.example.task_manegement_web_application.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/tasks")
public class TaskListController {

    @GetMapping
    public String taskListPage() {
        return "task-list";
    }
}
