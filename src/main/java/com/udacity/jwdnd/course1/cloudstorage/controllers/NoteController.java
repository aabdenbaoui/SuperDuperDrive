package com.udacity.jwdnd.course1.cloudstorage.controllers;

import com.udacity.jwdnd.course1.cloudstorage.entities.Note;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/notes")
public class NoteController {
    @Autowired
    NoteService noteService;
    @PostMapping("/saveOrUpdateNote")
    public String saveOrUpdateCredential(Note note) {
        System.out.println("save or update");
        noteService.createOrUpdateNote(note);
        return "redirect:/home";
    }
    @GetMapping("/delete")
    public String deleteCredential(@RequestParam("id") Integer id) {
        System.out.println("delete note has been called before deleteById");
        noteService.deleteById(id);
        System.out.println("delete credential has been called");
        return "redirect:/home";
//        return "home";
    }

}
