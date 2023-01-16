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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class RegisterController {
    @Autowired
    UserService userService;

    @GetMapping("/signup")
    public String getRegistrationPage(Model model){
          model.addAttribute("user", new User());
          return "signup";
    }
    @PostMapping("/signup")
    public String postRegistrationPage(Model model,@ModelAttribute User user, RedirectAttributes redirectAttributes){
        String successMessage =   "You successfully signed up! Please sign in below";
        boolean result = true;
        User tempUser = userService.findUserByUserName(user.getUsername());
        if(tempUser == null){
            result = true;
        }else{
            result = false;
        }
        if (result){
//            model.addAttribute("successMessage", "");
            userService.createUser(user);
            redirectAttributes.addFlashAttribute("successMessage", successMessage);
            return "redirect:/login";
        }
        else {
            model.addAttribute("errorUserTaken", "");
            return "signup";
        }
//        System.out.println("post register");
//        System.out.println(user);
    }

}
