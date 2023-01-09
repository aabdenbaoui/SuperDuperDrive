package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.entities.Note;
import com.udacity.jwdnd.course1.cloudstorage.mappers.INoteMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLOutput;
import java.util.List;

@Service
public class NoteService {

    @Autowired
    INoteMapping noteMapping;
    @Autowired
    AuthenticationService authenticationService;

    public int createOrUpdateNote(Note note){
        System.out.println(note.getNoteId());
        Note noteTemp = noteMapping.getNoteById(note.getNoteId());
        Integer userid = authenticationService.getUserId();
        if(noteTemp  == null) {
            System.out.println("No such note exists. New note will be created");
            return noteMapping.insert(new Note(null, note.getNoteTitle(), note.getNoteDescription(), userid));
        }else{
            System.out.println("The note will id exists. Note will be updated");
            note.setUserId(userid);
            noteMapping.updateNote(note);
            return note.getNoteId();

        }
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
