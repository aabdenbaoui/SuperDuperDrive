package com.udacity.jwdnd.course1.cloudstorage.entities;

public class Note {

            private String noteId;
            private String noteTitle;
            private String noteDescription;
            private Integer  userId;

    public Note() {
    }

    public Note(String noteId, String noteTitle, String noteDescription,Integer  userId) {
        this.noteId = noteId;
        this.noteTitle = noteTitle;
        this.noteDescription = noteDescription;
        this.userId = userId;
    }

    public String getNoteId() {
        return noteId;
    }

    public void setNoteId(String noteId) {
        this.noteId = noteId;
    }

    public String getNoteTitle() {
        return noteTitle;
    }

    public void setNoteTitle(String noteTitle) {
        this.noteTitle = noteTitle;
    }

    public String getNoteDescription() {
        return noteDescription;
    }

    public void setNoteDescription(String noteDescription) {
        this.noteDescription = noteDescription;
    }

    public Integer  getUserId() {
        return userId;
    }
}
