package com.example.task_manegement_web_application.controller;

import com.example.task_manegement_web_application.entity.Task;
import com.example.task_manegement_web_application.entity.UserEntity;
import com.example.task_manegement_web_application.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.example.task_manegement_web_application.services.TaskService;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import static com.example.task_manegement_web_application.controller.Functions.updateRemainingDaysFunc;

@Controller
@RequestMapping("home/tasks")


public class TaskController {
    private final TaskService taskService;
    private final UserService userService;


    @Autowired
    public TaskController(TaskService taskService, UserService userService) {
        this.taskService = taskService;
        this.userService = userService;
    }

    @GetMapping()
    public String getAllTasks(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();

        List<Task> tasks = taskService.getTaskByUsername(username);

        updateRemainingDaysFunc(tasks);

        model.addAttribute("tasks", tasks);
        return "task-list";
    }

    //    @PreAuthorize("hasRole('USER_ROLE')")
    @PostMapping("/create")
    public String createTask(@ModelAttribute Task task, @RequestParam("file") MultipartFile file) throws IOException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        UserEntity user = userService.getUser(username);
        task.setUserEntity(user);
        //task.setUserEntity(taskService.getUserFromDB(username));
        taskService.createTask(task, file);
        return "redirect:/home/tasks";
    }

//    @GetMapping("/home/tasks")
//    public String getTasks(Model model) {
//        List<Task> tasks = taskService.getAllTasksSorted(); // Метод в вашем сервисе для получения отсортированных задач
//        model.addAttribute("tasks", tasks);
//        return "task-list";
//    }

    @GetMapping("/{taskId}")
    public String getTaskById(@PathVariable Long taskId, Model model) {
        Task task = taskService.getTaskById(taskId);
        updateRemainingDaysFunc(task);
        model.addAttribute("task", task);
        model.addAttribute("images", task.getImages());
        return "task-details";
    }

    @GetMapping("/create")
    public String createTaskForm(Model model) {
        model.addAttribute("task", new Task());
        return "create-task";
    }



    @PostMapping("/{taskId}")
    public String updateTaskStatus(@PathVariable Long taskId,
                                   @RequestBody Map<String, Object> request) {
        boolean completed = (boolean) request.get("completed");
        taskService.updateTaskStatus(taskId, completed);
        return "redirect:/home/tasks";
    }

    @PostMapping("{taskId}/delete")
    public String deleteTask(@RequestParam Long taskId) {
        taskService.deleteTask(taskId);
        return "redirect:/home/tasks";
    }

    @GetMapping("/{taskId}/edit")
    public String editTaskForm(@PathVariable Long taskId,
                               Model model) {
        Task task = taskService.getTaskById(taskId);
        model.addAttribute("task", task);
        return "edit-task";
    }

    @PostMapping("/{taskId}/edit")
    public String editTask(@PathVariable Long taskId,
                           @ModelAttribute Task updateTask,
                           @RequestParam("image") MultipartFile imageFile) throws IOException{
        taskService.updateTask(taskId, updateTask, imageFile);
        return "redirect:/home/tasks/{taskId}";
    }



}
