package com.example.task_manegement_web_application.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idtask")
    private Long id;
    @Column(name = "name")
    private String name;
    @Column(name = "description")
    private String description;
    @Column(name = "dueDate")
    private LocalDateTime dueDate;
    @Column(name = "completed")
    private boolean completed;


    @ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
    private UserEntity userEntity;
    private String daysRemaining;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY,
            mappedBy = "task")

    private List<Image> images = new ArrayList<>();
    private Long previewImageId;
    private LocalDateTime dateOfCreated;

    @PrePersist
    private void init() {
        dateOfCreated = LocalDateTime.now();
    }

    public void addImageToTask(Image image) {
        image.setTask(this);
        images.add(image);
    }

    public void dada(Long id) {
        this.userEntity.setId(id);
    }

//    public void setUserEntity(Long id) {
//       this.userEntity = id;
//    }
//    public Long setUser(Long id) {
//        return this.setUserEntity(id);
//    }
//    public Task() {
//    }
//
//    public Task(String name, LocalDateTime dueDate, String description, boolean completed) {
//        this.name = name;
//        this.dueDate = dueDate;
//        this.completed = completed;
//        this.description = description;
//    }
//
//    public String getDescription() {
//        return description;
//    }
//
//    public void setDescription(String fullDescription) {
//        this.description = fullDescription;
//    }
//
//    public Long getId() {
//        return id;
//    }
//
//    public void setId(Long id) {
//        this.id = id;
//    }
//
//    public String getName() {
//        return name;
//    }
//
//    public void setName(String description) {
//        this.name = description;
//    }
//
//    public LocalDateTime getDueDate() {
//        return dueDate;
//    }
//
//    public void setDueDate(LocalDateTime dueDate) {
//        this.dueDate = dueDate;
//    }
//
//    public boolean isCompleted() {
//        return completed;
//    }
//
//    public void setCompleted(boolean completed) {
//        this.completed = completed;
//    }
//    public String  getDaysRemaining() {
//        return daysRemaining;
//    }
//    public void setDaysRemaining(String  daysRemaining) {
//        this.daysRemaining = daysRemaining;
//    }
}
