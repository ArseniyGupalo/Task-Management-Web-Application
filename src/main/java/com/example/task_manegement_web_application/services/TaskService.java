package com.example.task_manegement_web_application.services;

import com.example.task_manegement_web_application.entity.Image;
import com.example.task_manegement_web_application.entity.Task;
import com.example.task_manegement_web_application.interfaces.TaskRepository;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class TaskService {
    private final TaskRepository taskRepository;

    private EntityManager entityManager;

    @Autowired
    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public Task createTask(Task task, MultipartFile file) throws IOException {
        Image image;
        if (file.getSize() != 0) {
            image = toImageEntity(file);
            image.setPreviewImage(true);
            task.addImageToTask(image);
        }
        Task taskFromDb = taskRepository.save(task);
        taskFromDb.setPreviewImageId(taskFromDb.getImages().get(0).getId());
        return taskRepository.save(task);
    }

    private Image toImageEntity(MultipartFile file) throws IOException {
        Image image = new Image();
        image.setName(file.getName());
        image.setOriginalFileName(file.getOriginalFilename());
        image.setContentType(file.getContentType());
        image.setSize(file.getSize());
        image.setBytes(file.getBytes());
        return image;
    }

    public Task updateTask(Long taskId, Task updatedTask, MultipartFile file) throws IOException {
        Task existingTask = taskRepository
                .findById(taskId)
                .orElseThrow(() -> new IllegalArgumentException("Task with ID " + taskId + " not found"));


        if (updatedTask.getName() != null) {
            existingTask.setName(updatedTask.getName());
        }
        if (updatedTask.getDueDate() != null) {
            existingTask.setDueDate(updatedTask.getDueDate());
        }
        if (updatedTask.getDescription() != null) {
            existingTask.setDescription(updatedTask.getDescription());
        }

        if (file != null && !file.isEmpty()) {
            Image image = toImageEntity(file);
            image.setPreviewImage(true);

            existingTask.getImages().clear();
            existingTask.addImageToTask(image);
        }

        return taskRepository.save(existingTask);
    }


    public void deleteTask(Long id) {
        taskRepository.deleteById(id);
    }

    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    public Task getTaskById(Long id) {
        return taskRepository.findById(id).orElse(null);
    }

    public void updateTaskStatus(Long taskId, boolean completed) {
        Optional<Task> taskOptional = taskRepository.findById(taskId);
        if (taskOptional.isPresent()) {
            Task task = taskOptional.get();
            task.setCompleted(completed);
            taskRepository.save(task);
        } else {
            System.out.println("Task with ID " + taskId + " not found");
        }
    }

    @Transactional(readOnly = true)
    public List<Task> getAllTasksSorted() {
        Sort sort = Sort.by(Sort.Order.asc("completed").ignoreCase(), Sort.Order.desc("dueDate").ignoreCase());
        return taskRepository.findAll(sort);
    }


//    public List<Task> getTaskByUsername(String username) {
//        String hql = "SELECT t FROM Task t WHERE t.user_entity_id.username = :username";
//        return entityManager.createQuery(hql, Task.class)
//                .setParameter("username", username)
//                .getResultList();
//
//    }

    public List<Task> getTaskByUsername(String username) {
        return taskRepository.findByUsername(username);
    }

//    public Long getUser(Long id) {
//        return taskRepository.findById(id);
//    }
}
