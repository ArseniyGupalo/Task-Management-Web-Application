package com.example.task_manegement_web_application.interfaces;

import com.example.task_manegement_web_application.entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Image, Long> {

}
