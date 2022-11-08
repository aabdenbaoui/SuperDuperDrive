package com.udacity.jwdnd.course1.cloudstorage.controllers;

import com.udacity.jwdnd.course1.cloudstorage.entities.User;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class RegisterController {
    @Autowired
    UserService userService;

    @GetMapping("/register")
    public String getRegistrationPage(Model model){
          model.addAttribute("user", new User());
          return "signup";
    }
    @PostMapping("/register")
    public String postRegistrationPage(@ModelAttribute User user){
      userService.createUser(user);
        System.out.println(user);
      return "signup";
    }

}
