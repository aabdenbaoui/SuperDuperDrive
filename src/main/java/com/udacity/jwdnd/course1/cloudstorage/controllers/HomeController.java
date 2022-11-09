package com.udacity.jwdnd.course1.cloudstorage.controllers;

import com.udacity.jwdnd.course1.cloudstorage.entities.Credential;
import com.udacity.jwdnd.course1.cloudstorage.entities.File;
import com.udacity.jwdnd.course1.cloudstorage.entities.Note;
import com.udacity.jwdnd.course1.cloudstorage.mappers.ICredentialMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class HomeController {
    @Autowired
    ICredentialMapping credentialMapping;

    @GetMapping("/home")
    public String getHomePage(Model model){
        model.addAttribute("file", new File());
        model.addAttribute("note", new Note());
        model.addAttribute("credential", new Credential());
        return "home";
    }
    @PostMapping("/saveCredential")
    public void saveCredential(Credential credential){
        credentialMapping.createCredential(new Credential(credential.getUrl(), credential.getUsername(), credential.getPassword(), credential.getKey(),null));
    }
}
