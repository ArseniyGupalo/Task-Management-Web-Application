package com.example.task_manegement_web_application.controller;

import com.example.task_manegement_web_application.entity.ERole;
import com.example.task_manegement_web_application.entity.UserEntity;
import com.example.task_manegement_web_application.interfaces.UserRepository;
import com.example.task_manegement_web_application.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("home/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping()
    public String getAllUsers(Model model) {
        List<UserEntity> users = userService.getAllUsers();
        model.addAttribute("users", users);
        return "user-list";
    }

    @PostMapping("/create")
    public String createUser(@ModelAttribute UserEntity user) throws IOException {
        userService.saveUser(user);
        return "redirect:/home/users";
    }

    @GetMapping("/create")
    public String createUserForm(Model model) {
        model.addAttribute("user", new UserEntity());
        return "create-user";
    }

    @GetMapping("/{userId}")
    public String getUserById(@PathVariable Long userId, Model model) {
        UserEntity user = userService.getUserById(userId);
        model.addAttribute("user", user);
        return "user-details";
    }


//    @PostMapping("/{userId}")
//    public String updateUserRole(@PathVariable Long userId,
//                                 @RequestBody Map<String, Object> request) {
//        boolean role = (boolean) request.get("")
//
//        return "redirect:home/users/{userId}";
//    }

//    @GetMapping("/{userId}/update")
//    public String updateUserRoleForm(@PathVariable Long userId, Model model) {
//
//    }

    @PostMapping("/{userId}")
    public String updateUserRole(@PathVariable Long userId,
                                 @RequestBody Map<String, String> request) {
        try {
            String roleString = request.get("eRole");
            ERole role = ERole.valueOf(roleString);
            userService.updateUserRole(userId, role);
            return "redirect:home/users/{userId}";
        } catch (IllegalArgumentException e) {
            return e.getMessage();
        }

    }
}
