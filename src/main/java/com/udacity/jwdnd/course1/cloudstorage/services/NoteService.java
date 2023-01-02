package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.entities.Credential;
import com.udacity.jwdnd.course1.cloudstorage.entities.Note;
import com.udacity.jwdnd.course1.cloudstorage.mappers.INoteMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoteService {

    @Autowired
    INoteMapping noteMapping;
    @Autowired
    AuthenticationService authenticationService;

    public int createNote(Note note){
        Integer userid = authenticationService.getUserId();
        return noteMapping.insert(new Note(null, note.getNoteTitle(), note.getNoteDescription(), userid));
    }
    public List<Note> getAllNotesByUserId(){
        Integer userid = authenticationService.getUserId();
        return noteMapping.getNotesByUserId(userid);
    }

    public void deleteById(Integer id) {
            System.out.println("The id to be deleted: " + id);
            noteMapping.deleteById(id);
    }
}
