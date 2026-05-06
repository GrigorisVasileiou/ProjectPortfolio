package com.example;

import com.example.entity.User;
import com.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class HelloController {

    @Autowired
    private UserService userService;
    @Autowired
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public HelloController(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/hello")
    public String helloPage(@RequestParam(required = false) String error,
                            Model model) {

        model.addAttribute("error", error);
        return "hello";
    }

    @GetMapping("/register")
    public String registerPage() {
        return "register";
    }

    @PostMapping("/register")
    public String register(@RequestParam String username,
                           @RequestParam String password,
                           @RequestParam String email) {
        if (userService.findByUsername(username) != null) {
            return "redirect:/hello?error=userExists";
        }

        userService.registerUser(username, email, password);

        return "redirect:/hello?success=registered";
    }

    @GetMapping("/workspace")
    public String workspace(@AuthenticationPrincipal org.springframework.security.core.userdetails.User currentUser,
                            Model model) {
        User userEntity = userService.findByUsername(currentUser.getUsername());

        model.addAttribute("user", userEntity);
        return "workspace";
    }

    @PostMapping("/change-password")
    public String changePassword(@RequestParam String oldPassword,
                                 @RequestParam String newPassword,
                                 @AuthenticationPrincipal org.springframework.security.core.userdetails.User currentUser,
                                 Model model) {

        User userEntity = userService.findByUsername(currentUser.getUsername());

        if (!passwordEncoder.matches(oldPassword, userEntity.getPassword())) {
            model.addAttribute("error", "Old password is incorrect");
            model.addAttribute("user", userEntity);
            return "hello";
        }
        userService.saveRawPassword(userEntity, newPassword);

        model.addAttribute("success", "Password changed successfully!");
        model.addAttribute("user", userEntity);
        return "hello";
    }

    @PostMapping("/update-profile")
    public String updateProfile(@RequestParam String username,
                                @RequestParam String email,
                                @RequestParam(required = false) String oldPassword,
                                @RequestParam(required = false) String newPassword,
                                @AuthenticationPrincipal org.springframework.security.core.userdetails.User currentUser,
                                Model model) {

        User userEntity = userService.findByUsername(currentUser.getUsername());

        if (userEntity == null) {
            model.addAttribute("error", "User not found!");
            return "hello";
        }

        if (username != null && !username.isEmpty()) {
            userEntity.setUsername(username);
        }
        if (email != null && !email.isEmpty()) {
            userEntity.setEmail(email);
        }

        if (oldPassword != null && newPassword != null &&
                !oldPassword.isEmpty() && !newPassword.isEmpty()) {
            if (!passwordEncoder.matches(oldPassword, userEntity.getPassword())) {
                model.addAttribute("error", "Old password is incorrect");
                model.addAttribute("user", userEntity);
                return "hello";
            }
            //userService.saveRawPassword(userEntity, newPassword);
            userEntity.setPassword(passwordEncoder.encode(newPassword));
            userService.save(userEntity);
        }

        userService.saveWithoutEncoding(userEntity);

        model.addAttribute("success", "Profile updated successfully!");
        model.addAttribute("user", userEntity);

        return "hello";
    }

    @GetMapping("/profile")
    public String profile(@AuthenticationPrincipal org.springframework.security.core.userdetails.User currentUser,
                          Model model) {

        User userEntity = userService.findByUsername(currentUser.getUsername());

        model.addAttribute("user", userEntity);
        return "profile";
    }

    @GetMapping("/collaborators")
    public String collaborators() {
        return "collab";
    }
}