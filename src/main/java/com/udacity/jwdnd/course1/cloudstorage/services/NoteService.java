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
            return createNote(note, userid);
        }else{
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
            noteMapping.deleteById(id);
    }

    public int createNote(Note note, Integer userid){

        if(noteMapping.getNoteByTitle(note.getNoteTitle()) != null){
            throw new IllegalArgumentException("Duplicate Note");
        }
        if(note.getNoteDescription().length() >= 1000){
            throw new IllegalArgumentException("Description is too long");
        }
        return noteMapping.insert(new Note(null, note.getNoteTitle(), note.getNoteDescription(), userid));
    }
}
