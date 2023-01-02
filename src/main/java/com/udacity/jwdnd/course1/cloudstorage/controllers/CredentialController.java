package com.udacity.jwdnd.course1.cloudstorage.controllers;

import com.udacity.jwdnd.course1.cloudstorage.entities.Credential;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@RestController
@RequestMapping("/credentials")
public class CredentialController {
    @Autowired
    CredentialService credentialService;
    @PostMapping("/save")
    public void saveCredential(HttpServletResponse response, Credential credential) throws IOException {
        credentialService.createCredentialService(credential);
        response.sendRedirect("/home");
    }
    @GetMapping("/delete")
    public void deleteCredential(HttpServletResponse response, @RequestParam("id") Integer id) throws IOException {
        System.out.println("delete credential has been called before deleteById");
        credentialService.deleteById(id);
        System.out.println("delete credential has been called");
        response.sendRedirect("/home");
    }



}
