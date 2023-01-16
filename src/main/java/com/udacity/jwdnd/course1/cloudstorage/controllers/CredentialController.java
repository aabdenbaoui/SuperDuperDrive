package com.udacity.jwdnd.course1.cloudstorage.controllers;

import com.udacity.jwdnd.course1.cloudstorage.entities.Credential;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.ConditionalOnRepositoryType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
@RequestMapping("/credentials")
public class CredentialController {
    @Autowired
    CredentialService credentialService;
    @PostMapping("/saveOrUpdate")
    public String saveOrUpdateCredential(Credential credential, RedirectAttributes ra) {
        try {
            credentialService.createOrUpdateCredential(credential);
            ra.addFlashAttribute("credentialSuccess", "");
            return "redirect:/result";
        }catch(IllegalArgumentException exp){
            ra.addFlashAttribute("credentialErrorDuplicate", "");
           return "redirect:/result";
        }
    }
    @GetMapping("/delete")
    public String deleteCredential(@RequestParam("id") Integer id, RedirectAttributes ra) {
        ra.addFlashAttribute("credentialDeleteSuccess", "");
        credentialService.deleteById(id);
        return "redirect:/result";
//        return "home";
    }
    @GetMapping("/update")
    public String getCredentialById(@RequestParam("id") Integer id, RedirectAttributes redirectAttributes){
        System.out.println(credentialService.getCredentialById(id));
        redirectAttributes.addFlashAttribute("aCredential", credentialService.getCredentialById(id));
        return "redirect:/home";
    }

}
