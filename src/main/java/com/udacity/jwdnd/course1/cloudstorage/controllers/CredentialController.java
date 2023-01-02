package com.udacity.jwdnd.course1.cloudstorage.controllers;

import com.udacity.jwdnd.course1.cloudstorage.entities.Credential;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.ConditionalOnRepositoryType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
@RequestMapping("/credentials")
public class CredentialController {
    @Autowired
    CredentialService credentialService;
    @PostMapping("/saveOrUpdate")
    public String saveOrUpdateCredential(Credential credential) {
        System.out.println("save or update");
        credentialService.createOrUpdateCredential(credential);
        return "redirect:/home";
    }
    @GetMapping("/delete")
    public String deleteCredential(@RequestParam("id") Integer id) {
        System.out.println("delete credential has been called before deleteById");
        credentialService.deleteById(id);
        System.out.println("delete credential has been called");
        return "redirect:/home";
//        return "home";
    }
    @GetMapping("/showUpdate")
    public String showUpdateCredential(@RequestParam("id") Integer id, Model model) {
        Credential credential = credentialService.getCredentialById(id);
        System.out.println(credential);
        model.addAttribute("theCredential", credential);
        System.out.println("update credential has been called before deleteById");
        System.out.println("update credential has been called");
        return "redirect:/home";
    }




}
