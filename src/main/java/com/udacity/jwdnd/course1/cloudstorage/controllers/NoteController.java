package com.udacity.jwdnd.course1.cloudstorage.controllers;

import com.udacity.jwdnd.course1.cloudstorage.entities.Note;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/notes")
public class NoteController {
    @Autowired
    NoteService noteService;
    @PostMapping("/saveOrUpdateNote")
    public String saveOrUpdateCredential(Note note, RedirectAttributes ra) {
        ra.addFlashAttribute("noteSuccess", "");
        noteService.createOrUpdateNote(note);
        return "redirect:/result";
//        System.out.println("save or update");
//        return "redirect:/home";
    }
    @GetMapping("/delete")
    public String deleteCredential(@RequestParam("id") Integer id, RedirectAttributes ra) {
        ra.addFlashAttribute("deleteNoteSuccess", "");
        noteService.deleteById(id);
        return "redirect:/result";
//        return "home";
    }

}
