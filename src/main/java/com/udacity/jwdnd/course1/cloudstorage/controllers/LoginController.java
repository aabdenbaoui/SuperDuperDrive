package com.udacity.jwdnd.course1.cloudstorage.controllers;

import org.springframework.stereotype.Controller;

@Controller
public class LoginController {

    public String getLoginPage(){
        return "login";
    }
}
